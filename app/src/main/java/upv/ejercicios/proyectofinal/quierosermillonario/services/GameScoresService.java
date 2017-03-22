package upv.ejercicios.proyectofinal.quierosermillonario.services;

import android.content.Context;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.database.DBService;
import upv.ejercicios.proyectofinal.quierosermillonario.exception.PersistenceException;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;

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
            if (gameScore.getLastQuestionAnswered() >= 5 &&
                    gameScore.getLastQuestionAnswered() < 10) {
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[AppConstants.FIRST_MILESTONE]);
            } else if (gameScore.getLastQuestionAnswered() >= 10){
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
                dbService.save(this.gameScore);
                dbService.closeSession();
            } catch (PersistenceException persistenceEx) {
                log.error("Error while persisting score onto database");
                persistenceEx.printStackTrace();
                throw persistenceEx;
            }
        }
    }
}
