package org.zeropage.gdg.morningTypeHuman.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.model.AlarmServiece;
import org.zeropage.gdg.morningTypeHuman.view.AlarmEditActivity;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmTimePicker;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Skywave on 13. 9. 13.
 */
public class AlarmEditActivityController implements View.OnClickListener, TimePicker.OnTimeChangedListener, GoogleMap.OnCameraChangeListener {
    private AlarmEditActivity activity;
    private String name;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;

    Button editButton;
    Button removeButton;
    EditText editText;
    AlarmTimePicker timePicker;
    GoogleMap map;

    private AlarmInfo oldAlarmInfo;
    private AlarmInfo newAlarmInfo;
    ArrayList<AlarmInfo> alarmInfos;
    int alarmInfoToEdit;

    public AlarmEditActivityController(AlarmEditActivity activity) {
        alarmInfoToEdit = activity.getIntent().getExtras().getInt("AlarmInfoToEdit");


        editButton = (Button) activity.findViewById(R.id.buttonEditAlarm);
        removeButton = (Button) activity.findViewById(R.id.buttonDeleteAlarm);
        editText = (EditText) activity.findViewById(R.id.editTextLectureName);
        timePicker = (AlarmTimePicker) activity.findViewById(R.id.timePicker);
        map = ((SupportMapFragment) (activity.getSupportFragmentManager().findFragmentById(R.id.fragmentMap))).getMap();

        editButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(this);
        map.setOnCameraChangeListener(this);

        this.activity = activity;

        try {
            alarmInfos = AlarmInfoStorage.load();
        } catch (IOException e) {
            Toast.makeText(activity, "FATAL_ERROR:기존 알람 목록을 불러오는데 실패.", Toast.LENGTH_LONG).show();
            activity.finish();
        }

        oldAlarmInfo = alarmInfos.get(alarmInfoToEdit);

        name = oldAlarmInfo.name;
        hour = oldAlarmInfo.hour;
        minute = oldAlarmInfo.minute;
        latitude = oldAlarmInfo.latitude;
        longitude = oldAlarmInfo.longitude;

        editText.setText(name);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.buttonEditAlarm) {
            name = editText.getEditableText().toString();
            newAlarmInfo = new AlarmInfo(name, hour, minute, longitude, latitude);
            alarmInfos.add(newAlarmInfo);
            alarmInfos.remove(alarmInfoToEdit);
            try {
                AlarmInfoStorage.save(alarmInfos);
            } catch (IOException e) {
                Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                activity.finish();
            }
            Toast.makeText(activity, "알람 수정 완료", Toast.LENGTH_LONG).show();
            deactivateAlarm(oldAlarmInfo);
            activateAlarm(newAlarmInfo);
            activity.finish();
        } else if (viewId == R.id.buttonDeleteAlarm) {
            alarmInfos.remove(alarmInfoToEdit);
            try {
                AlarmInfoStorage.save(alarmInfos);
            } catch (IOException e) {
                Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                activity.finish();
            }
            deactivateAlarm(oldAlarmInfo);
            Toast.makeText(activity, "알람 삭제 완료", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        latitude = cameraPosition.target.latitude;
        longitude = cameraPosition.target.longitude;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    private void activateAlarm(AlarmInfo newAlarm) {
        AlarmServiece alarmServiece = new AlarmServiece();
        try {
            alarmServiece.enableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deactivateAlarm(AlarmInfo newAlarm) {
        AlarmServiece alarmServiece = new AlarmServiece();
        try {
            alarmServiece.disableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
