package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AppStatisticsManager {
    private static boolean isAvailable = false;
    private static AppStatistics appStatistics;

    public static void init(Context context) {
        try {
            appStatistics = FileStorage.loadAppStats();
        } catch (IOException exception) {
            Toast.makeText(context, "새 통계를 생성합니다.", Toast.LENGTH_SHORT).show();
            appStatistics = new AppStatistics();
            try {
                FileStorage.saveAppStats(appStatistics);
            } catch (IOException e) {
                Toast.makeText(context, "통계 파일 접근을 실패하였습니다. 통계 기능에 제한이 생깁니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        isAvailable = true;
    }

    public static void increaseTotalLaunched(Context context) {
        if(isAvailable) {
            appStatistics.totalLaunched++;
            try {
                FileStorage.saveAppStats(appStatistics);
            } catch (IOException e) {
                Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public static int getTotalLaunched() {
        if(isAvailable) {
            return appStatistics.totalLaunched;
        } else {
            return 0;
        }
    }

}


class AppStatistics implements Serializable {
    public int totalTries;
    public int totalSucceed;
    public int totalFailed;
    public int totalLaunched;
}