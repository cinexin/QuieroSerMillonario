package upv.ejercicios.proyectofinal.quierosermillonario.model.taskParams;

import java.util.List;

import upv.ejercicios.proyectofinal.quierosermillonario.model.GameScore;

/**
 * Created by migui on 0008.
 */

public class HighScoresTableParams {
    List<GameScore> gameScoreList;
    boolean remote; // true = remote scores / false = local scores

    public HighScoresTableParams() {
    }

    public HighScoresTableParams(List<GameScore> gameScoreList, boolean remote) {
        this.gameScoreList = gameScoreList;
        this.remote = remote;
    }

    public List<GameScore> getGameScoreList() {
        return gameScoreList;
    }

    public void setGameScoreList(List<GameScore> gameScoreList) {
        this.gameScoreList = gameScoreList;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
