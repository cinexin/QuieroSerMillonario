package upv.ejercicios.proyectofinal.quierosermillonario.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.database.DBService;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.model.json.HighScoreList;
import upv.ejercicios.proyectofinal.quierosermillonario.model.json.HighScoreRecord;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.HttpUtils;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.StringUtils;

/**
 * Created by migui on 0018.
 */
//
public class GameScoresService {

    private GameScore gameScore;
    private Context appContext;

    public GameScore getGameScore() {
        return gameScore;
    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public void setGameScore(GameScore gameScore) {
        this.gameScore = gameScore;

    }

    public GameScoresService(Context appContext) {
        this.appContext = appContext;
    }

    public GameScoresService(GameScore gameScore, Context appContext) {
        this.gameScore = gameScore;
        this.appContext = appContext;
    }

    // refreshGameScores

    public void refreshGameScore() {
        if (this.gameScore != null) {
            Logging logging = new Logging();
            logging.debug("Refresh game scores initial state: " + gameScore.toString());
            gameScore.setMoneyAchieved(AppConstants.QUESTIONS_VALUE[gameScore.getLastQuestionAnswered()]);
            gameScore.setPlayingFor(AppConstants.QUESTIONS_VALUE[gameScore.getLastQuestionAnswered() + 1]);
            if (gameScore.getLastQuestionAnswered() >= AppConstants.FIRST_MILESTONE &&
                    gameScore.getLastQuestionAnswered() < AppConstants.SECOND_MILESTONE) {
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[AppConstants.FIRST_MILESTONE]);
            } else if (gameScore.getLastQuestionAnswered() >= AppConstants.SECOND_MILESTONE){
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[AppConstants.SECOND_MILESTONE]);
            } else {
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[0]); // yes: zero :)
            }
            logging.debug("Refresh game scores final state: " + gameScore.toString());
        }
    }
    public void nextQuestion() {
        gameScore.setLastQuestionAnswered(gameScore.getLastQuestionAnswered() + 1);
        this.refreshGameScore();

    }
    // DONE: Call databaseService and save our score...
    public void saveScore() throws PersistenceException{
        DBService dbService = new DBService(AppConstants.DATABASE_NAME, this.getAppContext(), AppConstants.DATABASE_OPEN_WRITE_MODE);
        Logging log = new Logging();
        if (dbService != null && this.gameScore != null) {
            try {
                Log.d("[DEBUG]", "saveScore() -> GAME SCORE: " + gameScore.toString());
                dbService.save(this.gameScore);
                dbService.closeSession();
            } catch (PersistenceException persistenceEx) {
                log.error("Error while persisting score onto database");
                persistenceEx.printStackTrace();
                throw persistenceEx;
            }
        }
    }

    /*
        Register highest score via HTTP PUT WebService (it will register the highest score of you and your friends)
        PS: Only in case your score > all you and your friends registered scores at the moment
      */
    public void saveHighScore() throws IOException {
        Logging logging = new Logging();
        if (this.getAppContext() == null || this.getGameScore() == null) {
            String errMsg = this.getClass().getName() + ". Context / GameScore is null. Please, initialize object first.";
            logging.error(errMsg);
            throw new IOException(errMsg);
        }

        HttpUtils httpUtils = new HttpUtils(this.getAppContext());
        List<NameValuePair> saveHighScoreParams = new ArrayList<NameValuePair>();
        saveHighScoreParams.add(new BasicNameValuePair(AppConstants.URL_USER_NAME_PARAMETER,
                this.getGameScore().getUserName()));
        saveHighScoreParams.add(new BasicNameValuePair(AppConstants.URL_USER_SCORE_PARAMETER,
                String.valueOf(this.getGameScore().getMoneyAchieved())));
        saveHighScoreParams.add(new BasicNameValuePair(AppConstants.URL_USER_LONGITUDE_PARAMETER,
                String.valueOf(this.getGameScore().getLongitude())));
        saveHighScoreParams.add(new BasicNameValuePair(AppConstants.URL_USER_LATITUDE_PARAMETER,
                String.valueOf(this.getGameScore().getLatitude())));

        try {
            logging.debug("CALLING URL: " + AppConstants.URL_REGISTER_HIGH_SCORE + " with Score: " + this.getGameScore().toString());
            httpUtils.postRequest(AppConstants.URL_REGISTER_HIGH_SCORE, AppConstants.HTTP_PUT_METHOD, saveHighScoreParams, false);
        } catch (IOException ioEx) {
            logging.error(getClass().getName() + ":" + "Error while trying to register remote High Scores. " + ioEx.getMessage());
            throw ioEx;
        }



    }

    public List<GameScore> getUserScores(String userName) throws PersistenceException {
        List<Object> gameScores = new ArrayList<>();
        DBService dbService = new DBService(AppConstants.DATABASE_NAME, this.getAppContext(), AppConstants.DATABASE_OPEN_READ_MODE);
        Logging logging = new Logging();
        if (dbService!= null) {
            try {
                if (StringUtils.isEmpty(userName))
                    gameScores =  dbService.getByField(AppConstants.DATABASE_USERNAME_FIELD, AppConstants.DATABASE_DEFAULT_USER_NAME,
                            AppConstants.DATABASE_SCORE_FIELD, AppConstants.DATABASE_SCORES_TABLE);
                else
                    gameScores = dbService.getByField(AppConstants.DATABASE_USERNAME_FIELD, userName,
                            AppConstants.DATABASE_SCORE_FIELD, AppConstants.DATABASE_SCORES_TABLE);

            } catch (PersistenceException persistenceEx) {
                logging.error("Error while retrieving user scores from database");
                throw persistenceEx;
            } finally {
                dbService.closeSession();
            }
        }

        return (List<GameScore>) (List<?>) gameScores;
    }

    public List<GameScore> getAllScores() throws PersistenceException {
        List<Object> gameScores = new ArrayList<>();
        DBService dbService = new DBService(AppConstants.DATABASE_NAME, this.getAppContext(), AppConstants.DATABASE_OPEN_READ_MODE);
        Logging logging = new Logging();


        if (dbService!= null) {
            try {
                gameScores = dbService.getAll(AppConstants.DATABASE_SCORES_TABLE);

            } catch (PersistenceException persistenceEx) {
                logging.error("Error while retrieving user scores from database");
                throw persistenceEx;
            } finally {
                dbService.closeSession();
            }
        }
        return (List<GameScore>) (List<?>) gameScores;
    }

    // DONE: Implement (do the remote WS-REST call and parse the results)
    public List<GameScore> getRemoteHighScores(String userName) throws IOException, JSONException{
        List<GameScore> gameScores = new ArrayList<GameScore>();
        Logging logging = new Logging();

        if (this.getAppContext() == null) {
            String errMsg = getClass().getName() + ". Context is null, please initialize properly the class first";
            logging.error(errMsg);
            throw new IOException(errMsg);
        }

        HttpUtils httpUtils = new HttpUtils(this.getAppContext());
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new BasicNameValuePair(AppConstants.URL_USER_NAME_PARAMETER, userName));
        try {
            String response = httpUtils.postRequest(AppConstants.URL_GET_HIGH_SCORES, AppConstants.HTTP_GET_METHOD, requestParams, true);
            logging.debug(getClass().getName() + ": Response from the server (plain): " + response);
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(response);
            HighScoreList highScoreList = gson.fromJson(jsonObject.toString(), HighScoreList.class);
            List<HighScoreRecord> jsonHighScores = highScoreList.getScores();
            if (jsonHighScores != null) {
                Collections.sort(jsonHighScores);
                gameScores = fromHighScoreJSONListToGameScoreList(jsonHighScores);
                Collections.reverse(gameScores); // we want descending order to be displayed
            }
        } catch (IOException ioEx) {
            logging.error(this.getClass().getName() + ". I/O Exception: " + ioEx.getMessage());
            throw ioEx;
        } catch (JSONException jsonEx) {
            logging.error(this.getClass().getName() + ". error while parsing JSON response from server. " + jsonEx.getMessage());
            throw jsonEx;
        }

        return gameScores;

    }
    // scores clearing functionality
    public void clearScores() throws PersistenceException {
        DBService dbService = new DBService(AppConstants.DATABASE_NAME, this.getAppContext(), AppConstants.DATABASE_OPEN_WRITE_MODE);
        Logging logging = new Logging();
        try {
            dbService.deleteAllRows(AppConstants.DATABASE_SCORES_TABLE);

        } catch (PersistenceException persistenceEx) {
            logging.error("Error while removing scores from database. Caused by: " + persistenceEx.getMessage());
            persistenceEx.printStackTrace();
        } finally {
            dbService.closeSession();
        }


    }

    /*
        This is an auxiliary method to convert a List of HighScoreRecord (specific class to handle JSON response) to:
        GameScore (our standard model class)
     */
    private List<GameScore> fromHighScoreJSONListToGameScoreList(List<HighScoreRecord> jsonHighScoreList) {
        List<GameScore> gameScoreList = new ArrayList<>();

        for (HighScoreRecord highScore : jsonHighScoreList) {
            GameScore gameScore = new GameScore();

            if (!StringUtils.isEmpty(highScore.getName()))
                gameScore.setUserName(highScore.getName());
            if (!StringUtils.isEmpty(highScore.getScoring()))
                gameScore.setMoneyAchieved(Integer.valueOf(highScore.getScoring()).intValue());
            if (!StringUtils.isEmpty(highScore.getLongitude()))
                gameScore.setLongitude(Float.valueOf(highScore.getLongitude()).floatValue());
            if (!StringUtils.isEmpty(highScore.getLatitude()))
                gameScore.setLatitude(Float.valueOf(highScore.getLatitude()).floatValue());

            gameScoreList.add(gameScore);
        }

        return gameScoreList;
    }
}
