package com.greenlab.nmea_locator.tasks;

import android.app.Activity;
import android.os.Handler;

import com.greenlab.nmea_locator.event.TrackerEvent;
import com.greenlab.nmea_locator.trackers.GPSTracker;
import com.greenlab.nmea_locator.trackers.NMEATracker;
import com.greenlab.nmea_locator.trackers.Tracker;
import com.greenlab.nmea_locator.utils.Constants;



/**
 * Created by Metrolog on 27.10.14.
 */
public class CheckerTracker {

    private Tracker trackerA, trackerB;

    private boolean isDetectorTracker = false;
    private int errorValue = Constants.DEFAULT_ERROR;

    private Handler handler;

    public CheckerTracker(Activity activity) {
        trackerA = GPSTracker.getInstance(activity);
        trackerB = NMEATracker.getInstance(activity);
    }

    public void startCheck() {
        if (!isDetectorTracker) {
            trackerA.startUsing();
            trackerB.startUsing();
            new Thread(checkSubstitution).start();
            if(handler!=null) handler.sendEmptyMessage(TrackerEvent.START);
        }
    }

    public void stopCheck() {
        trackerA.stopUsing();
        trackerB.stopUsing();

        isDetectorTracker = false;
        if(handler!=null) handler.sendEmptyMessage(TrackerEvent.STOP);
    }

    Runnable checkSubstitution = new Runnable() {
        @Override
        public void run() {
            isDetectorTracker = true;
            while (isDetectorTracker) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((trackerA.getLatitude() == 0) && (trackerB.getLatitude() == 0)) {
                    if(handler!=null) handler.sendEmptyMessage(TrackerEvent.NOT_TOO_METHOD);
                    continue;
                }
                if ((trackerA.getLatitude() == 0) || (trackerB.getLatitude() == 0)) {
                    if(handler!=null) handler.sendEmptyMessage(TrackerEvent.NOT_ONE_METHOD);
                    continue;
                }
                if (isSubstitution()) {
                    if(handler!=null) handler.sendEmptyMessage(TrackerEvent.SUBSTITUTION);
                } else {
                    if(handler!=null) handler.sendEmptyMessage(TrackerEvent.NOT_SUBSTITUTION);
                }
            }
        }
    };

    private boolean isSubstitution() {
        double lenghtPath = distance(trackerA.getLatitude(), trackerA.getLongitude(), trackerB.getLatitude(), trackerB.getLongitude());

        if (lenghtPath > errorValue)
            return true;

        return false;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.853159616;
        return dist;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void setErrorValue(int errorValue) {
        this.errorValue = errorValue;
    }

}
