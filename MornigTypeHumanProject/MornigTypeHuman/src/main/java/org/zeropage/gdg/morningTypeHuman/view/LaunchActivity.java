package org.zeropage.gdg.morningTypeHuman.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.appstate.OnStateLoadedListener;
import com.google.android.gms.games.GamesClient;

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
    public LaunchActivity() {
        super(CLIENT_GAMES | CLIENT_APPSTATE);
    }

    protected int _splashTime = 5000;

    private static LaunchActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        signOut();
        finish();
    }

    @Override
    public void onSignInFailed() {
        setAction();
        Toast.makeText(this,"본 앱은 Google+ 로그인이 필요합니다.", Toast.LENGTH_LONG).show();
    }

    private void setAction() {
        if(isSignedIn()) {
            /* 액티비티 초기화 */
            instance = this;
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


            /* 다음 액티비티로  */
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

    public static GamesClient callAPI() {
        return instance.getGamesClient();
    }
}
