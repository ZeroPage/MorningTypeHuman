package org.zeropage.gdg.morningTypeHuman.model;

import java.io.Serializable;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmInfo implements Serializable{
    public static String intentKey="alarmInfo";
    public String name;
    public int hour;
    public int minute;
    public double longitude;
    public double latitude;
    public boolean isAlarmOn;
    public DayOfWeek dayOfWeek;

    public AlarmInfo(String name, int hour, int minute, double longitude, double latitude, boolean isAlarmOn, DayOfWeek dayOfWeek) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.longitude = longitude;
        this.latitude = latitude;

        this.isAlarmOn = isAlarmOn;
        try {
            this.dayOfWeek = (DayOfWeek) dayOfWeek.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
