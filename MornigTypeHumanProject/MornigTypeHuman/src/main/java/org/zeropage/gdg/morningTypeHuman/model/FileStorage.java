package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class FileStorage {
    private static File alarmInfoListFile = null;
    private static File AppStatsFile = null;

    public static void init(Context context) {// 이방법 뭔가 비효율적인 메모리 낭비인 것 같지만.. 일단 만들기 편하니
        File externalFilesDir = context.getExternalFilesDir(null);
        if (!externalFilesDir.isDirectory())
            externalFilesDir.mkdirs();
        alarmInfoListFile = new File(externalFilesDir, "alarmInfoListFile.serial");
        AppStatsFile = new File(externalFilesDir, "appStatsFile.serial");
    }

    public static void saveAlarmList(ArrayList<AlarmInfo> sender) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(alarmInfoListFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sender);
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static ArrayList<AlarmInfo> loadAlarmList() throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ArrayList<AlarmInfo> result = null;
        try {
            fis = new FileInputStream(alarmInfoListFile);
            ois = new ObjectInputStream(fis);
            result = (ArrayList<AlarmInfo>) ois.readObject();
        }catch (ClassNotFoundException e){
            throw new IOException(e);
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return result;
    }

    public static void saveAppStats(AppStats sender) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(AppStatsFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sender);
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static AppStats loadAppStats() throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        AppStats result = null;
        try {
            fis = new FileInputStream(AppStatsFile);
            ois = new ObjectInputStream(fis);
            result = (AppStats) ois.readObject();
        }catch (ClassNotFoundException e){
            throw new IOException(e);
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return result;
    }
}
