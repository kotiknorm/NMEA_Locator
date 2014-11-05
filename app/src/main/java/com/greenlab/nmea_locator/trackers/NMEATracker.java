package com.greenlab.nmea_locator.trackers;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.greenlab.nmea_locator.parsers.GPSPosition;
import com.greenlab.nmea_locator.parsers.NMEAParser;
import com.greenlab.nmea_locator.utils.Constants;

/**
 * Created by Metrolog on 27.10.14.
 */

public class NMEATracker extends Tracker implements GpsStatus.NmeaListener {

    private static NMEATracker _instance = null;

    private NMEAParser nmeaParser = new NMEAParser();

    private NMEATracker(Context _context) {
        super(_context);
    }

    public static synchronized NMEATracker getInstance(Context context) {
        if (_instance == null)
            _instance = new NMEATracker(context);
        return _instance;
    }

    public Location startUsing() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addNmeaListener(this);
        return null;
    }

    public void stopUsing() {
        if (locationManager != null) {
            locationManager.removeNmeaListener(this);
        }
    }

    public void onNmeaReceived(long timestamp, String nmea) {
        GPSPosition gpsPosition = nmeaParser.parse(nmea);
        latitude = gpsPosition.lat;
        longitude = gpsPosition.lon;
        Log.d(Constants.TAG + "_nmea", latitude + " " + longitude);
    }
}
