package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.LoggingInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0012.
 */

public class SettingsActivity extends ActionBarActivity {

    private GameSettings collectInputSettings() {
        GameSettings gameSettings = new GameSettings();

        LoggingInterface logging = new Logging();

        String userName = ((EditText) findViewById(R.id.txt_user_name)).getText().toString();
        if (userName != null)
            gameSettings.setUserName(userName);

        int numberOfJokers = AppConstants.MAX_NUMBER_OF_JOKERS;
        String numberOfJokersAsString = ((Spinner) findViewById(R.id.select_num_of_jokers)).getSelectedItem().toString();
        if (numberOfJokersAsString != null) {
            numberOfJokers = Integer.valueOf(numberOfJokersAsString);
        }
        gameSettings.setNumberOfJokers(numberOfJokers);

        float longitude ;
        String longitudeAsString =  ((EditText) findViewById(R.id.txt_longitude)).getText().toString();
        if (!StringUtils.isEmpty( longitudeAsString) ) {

            try {
                longitude = Float.valueOf(longitudeAsString);
            } catch (NumberFormatException nfEx) {
                logging.warning("[WARNING] Bad input for longitude: " + longitudeAsString);
                longitude = 0;
            }
            gameSettings.setLongitude(longitude);
        }

        float latitude;
        String latitudeAsString  = ((EditText) findViewById(R.id.txt_latitude)).getText().toString();
        if (!StringUtils.isEmpty(latitudeAsString)) {

            try {
                latitude = Float.valueOf(latitudeAsString);
            } catch (NumberFormatException nfEx) {
                logging.warning("[WARNING] Bad input for latitude: " + latitudeAsString);
                latitude = 0;
            }
            gameSettings.setLatitude(latitude);
        }


        logging.debug(gameSettings.toString());
        return  gameSettings;
    }

    private void fillSavedSettings(GameSettings gameSettings) {
        // DONE: fill fields with saved settings...
        if (!StringUtils.isEmpty(gameSettings.getUserName())) {
            EditText txtUserName = (EditText) findViewById(R.id.txt_user_name);
            txtUserName.setText(gameSettings.getUserName());
        }

        Spinner numOfJokersSpinner = (Spinner) findViewById(R.id.select_num_of_jokers);
        numOfJokersSpinner.setSelection(gameSettings.getNumberOfJokers());

        EditText txtLongitude = (EditText) findViewById(R.id.txt_longitude);
        txtLongitude.setText(Float.toString(gameSettings.getLongitude()));

        EditText txtLatitude = (EditText) findViewById(R.id.txt_latitude);
        txtLatitude.setText(Float.toString(gameSettings.getLatitude()));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        Spinner numOfJokesSpinner = (Spinner) findViewById(R.id.select_num_of_jokers);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_jokers_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        numOfJokesSpinner.setAdapter(adapter);

        // Retrieve saved settings if already present and show them...
        GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
        GameSettings gameSettings =  gameSettingsService.getSettings();
        if (gameSettings != null)
            fillSavedSettings(gameSettings);

        Button saveSettingsButton = (Button) findViewById(R.id.btn_save_settings);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: Collect input data and Call GameSettingsService.saveSettings
                GameSettings gameSettings = collectInputSettings();
                GameSettingsService gameSettingsService = new GameSettingsService(getApplicationContext());
                gameSettingsService.saveSettings(gameSettings);
            }
        });

        Button addFriendButton = (Button) findViewById(R.id.btn_add_friend);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
