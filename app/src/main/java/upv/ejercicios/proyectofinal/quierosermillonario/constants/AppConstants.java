package upv.ejercicios.proyectofinal.quierosermillonario.constants;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by migui on 0016.
 * This class will contain global App internal Constants
 * ie: Settings file name(s), database name(s), table name(s) / fields
 */

public class AppConstants extends Object {
    // Settings global constants (file name + fields keys)
    public static final String SETTINGS_FILE_NAME = "settings";
    public static final String SETTINGS_USERNAME_KEY = "Username";
    public static final String SETTINGS_NUMBER_OF_JOKERS_KEY = "NumberOfJokers";
    public static final String SETTINGS_LOCALIZATION_LONGITUDE_KEY = "Longitude";
    public static final String SETTINGS_LOCALIZATION_LATITUDE_KEY = "Latitude";
    public static final String SETTINGS_GAME_STATUS_CURRENT_QUESTION_KEY = "CurrentQuestion";

    // Game constants
    public static final int MAX_NUMBER_OF_JOKERS = 3;
    /*
        Position in the array = number of question
        QUESTIONS_VALUE[number_of_question] = value of the question when answered correctly
     */
    public static final int[] QUESTIONS_VALUE = {0,100,200,300,500, 1000,
        2000,4000,8000,16000, 32000,
        64000,125000,250000,500000,1000000};
    public static final int NUMBER_OF_QUESTIONS = 15;
    public static final int FIRST_MILESTONE = 5;
    public static final int SECOND_MILESTONE = 10;




}
