package upv.ejercicios.proyectofinal.quierosermillonario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TabHost;

/**
 * Created by migui on 0012.
 */

public class HighScoresActivity extends ActionBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        TabHost highScoresTabHost = (TabHost) findViewById(R.id.high_scores_tab_host);
        highScoresTabHost.setup();
        TabHost.TabSpec tabSpec = highScoresTabHost.newTabSpec("user_high_scores");
        tabSpec.setIndicator(getResources().getString(R.string.lbl_user_high_scores),
                getResources().getDrawable(R.drawable.ic_high_scores_user_tab));
        tabSpec.setContent(R.id.user_high_scores_view);
        highScoresTabHost.addTab(tabSpec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_high_scores_screen, menu);
        return super.onCreateOptionsMenu(menu);

    }
}