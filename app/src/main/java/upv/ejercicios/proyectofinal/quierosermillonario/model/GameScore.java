package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0018.
 */

public class GameScore {
    int lastQuestionAnswered;
    int moneyAchieved;
    int playingFor;
    int moneyEnsured; // 5th and 10th questions ensures you a quantity (500 € / 16.000 €)

    public GameScore() {

    }

    public GameScore(int lastQuestionAnswered, int moneyAchieved, int playingFor, int moneyEnsured) {
        this.lastQuestionAnswered = lastQuestionAnswered;
        this.moneyAchieved = moneyAchieved;
        this.playingFor = playingFor;
        this.moneyEnsured = moneyEnsured;
    }

    public int getLastQuestionAnswered() {
        return lastQuestionAnswered;
    }

    public void setLastQuestionAnswered(int lastQuestionAnswered) {
        this.lastQuestionAnswered = lastQuestionAnswered;
    }

    public int getMoneyAchieved() {
        return moneyAchieved;
    }

    public void setMoneyAchieved(int moneyAchieved) {
        this.moneyAchieved = moneyAchieved;
    }

    public int getPlayingFor() {
        return playingFor;
    }

    public void setPlayingFor(int playingFor) {
        this.playingFor = playingFor;
    }

    public int getMoneyEnsured() {
        return moneyEnsured;
    }

    public void setMoneyEnsured(int moneyEnsured) {
        this.moneyEnsured = moneyEnsured;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "lastQuestionAnswered=" + lastQuestionAnswered +
                ", moneyAchieved=" + moneyAchieved +
                ", playingFor=" + playingFor +
                ", moneyEnsured=" + moneyEnsured +
                '}';
    }
}
