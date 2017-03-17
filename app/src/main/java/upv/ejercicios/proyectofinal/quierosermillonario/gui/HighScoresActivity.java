package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import upv.ejercicios.proyectofinal.quierosermillonario.R;

/**
 * Created by migui on 0012.
 */

public class HighScoresActivity extends ActionBarActivity {

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

        TableLayout highScoresTable = (TableLayout) findViewById(R.id.high_scores_table_view);
        // Fill with sample content
        TableRow tableRow = new TableRow(this);
        TextView textView = new TextView(this);
        textView.setText("Test info 1");
        tableRow.addView(textView);
        highScoresTable.addView(tableRow);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_high_scores_screen, menu);
        return super.onCreateOptionsMenu(menu);

    }
}
