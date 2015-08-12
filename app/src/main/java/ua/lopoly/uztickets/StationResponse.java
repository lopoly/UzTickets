package ua.lopoly.uztickets;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Created by lopoly on 10.08.2015.
 */

public class StationResponse {

    ArrayList<Station> mStations;
    public StationResponse(){

    }

    @JsonProperty("value")
    public ArrayList<Station> getStations() {
        return mStations;
    }

    @JsonProperty("value")
    public void setStations(ArrayList<Station> stations) {
        mStations = stations;
    }

    @Override
    public String toString() {
        return "StationResponse{" +
                "mStations=" + mStations +
                '}';
    }
}

class Station {
    String mTitle;
    String mStationId;

    @Override
    public String toString() {
        return mTitle;
    }

    @JsonProperty("title")
    public String getTitle() {
        return mTitle;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        mTitle = title;
    }

    @JsonProperty("station_id")
    public String getStationId() {
        return mStationId;
    }

    @JsonProperty("station_id")
    public void setStationId(String stationId) {
        mStationId = stationId;
    }

}


