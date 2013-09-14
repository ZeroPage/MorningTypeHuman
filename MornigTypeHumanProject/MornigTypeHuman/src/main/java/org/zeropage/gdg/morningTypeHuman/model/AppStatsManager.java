package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AppStatsManager {
    private static boolean isAvailable = false;
    private static AppStats appStats;

    public static void init(Context context) {
        try {
            appStats = FileStorage.loadAppStats();
        } catch (IOException exception) {
            Toast.makeText(context, "새 통계를 생성합니다.", Toast.LENGTH_SHORT).show();
            appStats = new AppStats();
            try {
                FileStorage.saveAppStats(appStats);
            } catch (IOException e) {
                Toast.makeText(context, "통계 파일 접근을 실패하였습니다. 통계 기능에 제한이 생깁니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        isAvailable = true;
    }
}

class AppStats implements Serializable {
    public static String intentKey="appstats";
    public int totalTries;
    public int totalSucceed;
    public int totalFailed;
    public int totalLaunched;
}