package org.zeropage.gdg.morningTypeHuman.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmInfoStorage {
    public static ArrayList<AlarmInfo> loadList() throws IOException {
        return FileStorage.loadAlarmList();
    }

    public static AlarmInfo loadAlarmInfo(int index) throws IOException {
        return FileStorage.loadAlarmList().get(index);
    }

    public static void saveAlarmInfo(AlarmInfo alarmInfo) throws IOException, DuplicateNameException {
        HashSet<String> set = FileStorage.loadNameSet();
        ArrayList<AlarmInfo> list = FileStorage.loadAlarmList();

        if(set.contains(alarmInfo.name)) {
            throw new DuplicateNameException();
        }

        list.add(alarmInfo);
        set.add(alarmInfo.name);
        FileStorage.saveAlarmList(list);
        FileStorage.saveNameSet(set);
    }

    public static void deleteAlarmInfo(int index) throws IOException {
        HashSet<String> set = FileStorage.loadNameSet();
        ArrayList<AlarmInfo> list = FileStorage.loadAlarmList();

        set.remove(list.get(index).name);
        list.remove(index);

        FileStorage.saveAlarmList(list);
        FileStorage.saveNameSet(set);
    }
}
