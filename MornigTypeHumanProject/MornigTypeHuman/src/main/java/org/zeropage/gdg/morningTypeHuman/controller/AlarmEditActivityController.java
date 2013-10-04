package org.zeropage.gdg.morningTypeHuman.controller;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfoStorage;
import org.zeropage.gdg.morningTypeHuman.model.AlarmService;
import org.zeropage.gdg.morningTypeHuman.model.DayOfWeek;
import org.zeropage.gdg.morningTypeHuman.model.DuplicateNameException;
import org.zeropage.gdg.morningTypeHuman.view.AlarmEditActivity;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmCheckBox;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmTimePicker;

import java.io.IOException;

/**
 * Created by Skywave on 13. 9. 13.
 */
public class AlarmEditActivityController implements View.OnClickListener, TimePicker.OnTimeChangedListener, GoogleMap.OnCameraChangeListener, CheckBox.OnCheckedChangeListener {
    private AlarmEditActivity activity;
    private String name;
    private int hour;
    private int minute;
    private double latitude;
    private double longitude;
    private boolean isAlarmOn;
    private DayOfWeek dayOfWeek;

    // FIXME too many field variables;
    Button editButton;
    Button removeButton;
    EditText editText;
    AlarmTimePicker timePicker;
    GoogleMap map;
    ToggleButton toggleButton;

    AlarmCheckBox checkBoxMon;
    AlarmCheckBox checkBoxTue;
    AlarmCheckBox checkBoxWed;
    AlarmCheckBox checkBoxThu;
    AlarmCheckBox checkBoxFri;
    AlarmCheckBox checkBoxSat;
    AlarmCheckBox checkBoxSun;

    private AlarmInfo oldAlarmInfo;
    private AlarmInfo newAlarmInfo;
    int alarmInfoToEdit;

    public AlarmEditActivityController(AlarmEditActivity activity) {
        alarmInfoToEdit = activity.getIntent().getExtras().getInt("AlarmInfoToEdit");
        dayOfWeek = new DayOfWeek();

        // FIXME move it to Activity onCreate()
        editButton = (Button) activity.findViewById(R.id.buttonEditAlarm);
        removeButton = (Button) activity.findViewById(R.id.buttonDeleteAlarm);
        editText = (EditText) activity.findViewById(R.id.editTextLectureName);
        timePicker = (AlarmTimePicker) activity.findViewById(R.id.timePicker);
        toggleButton = (ToggleButton) activity.findViewById(R.id.toggleButton);
        map = ((SupportMapFragment) (activity.getSupportFragmentManager().findFragmentById(R.id.fragmentMap))).getMap();

        checkBoxMon = (AlarmCheckBox) activity.findViewById(R.id.checkBoxMon);
        checkBoxMon.init(this);

        checkBoxTue = (AlarmCheckBox) activity.findViewById(R.id.checkBoxTue);
        checkBoxTue.init(this);

        checkBoxWed = (AlarmCheckBox) activity.findViewById(R.id.checkBoxWed);
        checkBoxWed.init(this);

        checkBoxThu = (AlarmCheckBox) activity.findViewById(R.id.checkBoxThu);
        checkBoxThu.init(this);

        checkBoxFri = (AlarmCheckBox) activity.findViewById(R.id.checkBoxFri);
        checkBoxFri.init(this);

        checkBoxSat = (AlarmCheckBox) activity.findViewById(R.id.checkBoxSat);
        checkBoxSat.init(this);

        checkBoxSun = (AlarmCheckBox) activity.findViewById(R.id.checkBoxSun);
        checkBoxSun.init(this);

        editButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(this);
        map.setOnCameraChangeListener(this);
        toggleButton.setOnCheckedChangeListener(this);

        this.activity = activity;

        try {
            oldAlarmInfo = AlarmInfoStorage.loadAlarmInfo(alarmInfoToEdit);
        } catch (IOException e) {
            Toast.makeText(activity, "FATAL_ERROR:기존 알람 목록을 불러오는데 실패.", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    @Override
    public void onClick(View v) {
        // FIXME private method로 의미 분할이 필요해 보임.
        String name;
        int viewId = v.getId();
        if (viewId == R.id.buttonEditAlarm) {
            name = editText.getEditableText().toString();
            if (name == null || name.equals("")) {
                name = "강의";
            }
            if (!(dayOfWeek.mon || dayOfWeek.tue || dayOfWeek.wed || dayOfWeek.thu || dayOfWeek.fri || dayOfWeek.sat || dayOfWeek.sun)) {
                Toast.makeText(activity, "요일을 선택해 주세요.", Toast.LENGTH_LONG).show();
                return;
            }

            newAlarmInfo = new AlarmInfo(name, hour, minute, longitude, latitude, isAlarmOn, dayOfWeek);

            int duplicatedCount = 0;
            while(true) {
                try {
                    if(duplicatedCount != 0) {
                        newAlarmInfo.name = name + " (" + duplicatedCount + ")";
                    }
                    AlarmInfoStorage.deleteAlarmInfo(alarmInfoToEdit);
                    AlarmInfoStorage.saveAlarmInfo(newAlarmInfo);
                    break;
                } catch (IOException e) {
                    Toast.makeText(activity, "FATAL_ERROR:새 알람 목록을 저장하는데 실패.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    activity.finish();
                    return;
                } catch (DuplicateNameException e) {
                    duplicatedCount++;
                    e.printStackTrace();
                }
            }

            Toast.makeText(activity, "알람 수정 완료", Toast.LENGTH_LONG).show();
            deactivateAlarm(oldAlarmInfo);
            if (isAlarmOn) {
                activateAlarm(newAlarmInfo);
            }
            activity.finish();
        } else if (viewId == R.id.buttonDeleteAlarm) {
            try {
                AlarmInfoStorage.deleteAlarmInfo(alarmInfoToEdit);
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

    public void update() {
        name = oldAlarmInfo.name;
        hour = oldAlarmInfo.hour;
        minute = oldAlarmInfo.minute;
        latitude = oldAlarmInfo.latitude;
        longitude = oldAlarmInfo.longitude;
        isAlarmOn = oldAlarmInfo.isAlarmOn;
        dayOfWeek = oldAlarmInfo.dayOfWeek;

        editText.setText(name);
        toggleButton.setChecked(isAlarmOn);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));

        checkBoxMon.setChecked(dayOfWeek.mon);
        checkBoxTue.setChecked(dayOfWeek.tue);
        checkBoxWed.setChecked(dayOfWeek.wed);
        checkBoxThu.setChecked(dayOfWeek.thu);
        checkBoxFri.setChecked(dayOfWeek.fri);
        checkBoxSat.setChecked(dayOfWeek.sat);
        checkBoxSun.setChecked(dayOfWeek.sun);
    }

    private void activateAlarm(AlarmInfo newAlarm) {
        AlarmService alarmService = new AlarmService();
        try {
            alarmService.enableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deactivateAlarm(AlarmInfo newAlarm) {
        AlarmService alarmService = new AlarmService();
        try {
            alarmService.disableAlarm(activity, newAlarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
