package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.content.PeriodicSync;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameScoresService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;

/**
 * Created by migui on 0012.
 */

public class HighScoresActivity extends ActionBarActivity {

    private String userName;

    /*
        all="true": get all scores and fill "Your friend's high scores tab"
        all="false": get only scores for current user and fill data on "Your high scores tab"
            if no user, fill in with "anonymous" scores
     */
    private void fillHighScoresTable(boolean all) {
        GameScoresService gameScoresService= new GameScoresService(this.getApplicationContext());
        Logging logging = new Logging();
        List<GameScore> scores = new ArrayList<>();
        try {
            if (all) {
                scores = gameScoresService.getAllScores();
            } else {
                scores = gameScoresService.getUserScores(this.userName);
            }
        } catch (PersistenceException persistEx) {
            logging.error("Error while retrieving scores from database. Original error: " + persistEx.getMessage());
        }

        // display them on the corresponding scores table...
        TableLayout highScoresTable = (TableLayout) findViewById(R.id.user_high_scores_table_view);
        int positionInTheRanking = 1;
        for (GameScore score: scores) {

            String textRow = String.valueOf(positionInTheRanking) + " - " +
                    score.getUserName() + " | " + score.getMoneyAchieved() + " | "
                    + score.getLongitude() + " " + score.getLatitude();
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText(textRow);
            //textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(getResources().getDimension(R.dimen.scores_list_text_size));
            tableRow.addView(textView);
            highScoresTable.addView(tableRow);
            positionInTheRanking++;


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
}
