package org.zeropage.gdg.morningTypeHuman.view;

import android.os.Bundle;
import android.app.Activity;

import org.zeropage.gdg.morningTypeHuman.R;
import org.zeropage.gdg.morningTypeHuman.controller.StatisticsActivityController;
import org.zeropage.gdg.morningTypeHuman.view.statistics.StatisticsListView;

public class StatisticsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        StatisticsActivityController controller = new StatisticsActivityController(this);

        ((StatisticsListView) findViewById(R.id.listView)).init(controller);
    }
}
