package dev.linhnv.location2_2;

import android.util.Log;

/**
 * Created by linhnv on 01/05/2017.
 */

public class Position {
    private final String TAG = "Position.class";
    private String latitude;
    private String longtitude;

    public Position(){

    }

    public Position(String latitude, String longtitude){
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    private void print(String msg) {
        Log.d(TAG, "print: " + msg);
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
