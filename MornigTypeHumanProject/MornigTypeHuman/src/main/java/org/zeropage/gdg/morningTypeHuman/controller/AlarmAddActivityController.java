package org.zeropage.gdg.morningTypeHuman.controller;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.model.AlarmServiece;
import org.zeropage.gdg.morningTypeHuman.view.AlarmAddActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmAddActivityController implements TimePicker.OnTimeChangedListener, GoogleMap.OnCameraChangeListener, View.OnClickListener {
    private AlarmAddActivity activity;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;

    public AlarmAddActivityController(AlarmAddActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        latitude = cameraPosition.target.latitude;
        longitude = cameraPosition.target.longitude;
    }

    /**
     * 근래에 짠 코드중 가장 만에 안드는 코드가 나왔다.. 아,,,
     */
    @Override
    public void onClick(View view) {
        EditText editText = (EditText) activity.findViewById(R.id.editTextLectureName);
        Editable et = editText.getEditableText();
        if (et == null || et.toString().equals("")) {
            Toast.makeText(activity, "강의명을 입력해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }
        String name = et.toString();


        ArrayList<AlarmInfo> load;
        try {
            load = AlarmInfoStorage.load();
        } catch (IOException e) {
            Toast.makeText(activity, "FATAL_ERROR:기존 알람 목록을 불러오는데 실패.", Toast.LENGTH_LONG).show();
            return;
        }

        AlarmInfo newAlarm = new AlarmInfo(name, hour, minute, longitude, latitude);
        load.add(newAlarm);
        try {
            AlarmInfoStorage.save(load);
        } catch (IOException e) {
            Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
            return;
        }
        activateAlarm(newAlarm);
        activity.finish();
    }

    private void activateAlarm(AlarmInfo newAlarm) {
        AlarmServiece alarmServiece = new AlarmServiece();
        try {
            alarmServiece.enableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
