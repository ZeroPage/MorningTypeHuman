package org.zeropage.gdg.morningTypeHuman.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.basegameutils.BaseGameActivity;
import org.zeropage.gdg.morningTypeHuman.controller.LaunchActivityController;
import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AppStatisticsManager;
import org.zeropage.gdg.morningTypeHuman.model.FileStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends BaseGameActivity {
    protected int _splashTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // init here.
        FileStorage.init(this);
        try {
            FileStorage.loadAlarmList();
        } catch (IOException e) {
            try {
                FileStorage.saveAlarmList(new ArrayList<AlarmInfo>());
            } catch (IOException e2) {
                Toast.makeText(this, "Error: 알람 리스트를 생성하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        try {
            FileStorage.loadNameSet();
        } catch (IOException e) {
            try {
                FileStorage.saveNameSet(new HashSet<String>());
            } catch (IOException e2) {
                Toast.makeText(this, "Error: 알람 이름 리스트를 생성하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }


        AppStatisticsManager.init(this);
        AppStatisticsManager.appLaunched(this);

        LaunchActivityController controller = new LaunchActivityController(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    public void onSignInFailed() {
        setAction();
        Toast.makeText(this,"본 앱은 Google+ 로그인이 필요합니다.", Toast.LENGTH_LONG).show();
    }

    private void setAction() {
        if(true){
        //if(isSignedIn()) {
            TimerTask loginTask = new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                }
            };
            new Timer().schedule(loginTask, _splashTime);
        } else {
            beginUserInitiatedSignIn();
        }
    }

    @Override
    public void onSignInSucceeded() {
        setAction();
    }
}
