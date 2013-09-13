package org.zeropage.gdg.morningTypeHuman.view.alarmmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;
/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmTimePicker extends TimePicker{
    private Context context;

    public AlarmTimePicker(Context context) {
        super(context);
        create(context);
    }

    public AlarmTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(OnTimeChangedListener controller) {
        setOnTimeChangedListener(controller);
        controller.onTimeChanged(this, getCurrentHour(), getCurrentMinute());
    }
}
