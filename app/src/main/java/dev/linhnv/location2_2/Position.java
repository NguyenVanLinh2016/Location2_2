package dev.linhnv.location2_2;

/**
 * Created by linhnv on 01/05/2017.
 */

public class Position {
    private String latitude;
    private String longtitude;

    public Position(){

    }
    public Position(String latitude, String longtitude){
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongtitude(){
        return longtitude;
    }
    public void setLongtitude(String longtitude){
        this.longtitude = longtitude;
    }
}
