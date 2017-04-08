package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0018.
 */

public class GameScore {
    int lastQuestionAnswered;
    int moneyAchieved; // that is, the "score"
    int playingFor; // the value of the current question
    int moneyEnsured; // 5th and 10th questions ensures you a quantity (500 € / 16.000 €)
    String userName; // user name in case of "non-anonymous" gamer
    float longitude; // user's location (longitude)
    float latitude; // user's location (latitude)
    String userFriendlyLocationName;

    public GameScore() {

    }

    public GameScore(int lastQuestionAnswered, int moneyAchieved, int playingFor, int moneyEnsured) {
        this.lastQuestionAnswered = lastQuestionAnswered;
        this.moneyAchieved = moneyAchieved;
        this.playingFor = playingFor;
        this.moneyEnsured = moneyEnsured;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public GameScore(int lastQuestionAnswered, int moneyAchieved, int playingFor, int moneyEnsured, String userName, float longitude, float latitude) {
        this.lastQuestionAnswered = lastQuestionAnswered;
        this.moneyAchieved = moneyAchieved;
        this.playingFor = playingFor;
        this.moneyEnsured = moneyEnsured;
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getUserFriendlyLocationName() {
        return userFriendlyLocationName;
    }

    public void setUserFriendlyLocationName(String userFriendlyLocationName) {
        this.userFriendlyLocationName = userFriendlyLocationName;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "lastQuestionAnswered=" + lastQuestionAnswered +
                ", moneyAchieved=" + moneyAchieved +
                ", playingFor=" + playingFor +
                ", moneyEnsured=" + moneyEnsured +
                ", userName = " + userName +
                ", longitude = " + longitude +
                ", latitude = " + latitude +
                ", userFriendlyLocationName = " + userFriendlyLocationName +
        '}';
    }
}
