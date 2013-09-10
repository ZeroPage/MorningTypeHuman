package org.zeropage.gdg.morningTypeHuman.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class AlarmServiece extends BroadcastReceiver{

    private static Context context;

    public static void init(Context context){
        AlarmServiece.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 어느 인텐트가 날아오느냐에 따라 달렸지만,
        // 위치 정보를 체크. 다이얼로그든, 액티비티든 소환!
    }

    public void setWeeklyAlarm() throws IOException {
        // 알람 매니저에 등록할 인텐트를 만듬
        Intent intent = new Intent(context, AlarmServiece.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        // 알람을 받을 시간을 5초 뒤로 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        // 알람 매니저에 알람을 등록
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),(7*24*60*60*1000),sender);
        // ㄴ '등록 시점'으로 부터 일주일 동안 반복하는 코드 완성.
        // 당장은 일단 막는다.
        am.cancel(sender);

        // 훗날을 위해 PendingIntent 저장.
        Storage.save(sender,"testName");
    }

    public void cancleAlarm(){

    }
}
