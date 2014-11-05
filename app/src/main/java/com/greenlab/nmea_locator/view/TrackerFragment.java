package com.greenlab.nmea_locator.view;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greenlab.nmea_locator.R;
import com.greenlab.nmea_locator.event.TrackerEvent;
import com.greenlab.nmea_locator.tasks.CheckerTracker;

/**
 * Created by Metrolog on 27.10.14.
 */

public class TrackerFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private TextView status;
    private EditText errorValue;
    private RelativeLayout backgroundLayout;

    private CheckerTracker checkerTracker;
    private Handler handler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tracker_layout, container, false);
        initUI(view);
        if ((savedInstanceState != null))
            errorValue.setText(savedInstanceState.getString("error"));
        return view;
    }

    private void initUI(View view) {
        backgroundLayout = (RelativeLayout) view.findViewById(R.id.background);
        status = (TextView) view.findViewById(R.id.status);
        errorValue = (EditText) view.findViewById(R.id.error);
        errorValue.addTextChangedListener(this);

        initHandler();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkerTracker = new CheckerTracker(getActivity());
        checkerTracker.setHandler(handler);
        startDetector();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDetector();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("error", errorValue.getText().toString());
    }

    private void startDetector() {
        checkerTracker.startCheck();
    }

    private void stopDetector() {
        checkerTracker.stopCheck();
    }

    @Override
    public void onClick(View view) {
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void initHandler() {
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case TrackerEvent.START:
                        status.setText("Идет поиск сетей");
                        break;
                    case TrackerEvent.STOP:
                        backgroundLayout.setBackgroundResource(R.color.white);
                        status.setText("Поиск остановлен");
                        break;
                    case TrackerEvent.SUBSTITUTION:
                        backgroundLayout.setBackgroundResource(R.color.scarlet);
                        status.setText("Погрешность превышает допустимую");
                        break;
                    case TrackerEvent.NOT_SUBSTITUTION:
                        backgroundLayout.setBackgroundResource(R.color.white);
                        status.setText("Идет получение геоданных");
                        break;
                    case TrackerEvent.NOT_TOO_METHOD:
                        backgroundLayout.setBackgroundResource(R.color.white);
                        status.setText("Не удается включить геолокацию");
                        break;
                    case TrackerEvent.NOT_ONE_METHOD:
                        backgroundLayout.setBackgroundResource(R.color.white);
                        status.setText("Один из способов получения геоданных не работает");
                        break;
                }
            };
        };
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        try {
            if (checkerTracker != null)
                checkerTracker.setErrorValue(Integer.valueOf(String.valueOf(charSequence)));
        } catch (Exception e) {
            checkerTracker.setErrorValue(100);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }


}
