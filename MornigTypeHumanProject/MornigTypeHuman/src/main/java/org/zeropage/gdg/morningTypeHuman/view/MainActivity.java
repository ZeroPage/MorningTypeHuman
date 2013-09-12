package org.zeropage.gdg.morningTypeHuman.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import org.zeropage.gdg.morningTypeHuman.R;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("alarmList").setIndicator("List")
                .setContent(new Intent(this, AlarmListActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("setting").setIndicator("Setting")
                .setContent(new Intent(this, SettingActivity.class)));
    }
}
