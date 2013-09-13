package org.zeropage.gdg.morningTypeHuman.view.alarmmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AlarmToggleButton extends ToggleButton {
    private Context context;

    public AlarmToggleButton(Context context) {
        super(context);
        create(context);
    }

    public AlarmToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
    }

    public void init(OnCheckedChangeListener controller) {
        setOnCheckedChangeListener(controller);
        controller.onCheckedChanged(this, isChecked());
    }
}
