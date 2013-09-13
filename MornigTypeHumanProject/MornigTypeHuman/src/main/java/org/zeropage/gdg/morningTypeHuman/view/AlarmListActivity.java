package org.zeropage.gdg.morningTypeHuman.view;

import android.app.Activity;
import android.os.Bundle;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.AlarmListActivityController;
import org.zeropage.gdg.morningTypeHuman.view.alarmlist.AlarmAddButton;
import org.zeropage.gdg.morningTypeHuman.view.alarmlist.AlarmListView;

public class AlarmListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlarmListActivityController controller = new AlarmListActivityController(this);

        AlarmAddButton alarmAddButton = (AlarmAddButton)findViewById(R.id.buttonAddAlarm);
        alarmAddButton.init(controller);

        AlarmListView listView = (AlarmListView)findViewById(R.id.listView);
        listView.init(controller);
    }
}
