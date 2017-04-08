package upv.ejercicios.proyectofinal.quierosermillonario.gui;


import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameScoresService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0012.
 */

public class HighScoresActivity extends ActionBarActivity {

    private String userName;

    private void displayInTable(TableLayout scoresTable, List<GameScore> scores) {
        int positionInTheRanking = 1;
        for (GameScore score : scores) {

            String textRow = String.valueOf(positionInTheRanking) + " - " +
                    score.getUserName() + " | " + score.getMoneyAchieved() + " | "
                    + score.getUserFriendlyLocationName();
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText(textRow);
            //textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(getResources().getDimension(R.dimen.scores_list_text_size));
            tableRow.addView(textView);
            scoresTable.addView(tableRow);
            positionInTheRanking++;
        }
    }

    /*
        all="true": get all scores and fill "Your friend's high scores tab" (get remote scores indeed)
        all="false": get only scores for current user and fill data on "Your high scores tab"
            if no user, fill in with "anonymous" scores
     */
    private void fillHighScoresTable(boolean all) {
        GameScoresService gameScoresService= new GameScoresService(this.getApplicationContext());
        Logging logging = new Logging();
        List<GameScore> scores = new ArrayList<>();
        try {
            if (all) {
                // DONE: do it on a separate thread (it implies HTTP-WS REST service call)
                new RemoteHighScoresTask().execute(this.userName);
            } else {
                scores = gameScoresService.getUserScores(this.userName);
                new LocationsTranslationTask().execute(scores);
            }
        } catch (PersistenceException persistEx) {
            logging.error("Error while retrieving scores from database. Original error: " + persistEx.getMessage());
        }




    }

    private void clearScores() {
        GameScoresService gameScoresService= new GameScoresService(this.getApplicationContext());
        try {
            gameScoresService.clearScores();
            TableLayout userHighScoresTable = (TableLayout) findViewById(R.id.user_high_scores_table_view);
            TableLayout friendsHighScoresTable = (TableLayout) findViewById(R.id.all_high_scores_table_view);
            userHighScoresTable.removeAllViews();
            friendsHighScoresTable.removeAllViews();
        } catch (PersistenceException persistEx) {
            Logging logging = new Logging();
            logging.error("Error while removing");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Set tabs content
        TabHost highScoresTabHost = (TabHost) findViewById(R.id.high_scores_tab_host);
        highScoresTabHost.setup();
        TabHost.TabSpec tabSpec = highScoresTabHost.newTabSpec("user_high_scores");
        tabSpec.setIndicator(getResources().getString(R.string.lbl_user_high_scores),
                getResources().getDrawable(R.drawable.ic_high_scores_user_tab));
        tabSpec.setContent(R.id.user_high_scores_view);
        highScoresTabHost.addTab(tabSpec);

        tabSpec = highScoresTabHost.newTabSpec("friends_high_scores");
        tabSpec.setIndicator(getResources().getString(R.string.lbl_friends_high_scores),
                getResources().getDrawable(R.drawable.ic_high_scores_friends_tab));
        tabSpec.setContent(R.id.friends_high_scores_view);
        highScoresTabHost.addTab(tabSpec);

        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        GameSettings gameSettings = gameSettingsService.getSettings();
        userName = gameSettings.getUserName();

        fillHighScoresTable(false);
        fillHighScoresTable(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_high_scores_screen, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_high_scores_delete:
                clearScores();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
        Thread that's in charge of getting the user-friendly location from each Longitude-Latitude
     */
    private class LocationsTranslationTask extends AsyncTask<List<GameScore>, Void, List<GameScore>> {

        @Override
        protected List<GameScore> doInBackground(List<GameScore>... params) {
            List<GameScore> inputGameScoreList = params[0];
            List<GameScore> resultGameScoreList = new ArrayList<>(); // the same input game score list with user-friendly location populated
            Logging logging = new Logging();

            for (GameScore gameScore:inputGameScoreList) {
                if (StringUtils.isEmpty(gameScore.getUserFriendlyLocationName())) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(Double.valueOf(gameScore.getLatitude()).doubleValue(),
                                Double.valueOf(gameScore.getLongitude()).doubleValue(),
                                AppConstants.MAX_NUMBER_OF_RETURN_ADDRESSES);
                        for (Address address:addresses) {
                            StringBuffer userFriendlyLocationName = new StringBuffer("");

                            // get locality if present
                            if (!StringUtils.isEmpty(address.getLocality()))
                                userFriendlyLocationName.append(address.getLocality());

                            if (!StringUtils.isEmpty(userFriendlyLocationName.toString()))
                                userFriendlyLocationName.append(" , ");
                            // get country name and append to locality
                            userFriendlyLocationName.append(address.getCountryName());

                            gameScore.setUserFriendlyLocationName(userFriendlyLocationName.toString());
                        }
                        resultGameScoreList.add(gameScore);
                    } catch (IOException ioEx) {
                        logging.error("Error when translating lat/long into user friendly location: " + ioEx.getMessage());
                        ioEx.printStackTrace();
                        return null;
                    }
                }
            }


            return resultGameScoreList;
        }

        @Override
        protected void onPostExecute(List<GameScore> gameScores) {
            super.onPostExecute(gameScores);
            TableLayout highScoresTable = (TableLayout) findViewById(R.id.user_high_scores_table_view);
            displayInTable(highScoresTable, gameScores);
        }
    }

    /*
        Thread that's in charge of getting remote high scores (via REST-WS)
     */
    private class RemoteHighScoresTask extends AsyncTask<String, Void, List<GameScore>> {
        @Override
        protected List<GameScore> doInBackground(String... params) {
            Logging logging = new Logging();
            String userName = params[0];
            List<GameScore> gameScoreList = null;

            if (!StringUtils.isEmpty(userName)) {
                GameScoresService scoresService = new GameScoresService(getApplicationContext());
                try {
                    gameScoreList = scoresService.getRemoteHighScores(userName);
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                    logging.error("Error fetching remote High Scores: " + ex.getMessage());
                    return null;
                }
            }

            return gameScoreList;
        }

        @Override
        protected void onPostExecute(List<GameScore> gameScores) {
            super.onPostExecute(gameScores);
            if (gameScores != null) {
                TableLayout remoteHighScoresTable = (TableLayout) findViewById(R.id.all_high_scores_table_view);
                displayInTable(remoteHighScoresTable, gameScores);
            }
        }
    }
}
