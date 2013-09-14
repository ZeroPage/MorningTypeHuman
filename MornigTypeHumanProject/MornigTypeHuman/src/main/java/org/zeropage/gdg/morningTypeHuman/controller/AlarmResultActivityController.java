package org.zeropage.gdg.morningTypeHuman.controller;

import android.app.AlarmManager;

import org.zeropage.gdg.morningTypeHuman.model.AlarmInfo;
import org.zeropage.gdg.morningTypeHuman.model.AlarmService;
import org.zeropage.gdg.morningTypeHuman.view.AlarmResultActivity;

import java.io.IOException;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmResultActivityController {
    private AlarmResultActivity activity;
    private AlarmInfo alarm;

    public AlarmResultActivityController(AlarmResultActivity activity, AlarmInfo alarm) {
        this.activity = activity;
        this.alarm = alarm;

        AlarmService alarmService = new AlarmService();
        try {
            alarmService.enableAlarm(this.activity, this.alarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(double latitude, double longitude) {
        return (distance(latitude, longitude, alarm.latitude, alarm.longitude) < 1.0);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
