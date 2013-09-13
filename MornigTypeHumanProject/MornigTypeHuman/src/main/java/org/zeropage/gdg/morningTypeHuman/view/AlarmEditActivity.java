package org.zeropage.gdg.morningTypeHuman.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.AlarmEditActivityController;

public class AlarmEditActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);

        AlarmEditActivityController controller = new AlarmEditActivityController(this);

        controller.update();
    }
}
