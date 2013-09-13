package org.zeropage.gdg.morningTypeHuman.controller;

import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.model.AlarmService;
import org.zeropage.gdg.morningTypeHuman.model.DayOfWeek;
import org.zeropage.gdg.morningTypeHuman.view.AlarmAddActivity;

import java.io.IOException;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmAddActivityController implements TimePicker.OnTimeChangedListener, GoogleMap.OnCameraChangeListener, View.OnClickListener, CheckBox.OnCheckedChangeListener {
    private AlarmAddActivity activity;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;
    private boolean isAlarmOn;
    private DayOfWeek dayOfWeek;

    public AlarmAddActivityController(AlarmAddActivity activity) {
        this.activity = activity;
        dayOfWeek = new DayOfWeek();
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
        if (!(dayOfWeek.mon || dayOfWeek.tue || dayOfWeek.wed || dayOfWeek.thu || dayOfWeek.fri || dayOfWeek.sat || dayOfWeek.sun)) {
            Toast.makeText(activity, "요일을 선택해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        AlarmInfo newAlarm = new AlarmInfo(name, hour, minute, longitude, latitude, isAlarmOn, dayOfWeek);
        try {
            AlarmInfoStorage.saveAlarmInfo(newAlarm);
        } catch (IOException e) {
            Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
            return;
        }
        if(isAlarmOn) {
            activateAlarm(newAlarm);
        }
        activity.finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        if(viewId == R.id.checkBoxMon) {
            dayOfWeek.mon = isChecked;
        } else if(viewId == R.id.checkBoxTue) {
            dayOfWeek.tue = isChecked;
        } else if(viewId == R.id.checkBoxWed) {
            dayOfWeek.wed = isChecked;
        } else if(viewId == R.id.checkBoxThu) {
            dayOfWeek.thu = isChecked;
        } else if(viewId == R.id.checkBoxFri) {
            dayOfWeek.fri = isChecked;
        } else if(viewId == R.id.checkBoxSat) {
            dayOfWeek.sat = isChecked;
        } else if(viewId == R.id.checkBoxSun) {
            dayOfWeek.sun = isChecked;
        } else if(viewId == R.id.toggleButton) {
            isAlarmOn = isChecked;
        }
    }

    private void activateAlarm(AlarmInfo newAlarm) {
        AlarmService alarmService = new AlarmService();
        try {
            alarmService.enableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
