package com.greenlab.nmea_locator.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.greenlab.nmea_locator.R;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TrackerFragment recordFragment = new TrackerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.field_for_tracker, recordFragment)
                .commit();
    }

}
