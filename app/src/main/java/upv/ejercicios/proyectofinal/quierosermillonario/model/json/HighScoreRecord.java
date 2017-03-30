package upv.ejercicios.proyectofinal.quierosermillonario.model.json;

/**
 * Created by migui on 0030.
 */

public class HighScoreRecord implements Comparable<HighScoreRecord> {
    String name;
    String scoring;
    String longitude;
    String latitude;

    public HighScoreRecord() {
    }

    public HighScoreRecord(String name, String scoring, String longitude, String latitude) {
        this.name = name;
        this.scoring = scoring;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public int compareTo(HighScoreRecord o) {
        return compareTo(Integer.parseInt(o.getScoring()));
    }

    public int compareTo(String score) {
        return compareTo(Integer.parseInt(score));
    }

    public int compareTo(int score) {
        if (Integer.parseInt(this.getScoring()) > score) {
            return 1;
        }
        else if (Integer.parseInt(this.getScoring()) < score) {
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "HighScoreRecord{" +
                "name='" + name + '\'' +
                ", scoring='" + scoring + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
