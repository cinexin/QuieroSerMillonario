package upv.ejercicios.proyectofinal.quierosermillonario.services;

import java.util.Map;

import upv.ejercicios.proyectofinal.quierosermillonario.constants.AppConstants;
import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;

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

    // TODO: refreshGameScores

    public void refereshGameScores() {
        if (this.gameScore != null) {
            gameScore.setMoneyAchieved(gameScore.getMoneyAchieved() + AppConstants.QUESTIONS_VALUE[gameScore.getLastQuestionAnswered()]);
            gameScore.setPlayingFor(AppConstants.QUESTIONS_VALUE[gameScore.getLastQuestionAnswered() + 1]);
            if (gameScore.getLastQuestionAnswered() >= 5 &&
                    gameScore.getLastQuestionAnswered() < 10) {
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[AppConstants.FIRST_MILESTONE]);
            } else if (gameScore.getLastQuestionAnswered() >= 10){
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[AppConstants.SECOND_MILESTONE]);
            } else {
                gameScore.setMoneyEnsured(AppConstants.QUESTIONS_VALUE[0]); // yes: zero :)
            }

        }
    }
    public void nextQuestion() {

        this.refereshGameScores();
        gameScore.setLastQuestionAnswered(gameScore.getLastQuestionAnswered() + 1);
    }
}
