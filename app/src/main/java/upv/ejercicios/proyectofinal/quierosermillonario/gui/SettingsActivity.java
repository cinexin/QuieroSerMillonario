package upv.ejercicios.proyectofinal.quierosermillonario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import upv.ejercicios.proyectofinal.quierosermillonario.R;
import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.gui.utils.ToastMessage;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.LoggingInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.Friend;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.services.FriendService;
import upv.ejercicios.proyectofinal.quierosermillonario.services.GameSettingsService;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0012.
 */

public class SettingsActivity extends ActionBarActivity {

    private int localizationServiceStatus = AppConstants.STATUS_DONT_LOCATE;
    LocationManager locationManager;
    String locationProvider;
    EditText txtLongitude;
    EditText txtLatitude;
    UserLocationListener userLocationListener;

    private void getLocationServices() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            locationProvider = LocationManager.GPS_PROVIDER;
        else {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
    }
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
        String longitudeAsString =  txtLongitude.getText().toString();
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
        String latitudeAsString  = txtLatitude.getText().toString();
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


        txtLongitude.setText(Float.toString(gameSettings.getLongitude()));


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

        txtLongitude = (EditText) findViewById(R.id.txt_longitude);
        txtLatitude = (EditText) findViewById(R.id.txt_latitude);
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
                String userName =  ((EditText) findViewById(R.id.txt_user_name)).getText().toString();
                String friendName = ((EditText) findViewById(R.id.txt_add_friend_name)).getText().toString();

                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(friendName)) {
                    Friend friend = new Friend(userName, friendName);
                    new AddFriendTask().execute(friend);
                }
            }
        });

        userLocationListener = new UserLocationListener();
        this.getLocationServices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_settings_screen, menu);
        switch (localizationServiceStatus) {
            case AppConstants.STATUS_LOCATE:
                menu.findItem(R.id.btn_settings_locate_me).setVisible(false);
                menu.findItem(R.id.btn_settings_stop_locating_me).setVisible(true);
                break;

            case AppConstants.STATUS_DONT_LOCATE:
                menu.findItem(R.id.btn_settings_locate_me).setVisible(true);
                menu.findItem(R.id.btn_settings_stop_locating_me).setVisible(false);
                break;

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logging logging = new Logging();
        switch (item.getItemId()) {

            case R.id.btn_settings_locate_me:
                this.localizationServiceStatus = AppConstants.STATUS_LOCATE;
                try {
                    locationManager.requestLocationUpdates(locationProvider, 0, 0, userLocationListener);
                } catch (SecurityException securityEx) {
                    logging.error("Couldn't get location updates. Check your GPS/Network provider: " +
                            securityEx.getMessage());
                    return false;
                }
                break;

            case R.id.btn_settings_stop_locating_me:
                this.localizationServiceStatus = AppConstants.STATUS_DONT_LOCATE;
                locationManager.removeUpdates(userLocationListener);
                break;

        }
        supportInvalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private class AddFriendTask extends AsyncTask<Friend, Void, Friend> {

        @Override
        protected Friend doInBackground(Friend... params) {
            Friend friend = params[0];
            Log.d("[DEBUG]", friend.toString());

            FriendService friendService = new FriendService(getApplicationContext());

            try {
                friendService.addFriend(friend);
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                Log.e("[ERROR]", "Error adding friend: " + ioEx.getMessage());
                return null;
            }
            return friend;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            TextView statusText = (TextView) findViewById(R.id.txt_friend_add_status);
            statusText.setText(getResources().getString(R.string.msg_adding_friend));
            ToastMessage.infoMessage(getApplicationContext(), getResources().getString(R.string.msg_adding_friend));
        }

        @Override
        protected void onPostExecute(Friend friend) {
            super.onPostExecute(friend);
            TextView statusText = (TextView) findViewById(R.id.txt_friend_add_status);
            if (friend != null) {
                ToastMessage.infoMessage(getApplicationContext(), getResources().getString(R.string.msg_friend_added_successfully));
                statusText.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                statusText.setText(getResources().getString(R.string.msg_friend_added_successfully) + ": " + friend.getFriendName());
            } else {
                statusText.setBackgroundColor(getResources().getColor(R.color.errorGeneric));
                statusText.setText(getResources().getString(R.string.msg_friend_add_error));
            }

            Log.d("[DEBUG]", "Friend added");
        }
    }

    private class UserLocationListener implements LocationListener {

        private Logging logging = new Logging();

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            logging.warning("GPS status changed. Provider: " + provider + ". Status: " + status);
        }

        @Override
        public void onProviderDisabled(String provider) {
            logging.warning("Location provider disabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            logging.info("Provider enabled: " + provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            txtLongitude.setText(String.valueOf(location.getLongitude()));
            txtLatitude.setText(String.valueOf(location.getLatitude()));
        }
    }
}
