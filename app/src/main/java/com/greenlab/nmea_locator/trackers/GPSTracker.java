package com.greenlab.nmea_locator.trackers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.greenlab.nmea_locator.utils.Constants;

/**
 * Created by Metrolog on 27.10.14.
 */

public class GPSTracker extends Tracker implements LocationListener {

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;

    private static GPSTracker _instance = null;

    private GPSTracker(Context context) {
        super(context);
    }

    public static synchronized GPSTracker getInstance(Context context) {
        if (_instance == null)
            _instance = new GPSTracker(context);
        return _instance;
    }

    public Location startUsing() {

        try {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            if (locationManager == null) {
                Log.d(Constants.TAG + " isGPSEnabled", "locationManager null");
            }

            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d(Constants.TAG + " isGPSEnabled", "" + isGPSEnabled);

            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d(Constants.TAG + " isNetworkEnabled", "" + isNetworkEnabled);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d(Constants.TAG + " info", " no network provider is enabled");
                return null;

            } else {
                this.canGetLocation = true;
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            Constants.MIN_TIME_BW_UPDATES,
                            Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(Constants.TAG + " GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                        Log.d(Constants.TAG + " location,GPS", "" + location);
                        if (location != null) {
                            Log.d(Constants.TAG + " location", "" + "enter");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if ((isNetworkEnabled) || (location == null)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            Constants.MIN_TIME_BW_UPDATES,
                            Constants.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(Constants.TAG + " Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        Log.d(Constants.TAG + " location", "" + location);
                        if (location != null) {
                            Log.d(Constants.TAG + " location", "" + "enter");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void stopUsing() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
            onLocationChanged(location);
        }
        return latitude;
    }

    @Override
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        Log.d(Constants.TAG + "_gps", latitude + " " + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}