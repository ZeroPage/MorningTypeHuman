package org.zeropage.gdg.morningTypeHuman.view.statistics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import org.zeropage.gdg.morningTypeHuman.controller.StatisticsActivityController;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class StatisticsListView extends ListView {
    private Context context;
    private StatisticsActivityController controller;
    public StatisticsListView(Context context) {
        super(context);
    }

    public StatisticsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatisticsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(StatisticsActivityController controller) {
        this.controller = controller;
        setAdapter(controller);
    }
}
