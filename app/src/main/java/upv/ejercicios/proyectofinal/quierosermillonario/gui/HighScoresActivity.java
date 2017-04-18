package upv.ejercicios.proyectofinal.quierosermillonario.gui;


import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ModalMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.model.taskParams.HighScoresTableParams;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameScoresService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0012.
 */

public class HighScoresActivity extends ActionBarActivity implements OnMapReadyCallback {

    private String userName;
    private GoogleMap googleMap;

    private void displayInTable(TableLayout scoresTable, List<GameScore> scores) {
        int positionInTheRanking = 1;

        for (GameScore score : scores) {
            String userFriendlyLocationName = score.getUserFriendlyLocationName() == null ? "":score.getUserFriendlyLocationName();

            StringBuilder textRow = new StringBuilder("");
            textRow.append(String.valueOf(positionInTheRanking));
            textRow.append(" - "); // tab
            textRow.append(score.getUserName());
            textRow.append(" - "); // tab
            textRow.append(score.getMoneyAchieved());
            textRow.append(" - "); // tab
            textRow.append(userFriendlyLocationName);


            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText(textRow.toString());

            //textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(getResources().getDimension(R.dimen.scores_list_text_size));
            tableRow.addView(textView);
            scoresTable.addView(tableRow);
            positionInTheRanking++;
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.high_scores_progress_bar);
        progressBar.setVisibility(View.GONE);
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
                HighScoresTableParams highScoresTableParams = new HighScoresTableParams(scores, false);
                new LocationsTranslationTask().execute(highScoresTableParams);
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
            userHighScoresTable.removeAllViews();
        } catch (PersistenceException persistEx) {
            Logging logging = new Logging();
            logging.error(this.getClass().getName() + ": " + "Error while removing scores. Cause: " + persistEx.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        googleMap.moveCamera(CameraUpdateFactory.zoomIn());
        this.googleMap = googleMap;
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

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

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
                // ask for user confirmation and clear local scores...
                DialogInterface.OnClickListener yesNoClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                clearScores();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                ModalMessage.ModalYesNoMessage(HighScoresActivity.this, R.string.delete_high_scores_confirmation, yesNoClickListener);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
        Thread that's in charge of getting the user-friendly location from each Longitude-Latitude
     */
    private class LocationsTranslationTask extends AsyncTask<HighScoresTableParams, Void, HighScoresTableParams> {

        @Override
        protected HighScoresTableParams doInBackground(HighScoresTableParams... params) {
            HighScoresTableParams inputHighScoresTableParams = params[0];
            HighScoresTableParams resultHighScoresTableParams = new HighScoresTableParams(); // the same input game score list with user-friendly location populated
            Logging logging = new Logging();

            resultHighScoresTableParams.setRemote(inputHighScoresTableParams.isRemote());
            resultHighScoresTableParams.setGameScoreList(new ArrayList<GameScore>());

            for (GameScore gameScore:inputHighScoresTableParams.getGameScoreList()) {
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
                        resultHighScoresTableParams.getGameScoreList().add(gameScore);
                    } catch (IOException ioEx) {
                        logging.error("Error when translating lat/long into user friendly location: " + ioEx.getMessage());
                        ioEx.printStackTrace();
                        return null;
                    }
                }
            }


            return resultHighScoresTableParams;
        }

        @Override
        protected void onPostExecute(HighScoresTableParams highScoresTableParams) {
            super.onPostExecute(highScoresTableParams);

            TableLayout highScoresTable;

            highScoresTable = (TableLayout) findViewById(R.id.user_high_scores_table_view);
            if (highScoresTableParams != null)
                displayInTable(highScoresTable, highScoresTableParams.getGameScoreList());
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
                /* DONE: Position scores markers in the GMap */
                for (int i = 0; i<gameScores.size();i++) {
                    GameScore gameScore = gameScores.get(i);
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng locationLatLng = new LatLng(Double.valueOf(gameScore.getLatitude()), Double.valueOf(gameScore.getLongitude()));
                    markerOptions.position(locationLatLng);
                    markerOptions.title(gameScore.getUserName());
                    markerOptions.snippet(String.valueOf(gameScore.getMoneyAchieved()));
                    /*
                        Display as "podium"
                     */
                    switch (i) {
                        case 0:
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_first));
                            break;

                        case 1:
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_second));
                            break;

                        case 2:
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_third));
                            break;

                        default:
                            break;
                    }

                    googleMap.addMarker(markerOptions);


                }

            }
        }
    }
}
