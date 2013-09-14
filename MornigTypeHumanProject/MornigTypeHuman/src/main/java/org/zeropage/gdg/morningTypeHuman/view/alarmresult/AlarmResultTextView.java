package org.zeropage.gdg.morningTypeHuman.view.alarmresult;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TextView;

import org.zeropage.gdg.morningTypeHuman.controller.AlarmResultActivityController;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmResultTextView extends TextView implements LocationListener {
    private Context context;
    private AlarmResultActivityController controller;

    public AlarmResultTextView(Context context) {
        super(context);
        create(context);
    }

    public AlarmResultTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        create(context);
    }

    public AlarmResultTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        create(context);
    }

    private void create(Context context) {
        this.context = context;
        setText("위치정보를 읽는 중...");
    }

    public void init(AlarmResultActivityController controller) {
        this.controller = controller;
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(controller.isValid(location.getLatitude(),location.getLongitude())) {
            setText("성공!!!");
        } else {
            setText("실패ㅠㅜ");
        }
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
