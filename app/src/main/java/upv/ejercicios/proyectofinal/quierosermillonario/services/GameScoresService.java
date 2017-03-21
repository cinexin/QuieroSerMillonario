package upv.ejercicios.proyectofinal.quierosermillonario.services;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;
import upv.ejercicios.proyectofinal.quierosermillonario.utils.Logging;

/**
 * Created by migui on 0018.
 */
// TODO
public class GameScoresService {

    private GameScore gameScore;

    public GameScore getGameScore() {
        return gameScore;
    }

    public void setGameScore(GameScore gameScore) {
        this.gameScore = gameScore;
    }

    public GameScoresService(GameScore gameScore) {
        this.gameScore = gameScore;
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
    // TODO: Implement...
    public void saveScore() {

    }
}
