package org.zeropage.gdg.morningTypeHuman.view.alarmlist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import org.zeropage.gdg.morningTypeHuman.controller.AlarmListActivityController;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmAddButton extends Button {
    private Context context;

    public AlarmAddButton(Context context) {
        super(context);
        create(context);
    }

    public AlarmAddButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmAddButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(AlarmListActivityController controller) {
        setOnClickListener(controller);
    }
}
