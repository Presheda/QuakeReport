package com.example.android.quakereport;

/**
 * Created by Precious on 9/24/2017.
 */

public class Earthquake {

    public  Double magnitude;
    public String location;
    public Long TimeInMilliseconds;
    public String url;

    public Earthquake(double mag, String location, Long Hour, String url){
        magnitude = mag;
       this.location = location;
        TimeInMilliseconds = Hour;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public Long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }
}
