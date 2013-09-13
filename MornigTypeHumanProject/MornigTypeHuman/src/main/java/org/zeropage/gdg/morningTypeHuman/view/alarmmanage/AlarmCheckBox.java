package org.zeropage.gdg.morningTypeHuman.view.alarmmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AlarmCheckBox extends CheckBox {
    private Context context;

    public AlarmCheckBox(Context context) {
        super(context);
        create(context);
    }

    public AlarmCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmCheckBox(Context context, AttributeSet attrs, int defStyle) {
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
