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
import org.zeropage.gdg.morningTypeHuman.model.DuplicateNameException;
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

    @Override
    public void onClick(View view) {
        String name;
        EditText editText = (EditText) activity.findViewById(R.id.editTextLectureName);
        Editable et = editText.getEditableText();

        if (et == null || et.toString().equals("")) {
            name = "강의";
        } else {
            name = et.toString();
        }

        if (!(dayOfWeek.mon || dayOfWeek.tue || dayOfWeek.wed || dayOfWeek.thu || dayOfWeek.fri || dayOfWeek.sat || dayOfWeek.sun)) {
            Toast.makeText(activity, "요일을 선택해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        AlarmInfo newAlarm = new AlarmInfo(name, hour, minute, longitude, latitude, isAlarmOn, dayOfWeek);

        int duplicatedCount = 0;
        while(true) {
            try {
                if(duplicatedCount != 0) {
                    newAlarm.name = name + " (" + duplicatedCount + ")";
                }
                AlarmInfoStorage.saveAlarmInfo(newAlarm);
                break;
            } catch (IOException e) {
                Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
                return;
            } catch (DuplicateNameException e) {
                duplicatedCount++;
                e.printStackTrace();
            }
        }

        if (isAlarmOn) {
            activateAlarm(newAlarm);
        }
        activity.finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        if (viewId == R.id.checkBoxMon) {
            dayOfWeek.mon = isChecked;
        } else if (viewId == R.id.checkBoxTue) {
            dayOfWeek.tue = isChecked;
        } else if (viewId == R.id.checkBoxWed) {
            dayOfWeek.wed = isChecked;
        } else if (viewId == R.id.checkBoxThu) {
            dayOfWeek.thu = isChecked;
        } else if (viewId == R.id.checkBoxFri) {
            dayOfWeek.fri = isChecked;
        } else if (viewId == R.id.checkBoxSat) {
            dayOfWeek.sat = isChecked;
        } else if (viewId == R.id.checkBoxSun) {
            dayOfWeek.sun = isChecked;
        } else if (viewId == R.id.toggleButton) {
            isAlarmOn = isChecked;
        }
    }

    private void activateAlarm(AlarmInfo newAlarm) {
        AlarmService alarmService = new AlarmService();
        alarmService.enableAlarm(activity, newAlarm);
    }


}
