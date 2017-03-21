package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import upv.ejercicios.proyectofinal.quierosermillonario.R;

public class MainScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_main_menu_credits:
                Intent intent = new Intent(this,CreditsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClickBtnMainMenu(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_play_game:
                intent = new Intent(this, PlayGameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_high_scores:
                intent = new Intent(this, HighScoresActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            default:
                return;
        }
    }
}
