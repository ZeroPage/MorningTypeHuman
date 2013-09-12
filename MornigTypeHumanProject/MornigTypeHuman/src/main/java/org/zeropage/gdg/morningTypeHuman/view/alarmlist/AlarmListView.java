package org.zeropage.gdg.morningTypeHuman.view.alarmlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.zeropage.gdg.morningTypeHuman.controller.AlarmListActivityController;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmListView extends ListView implements AdapterView.OnItemClickListener {
    private Context context;
    private AlarmListActivityController controller;

    public AlarmListView(Context context) {
        super(context);
        create(context);
    }

    public AlarmListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(AlarmListActivityController controller) {
        this.controller = controller;
        setOnItemClickListener(this);
        setAdapter(controller);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        controller.listItemClick(adapterView,view,i,l);
    }
}
