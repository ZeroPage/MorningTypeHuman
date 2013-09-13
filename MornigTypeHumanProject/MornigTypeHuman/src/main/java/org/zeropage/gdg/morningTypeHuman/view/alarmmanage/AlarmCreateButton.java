package org.zeropage.gdg.morningTypeHuman.view.alarmmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmCreateButton extends Button {
    private Context context;

    public AlarmCreateButton(Context context) {
        super(context);
        create(context);
    }

    public AlarmCreateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmCreateButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(OnClickListener controller) {
        setOnClickListener(controller);
    }
}
