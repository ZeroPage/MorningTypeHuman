package org.zeropage.gdg.morningTypeHuman.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.AlarmAddActivityController;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmCheckBox;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmCreateButton;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmTimePicker;
import org.zeropage.gdg.morningTypeHuman.view.alarmmanage.AlarmToggleButton;

public class AlarmAddActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add);

        AlarmAddActivityController controller = new AlarmAddActivityController(this);

        AlarmTimePicker lectureTime = (AlarmTimePicker) findViewById(R.id.timePicker);
        lectureTime.init(controller);

        // 음.. 이건 커스텀으로 못 만들겠다..
        SupportMapFragment googleMapFragment = (SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.fragmentMap));

        GoogleMap alarmGoogleMap = googleMapFragment.getMap();
        alarmGoogleMap.setOnCameraChangeListener(controller);

        AlarmCreateButton alarmCreateButton = (AlarmCreateButton) findViewById(R.id.buttonAddAlarm);
        alarmCreateButton.init(controller);

        // FIXME: make it CheckBoxGroup
        AlarmCheckBox checkBoxMon = (AlarmCheckBox) findViewById(R.id.checkBoxMon);
        checkBoxMon.init(controller);

        AlarmCheckBox checkBoxTue = (AlarmCheckBox) findViewById(R.id.checkBoxTue);
        checkBoxTue.init(controller);

        AlarmCheckBox checkBoxWed = (AlarmCheckBox) findViewById(R.id.checkBoxWed);
        checkBoxWed.init(controller);

        AlarmCheckBox checkBoxThu = (AlarmCheckBox) findViewById(R.id.checkBoxThu);
        checkBoxThu.init(controller);

        AlarmCheckBox checkBoxFri = (AlarmCheckBox) findViewById(R.id.checkBoxFri);
        checkBoxFri.init(controller);

        AlarmCheckBox checkBoxSat = (AlarmCheckBox) findViewById(R.id.checkBoxSat);
        checkBoxSat.init(controller);

        AlarmCheckBox checkBoxSun = (AlarmCheckBox) findViewById(R.id.checkBoxSun);
        checkBoxSun.init(controller);

        AlarmToggleButton toggleButton = (AlarmToggleButton) findViewById(R.id.toggleButton);
        toggleButton.init(controller);
    }
}



