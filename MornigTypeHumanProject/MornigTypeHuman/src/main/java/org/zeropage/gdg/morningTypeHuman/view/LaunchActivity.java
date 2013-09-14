package org.zeropage.gdg.morningTypeHuman.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.LaunchActivityController;
import org.zeropage.gdg.morningTypeHuman.model.AppStatisticsManager;
import org.zeropage.gdg.morningTypeHuman.model.FileStorage;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends Activity {
    protected int _splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // init here.
        FileStorage.init(this);
        AppStatisticsManager.init(this);

        AppStatisticsManager.appLaunched(this);

        LaunchActivityController controller = new LaunchActivityController(this);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, _splashTime);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }


}
