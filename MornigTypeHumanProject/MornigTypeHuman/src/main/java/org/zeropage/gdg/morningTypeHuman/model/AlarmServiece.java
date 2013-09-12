package org.zeropage.gdg.morningTypeHuman.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.zeropage.gdg.morningTypeHuman.view.AlarmResultActivity;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class AlarmServiece extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent fromIntent) {
        // 어느 인텐트가 날아오느냐에 따라 달렸지만,
        // 위치 정보를 체크. 다이얼로그든, 액티비티든 소환!

        AlarmInfo alarmInfo = (AlarmInfo) fromIntent.getExtras().get(AlarmInfo.intentKey);
        Intent intent = new Intent(context, AlarmResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AlarmInfo.intentKey,alarmInfo);
        context.startActivity(intent);
    }

    public void enableWeeklyAlarm(Context context, AlarmInfo newAlarm) throws IOException {
        // AlarmManager 호출
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 알람을 받을 시간을, 넘겨 받은 정보를 가지고 설정.

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_alarm.set(Calendar.HOUR_OF_DAY, newAlarm.hour);//set the alarm time
        cal_alarm.set(Calendar.MINUTE, newAlarm.minute);
        cal_alarm.set(Calendar.SECOND, 0);
        while (cal_alarm.before(cal_now)) {//if its in the past increment
            cal_alarm.add(Calendar.DATE, 1);
        }
        //SET YOUR AlarmManager here
        PendingIntent sender = getPendingIntent(context, newAlarm);
        am.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), sender);
        // 알람 매니저에 알람을 등록
//        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),(7*24*60*60*1000),sender);
//        // ㄴ '등록 시점'으로 부터 일주일 동안 반복하는 코드 완성.
    }

    private PendingIntent getPendingIntent(Context context, AlarmInfo newAlarm) {
        Intent intent = new Intent(context, AlarmServiece.class);
        intent.putExtra(AlarmInfo.intentKey, newAlarm);
        intent.setAction("ACTION_AS_KEY_" + newAlarm.name); // 각 AlarmInfo를 구분하기 위한 키.
        Log.i("asdf", "ACTION_AS_KEY_" + newAlarm.name);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void disableWeeklyAlarm(Context context, AlarmInfo newAlarm) throws IOException {
        // 리스트에 띄우고 난뒤 작업 할 것.
    }
}
