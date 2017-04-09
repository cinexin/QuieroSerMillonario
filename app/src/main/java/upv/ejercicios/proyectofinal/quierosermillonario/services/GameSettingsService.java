package upv.ejercicios.proyectofinal.quierosermillonario.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.interfaces.SettingsInterface;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameSettings;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0016.
 */

/* Settings management via SharedPreferences Android Service */
public class GameSettingsService implements SettingsInterface {

    Context context ;

    @Override
    public GameSettings getSettings()
    {
        GameSettings gameSettings = new GameSettings();
        SharedPreferences _prefs = context.getApplicationContext().getSharedPreferences(AppConstants.SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        gameSettings.setUserName(_prefs.getString(AppConstants.SETTINGS_USERNAME_KEY, null));
        gameSettings.setNumberOfJokers(_prefs.getInt(AppConstants.SETTINGS_NUMBER_OF_JOKERS_KEY, AppConstants.MAX_NUMBER_OF_JOKERS));
        gameSettings.setLongitude(_prefs.getFloat(AppConstants.SETTINGS_LOCALIZATION_LONGITUDE_KEY, 0));
        gameSettings.setLatitude(_prefs.getFloat(AppConstants.SETTINGS_LOCALIZATION_LATITUDE_KEY, 0));
        gameSettings.setCurrentQuestion(_prefs.getInt(AppConstants.SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY,1));
        gameSettings.setAudienceJokerUsed(_prefs.getBoolean(AppConstants.SETTINGS_AUDIENCE_JOKER_USED, false));
        gameSettings.setFiftyPercentJokerUsed(_prefs.getBoolean(AppConstants.SETTINGS_FIFTY_PERCENT_JOKER_USED, false));
        gameSettings.setPhoneCallJokerUsed(_prefs.getBoolean(AppConstants.SETTINGS_PHONE_CALL_JOKER_USED, false));
        return  gameSettings;
    }

    @Override
    public void saveSettings(GameSettings settings)  {
        SharedPreferences _prefs = context.getApplicationContext().getSharedPreferences(AppConstants.SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();

        if (!StringUtils.isEmpty(settings.getUserName())) {
            _prefsEditor.putString(AppConstants.SETTINGS_USERNAME_KEY, settings.getUserName());
        } else {
            _prefsEditor.putString(AppConstants.SETTINGS_USERNAME_KEY, "");
        }

        _prefsEditor.putInt(AppConstants.SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY, 1);

        _prefsEditor.putInt(AppConstants.SETTINGS_NUMBER_OF_JOKERS_KEY, settings.getNumberOfJokers());
        _prefsEditor.putFloat(AppConstants.SETTINGS_LOCALIZATION_LONGITUDE_KEY, settings.getLongitude());
        _prefsEditor.putFloat(AppConstants.SETTINGS_LOCALIZATION_LATITUDE_KEY, settings.getLatitude());

        _prefsEditor.putBoolean(AppConstants.SETTINGS_AUDIENCE_JOKER_USED, settings.isAudienceJokerUsed());
        _prefsEditor.putBoolean(AppConstants.SETTINGS_FIFTY_PERCENT_JOKER_USED, settings.isFiftyPercentJokerUsed());
        _prefsEditor.putBoolean(AppConstants.SETTINGS_PHONE_CALL_JOKER_USED, settings.isPhoneCallJokerUsed());

        _prefsEditor.commit();

    }

    public void saveGameState(int questionNumber) {
        SharedPreferences _prefs = context.getApplicationContext().getSharedPreferences(AppConstants.SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();


        Logging logging = new Logging();
        logging.debug("QUESTION NUMBER: " + questionNumber);
        _prefsEditor.putInt(AppConstants.SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY, questionNumber );
        _prefsEditor.commit();
    }

    public void saveGameState(int questionNumber, boolean audienceJokerUsed,
                              boolean fiftyPercentJokerUsed, boolean phoneCallJokerUsed) {
        SharedPreferences _prefs = context.getApplicationContext().getSharedPreferences(AppConstants.SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        _prefsEditor.putInt(AppConstants.SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY, questionNumber );
        _prefsEditor.putBoolean(AppConstants.SETTINGS_AUDIENCE_JOKER_USED, audienceJokerUsed);
        _prefsEditor.putBoolean(AppConstants.SETTINGS_FIFTY_PERCENT_JOKER_USED, fiftyPercentJokerUsed);
        _prefsEditor.putBoolean(AppConstants.SETTINGS_PHONE_CALL_JOKER_USED, phoneCallJokerUsed);
        _prefsEditor.commit();
    }

    public void resetGameState() {

        SharedPreferences _prefs = context.getApplicationContext().getSharedPreferences(AppConstants.SETTINGS_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        _prefsEditor.putInt(AppConstants.SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY, 1);
        _prefsEditor.putBoolean(AppConstants.SETTINGS_AUDIENCE_JOKER_USED, false);
        _prefsEditor.putBoolean(AppConstants.SETTINGS_FIFTY_PERCENT_JOKER_USED, false);
        _prefsEditor.putBoolean(AppConstants.SETTINGS_PHONE_CALL_JOKER_USED, false);

    }

    public GameSettingsService(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
