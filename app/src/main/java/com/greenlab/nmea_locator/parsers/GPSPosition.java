package com.greenlab.nmea_locator.parsers;

public class GPSPosition {

    public String LatNS;
    public String LonEW;
    public float altitude;
    public float dir;
    public boolean fixed;
    public String gpType;
    public float lat;
    public float lon;
    public int quality;
    public int satellites;
    public float speed;
    public String time;

    public GPSPosition() {
        this.time = "";
        this.lat = 0.0F;
        this.lon = 0.0F;
        this.LatNS = "";
        this.LonEW = "";
        this.fixed = false;
        this.quality = 0;
        this.dir = 0.0F;
        this.altitude = 0.0F;
        this.speed = 0.0F;
        this.satellites = 0;
        this.gpType = "";
    }

}
