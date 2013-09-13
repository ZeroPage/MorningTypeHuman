package org.zeropage.gdg.morningTypeHuman.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmInfoStorage {
    public static ArrayList<AlarmInfo> loadList() throws IOException {
        return FileStorage.loadAlarmList();
    }

    public static void saveList(ArrayList<AlarmInfo> list) throws IOException {
        FileStorage.saveAlarmList(list);
    }

    public static AlarmInfo loadAlarmInfo(int index) throws IOException {
        return FileStorage.loadAlarmList().get(index);
    }

    public static void saveAlarmInfo(AlarmInfo alarmInfo) throws IOException {
        ArrayList<AlarmInfo> list = FileStorage.loadAlarmList();
        list.add(alarmInfo);
        FileStorage.saveAlarmList(list);
    }

    public static void deleteAlarmInfo(int index) throws IOException {
        ArrayList<AlarmInfo> list = FileStorage.loadAlarmList();
        list.remove(index);
        FileStorage.saveAlarmList(list);
    }

}
