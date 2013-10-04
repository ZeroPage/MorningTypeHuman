package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class FileStorage {
    private static File alarmInfoListFile = null;
    private static File appStatisticsFile = null;
    private static File nameSetFile = null;

    public static void init(Context context) {// 이방법 뭔가 비효율적인 메모리 낭비인 것 같지만.. 일단 만들기 편하니
        File externalFilesDir = context.getExternalFilesDir(null);
        if (!externalFilesDir.isDirectory())
            externalFilesDir.mkdirs();
        alarmInfoListFile = new File(externalFilesDir, "alarmInfoListFile.serial");
        appStatisticsFile = new File(externalFilesDir, "appStatsFile.serial");
        nameSetFile = new File(externalFilesDir, "nameSetFile.serial");
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
        } catch (ClassNotFoundException e) {
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

    public static void saveAppStats(AppStatistics sender) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(appStatisticsFile);
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

    public static AppStatistics loadAppStats() throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        AppStatistics result = null;
        try {
            fis = new FileInputStream(appStatisticsFile);
            ois = new ObjectInputStream(fis);
            result = (AppStatistics) ois.readObject();
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

    public static void saveNameSet(HashSet<String> sender) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(nameSetFile);
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

    public static HashSet<String> loadNameSet() throws IOException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        HashSet<String> result = null;
        try {
            fis = new FileInputStream(nameSetFile);
            ois = new ObjectInputStream(fis);
            result = (HashSet<String>) ois.readObject();
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
