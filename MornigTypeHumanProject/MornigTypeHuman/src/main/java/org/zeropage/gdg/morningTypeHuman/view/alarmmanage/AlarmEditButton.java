package org.zeropage.gdg.morningTypeHuman.view.alarmmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Skywave on 13. 9. 13.
 */
public class AlarmEditButton extends Button {
    private Context context;

    public AlarmEditButton(Context context) {
        super(context);
        create(context);
    }

    public AlarmEditButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmEditButton(Context context, AttributeSet attrs, int defStyle) {
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
