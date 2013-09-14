package org.zeropage.gdg.morningTypeHuman.view;

import android.app.Activity;
import android.os.Bundle;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.AlarmResultActivityController;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.view.alarmresult.AlarmResultTextView;

public class AlarmResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_result);

        AlarmInfo alarm = (AlarmInfo) getIntent().getExtras().get(AlarmInfo.intentKey);
        AlarmResultActivityController controller = new AlarmResultActivityController(this, alarm);

        AlarmResultTextView showResult = (AlarmResultTextView) findViewById(R.id.textViewShowResult);
        showResult.init(controller);
    }
}
