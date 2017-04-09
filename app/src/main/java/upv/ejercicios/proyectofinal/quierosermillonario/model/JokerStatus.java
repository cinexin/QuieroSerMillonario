package upv.ejercicios.proyectofinal.quierosermillonario.model;

/**
 * Created by migui on 0009.
 */

public class JokerStatus {

    private boolean audienceJokerUsed ;
    private boolean fiftyPercentJokerUsed ;
    private boolean phoneCallJokerUsed;
    private int numOfJokersUsed;

    public JokerStatus() {
        this.audienceJokerUsed = false;
        this.fiftyPercentJokerUsed = false;
        this.phoneCallJokerUsed = false;
        this.phoneCallJokerUsed = false;
        this.numOfJokersUsed = 0;
    }

    public JokerStatus(boolean audienceJokerUsed, boolean fiftyPercentJokerUsed, boolean phoneCallJokerUsed, int numOfJokersUsed) {
        this.audienceJokerUsed = audienceJokerUsed;
        this.fiftyPercentJokerUsed = fiftyPercentJokerUsed;
        this.phoneCallJokerUsed = phoneCallJokerUsed;
        this.numOfJokersUsed = numOfJokersUsed;
    }

    public boolean isAudienceJokerUsed() {
        return audienceJokerUsed;
    }

    public void setAudienceJokerUsed(boolean audienceJokerUsed) {
        this.audienceJokerUsed = audienceJokerUsed;
    }

    public boolean isFiftyPercentJokerUsed() {
        return fiftyPercentJokerUsed;
    }

    public void setFiftyPercentJokerUsed(boolean fiftyPercentJokerUsed) {
        this.fiftyPercentJokerUsed = fiftyPercentJokerUsed;
    }

    public boolean isPhoneCallJokerUsed() {
        return phoneCallJokerUsed;
    }

    public void setPhoneCallJokerUsed(boolean phoneCallJokerUsed) {
        this.phoneCallJokerUsed = phoneCallJokerUsed;
    }

    public int getNumOfJokersUsed() {
        return numOfJokersUsed;
    }

    public void setNumOfJokersUsed(int numOfJokersUsed) {
        this.numOfJokersUsed = numOfJokersUsed;
    }

    public void increaseNumOfJokersUsed() {
        this.numOfJokersUsed++;
    }

    @Override
    public String toString() {
        return "JokerStatus{" +
                "audienceJokerUsed=" + audienceJokerUsed +
                ", fiftyPercentJokerUsed=" + fiftyPercentJokerUsed +
                ", phoneCallJokerUsed=" + phoneCallJokerUsed +
                ", numOfJokersUsed=" + numOfJokersUsed +
                '}';
    }
}
