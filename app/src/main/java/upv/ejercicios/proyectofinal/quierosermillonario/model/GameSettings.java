package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0016.
 * Model Object for "Settings"
 */

public class GameSettings extends Object {
    private String userName;
    private int numberOfJokers;
    private float longitude;
    private float latitude;
    private int currentQuestion = 1;
    private int[] amountWithQuestionRelationship;

    public GameSettings() {

    }

    public GameSettings(String userName, int numberOfJokers, float longitude, float latitude) {
        this.userName = userName;
        this.numberOfJokers = numberOfJokers;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GameSettings(String userName, int numberOfJokers, float longitude, float latitude, int currentQuestion) {
        this.userName = userName;
        this.numberOfJokers = numberOfJokers;
        this.longitude = longitude;
        this.latitude = latitude;
        this.currentQuestion = currentQuestion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumberOfJokers() {
        return numberOfJokers;
    }

    public void setNumberOfJokers(int numberOfJokers) {
        this.numberOfJokers = numberOfJokers;
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

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "userName='" + userName + '\'' +
                ", numberOfJokers=" + numberOfJokers +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", currentQuestion = " + currentQuestion +
                '}';
    }
}
