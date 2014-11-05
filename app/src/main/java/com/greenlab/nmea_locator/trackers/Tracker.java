package com.greenlab.nmea_locator.trackers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Created by Metrolog on 27.10.14.
 */

public abstract class Tracker {

    protected final Context context;

    protected double latitude;

    protected double longitude;

    protected LocationManager locationManager;

    protected Tracker(Context _context) {
        this.context = _context;
        startUsing();
    }

    public abstract Location startUsing();

    public abstract void stopUsing();

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void showSettingsAlert() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity((intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

}
