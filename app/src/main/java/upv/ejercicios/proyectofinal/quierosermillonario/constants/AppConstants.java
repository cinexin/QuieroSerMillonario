package upv.ejercicios.proyectofinal.quierosermillonario.constants;


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
    // cause of game ending
    public static final int CONTESTANT_FAILED_QUESTION = 1;
    public static final int CONTESTANT_ABANDONED_GAME = 2;
    public static final String DEFAULT_YES_NO_MSG = "Are you sure?";

    // Auxiliary resources locations / names
    public static final String QUESTIONS_FILE_NAME = "questions";
    // XML attributes and tags names
    public static final String XML_TAG_QUESTION = "question";
    public static final String XML_ATTR_QUESTION_TEXT = "text";
    public static final String XML_ATTR_QUESTION_NUMBER = "number";
    public static final String XML_ATTR_ANSWER1 = "answer1";
    public static final String XML_ATTR_ANSWER2 = "answer2";
    public static final String XML_ATTR_ANSWER3 = "answer3";
    public static final String XML_ATTR_ANSWER4 = "answer4";
    public static final String XML_ATTR_AUDIENCE_ANSWER = "audience";
    public static final String XML_ATTR_FIFTY1_ANSWER = "fifty1";
    public static final String XML_ATTR_FIFTY2_ANSWER = "fifty2";
    public static final String XML_ATTR_PHONE_ANSWER = "phone";
    public static final String XML_ATTR_RIGHT_ANSWER = "right";

    // Database constants
    public static final String DATABASE_NAME = "WhoWantsToBeMillionaire.db";
    public static final int DATABASE_DEFAULT_VERSION = 1;
    public static final int DATABASE_OPEN_READ_MODE = 1;
    public static final int DATABASE_OPEN_WRITE_MODE = 2;
    public static final String DATABASE_SCORES_TABLE = "Score";
    public static final String DATABASE_DEFAULT_USER_NAME = "anonymous";
    public static final String DATABASE_USERNAME_FIELD = "name";
    public static final String DATABASE_SCORE_FIELD = "score";
    public static final String DATABASE_LONGITUDE_FIELD = "longitude";
    public static final String DATABASE_LATITUDE_FIELD = "latitude";

    // Network connection constants
    public static final int CONNECTION_TYPE_3G = 1;
    public static final int CONNECTION_TYPE_WIFI = 2;

    public static final String HTTP_POST_METHOD = "POST";
    public static final String HTTP_GET_METHOD = "GET";
    public static final String HTTP_PUT_METHOD = "PUT";
    public static final String HTTP_DELETE_METHOD = "DELETE";

    public static final String URL_ADD_NEW_FRIEND = "https://wwtbam-1076.appspot.com/_ah/api/friends/v1/add";
    public static final String URL_REGISTER_HIGH_SCORE = "https://wwtbam-1076.appspot.com/_ah/api/highscores/v1/new";
    public static final String URL_GET_HIGH_SCORES = "https://wwtbam-1076.appspot.com/_ah/api/highscores/v1/friends";
    public static final String URL_USER_NAME_PARAMETER = "name";
    public static final String URL_USER_FRIEND_NAME_PARAMETER = "friend_name";
    public static final String URL_USER_SCORE_PARAMETER = "score";
    public static final String URL_USER_LONGITUDE_PARAMETER = "longitude";
    public static final String URL_USER_LATITUDE_PARAMETER = "latitude";

    // Localization related constants...
    public static final int STATUS_LOCATE = 0;
    public static final int STATUS_DONT_LOCATE = 1;


}
