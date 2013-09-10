package org.zeropage.gdg.morningTypeHuman.model;

import android.app.PendingIntent;

import java.io.IOException;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class Storage {
    public static void save(PendingIntent sender, String name) throws IOException {
        FileStorage.save(sender, name);
    }
}
