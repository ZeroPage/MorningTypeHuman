package org.zeropage.gdg.morningTypeHuman.model;

import android.app.PendingIntent;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by rino0601 on 13. 9. 9..
 */
public class FileStorage {
    private static File pendingIntentFile = null;

    public static void init(Context context) {// 이방법 뭔가 비효율적인 메모리 낭비인 것 같지만.. 일단 만들기 편하니
        File externalFilesDir = context.getExternalFilesDir(null);
        pendingIntentFile = new File(externalFilesDir,"pendingIntentFile");
        if(!pendingIntentFile.isDirectory()){
            pendingIntentFile.mkdirs();
        }
    }

    public static void save(PendingIntent sender,String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(pendingIntentFile,name));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(sender);
        oos.close();
        fos.close();
    }
}
