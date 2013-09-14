package org.zeropage.gdg.morningTypeHuman.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.zeropage.gdg.morningTypeHuman.view.AlarmResultActivity;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class AlarmService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent fromIntent) {
        // 어느 인텐트가 날아오느냐에 따라 달렸지만,
        // 위치 정보를 체크. 다이얼로그든, 액티비티든 소환!

        AlarmInfo alarmInfo = (AlarmInfo) fromIntent.getExtras().get(AlarmInfo.intentKey);
        Intent intent = new Intent(context, AlarmResultActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AlarmInfo.intentKey, alarmInfo);
        context.startActivity(intent);
    }

    public void enableAlarm(Context context, AlarmInfo newAlarm) throws IOException {
        // AlarmManager 호출
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int dayDifference;

        // 알람을 받을 시간을, 넘겨 받은 정보를 가지고 설정.

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_alarm.set(Calendar.HOUR_OF_DAY, newAlarm.hour);//set the alarm time
        cal_alarm.set(Calendar.MINUTE, newAlarm.minute);
        cal_alarm.set(Calendar.SECOND, 0);


        if (cal_alarm.before(cal_now)) {//if its in the past increment
            cal_now.add(Calendar.DATE, 1);
        }

        dayDifference = getDayDifference(cal_now.DAY_OF_WEEK, newAlarm.dayOfWeek);
        cal_alarm.add(Calendar.DATE, dayDifference);

        //For Debugging.
        Toast.makeText(context, cal_alarm.getTime().toString(), Toast.LENGTH_LONG).show();

        //SET YOUR AlarmManager here
        PendingIntent sender = getPendingIntent(context, newAlarm);
        am.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), sender);
        // 알람 매니저에 알람을 등록
//        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),(7*24*60*60*1000),sender);
//        // ㄴ '등록 시점'으로 부터 일주일 동안 반복하는 코드 완성.
    }

    public void disableAlarm(Context context, AlarmInfo newAlarm) throws IOException {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getPendingIntent(context, newAlarm));
    }

    private PendingIntent getPendingIntent(Context context, AlarmInfo newAlarm) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(AlarmInfo.intentKey, newAlarm);
        intent.setAction("ACTION_AS_KEY_" + newAlarm.name); // 각 AlarmInfo를 구분하기 위한 키.
        Log.i("asdf", "ACTION_AS_KEY_" + newAlarm.name);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    private int getDayDifference(int dayOfWeekToday, DayOfWeek dayOfWeek) {
        int count = 0;
        while (count < 7) {
            if (dayOfWeekToday == 1) {
                if (dayOfWeek.sun) {
                    return count;
                }
            } else if (dayOfWeekToday == 2) {
                if (dayOfWeek.mon) {
                    return count;
                }
            } else if (dayOfWeekToday == 3) {
                if (dayOfWeek.tue) {
                    return count;
                }
            } else if (dayOfWeekToday == 4) {
                if (dayOfWeek.wed) {
                    return count;
                }
            } else if (dayOfWeekToday == 5) {
                if (dayOfWeek.thu) {
                    return count;
                }
            } else if (dayOfWeekToday == 6) {
                if (dayOfWeek.fri) {
                    return count;
                }
            } else if (dayOfWeekToday == 7) {
                if (dayOfWeek.sat) {
                    return count;
                }
            }
            if (dayOfWeekToday == 7) {
                dayOfWeekToday = 1;
            } else {
                dayOfWeekToday++;
            }
            count++;
        }
        return 0;
    }
}
