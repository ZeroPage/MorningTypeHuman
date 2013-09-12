package org.zeropage.gdg.morningTypeHuman.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rino0601 on 13. 9. 13..
 */
public class AlarmInfoStorage {
    public static ArrayList<AlarmInfo> load() throws IOException {
        return FileStorage.loadAlarmList();
    }

    public static void save(ArrayList<AlarmInfo> list) throws IOException {
        FileStorage.saveAlarmList(list);
    }

}
