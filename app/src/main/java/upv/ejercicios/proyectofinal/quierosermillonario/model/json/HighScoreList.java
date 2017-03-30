package upv.ejercicios.proyectofinal.quierosermillonario.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migui on 0030.
 */

public class HighScoreList {

    @SerializedName("items")
    private List<HighScoreRecord> scores;

    public List<HighScoreRecord> getScores() {
        return scores;
    }

    public void setScores(List<HighScoreRecord> scores) {
        this.scores = scores;
    }

}
