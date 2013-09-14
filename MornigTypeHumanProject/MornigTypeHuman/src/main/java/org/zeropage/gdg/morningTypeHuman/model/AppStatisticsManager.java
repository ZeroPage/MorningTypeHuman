package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
            appStatistics = new AppStatistics();
            appStatistics.firstLaunchedDate = new Date();
            try {
                FileStorage.saveAppStats(appStatistics);
                Toast.makeText(context, "새 통계를 생성합니다.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(context, "통계 파일 접근을 실패하였습니다. 통계 기능에 제한이 생깁니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        isAvailable = true;
    }

    public static void appLaunched(Context context) {
        if(isAvailable) {
            appStatistics.totalLaunched++;
            appStatistics.lastLaunchedDate = new Date();
            try {
                FileStorage.saveAppStats(appStatistics);
            } catch (IOException e) {
                Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public static void alarmSucceed(Context context) {
        if(isAvailable) {
            appStatistics.totalTries++;
            appStatistics.totalSucceed++;
            try {
                FileStorage.saveAppStats(appStatistics);
            } catch (IOException e) {
                Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public static void alarmFailed(Context context) {
        if(isAvailable) {
            appStatistics.totalTries++;
            appStatistics.totalLaunched++;
            try {
                FileStorage.saveAppStats(appStatistics);
            } catch (IOException e) {
                Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public static void visitStatistics(Context context) {
        if(isAvailable) {
            appStatistics.statisticsVisited++;
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

    public static int getTotalTries() {
        if(isAvailable) {
            return appStatistics.totalTries;
        } else {
            return 0;
        }
    }

    public static int getTotalSucceed() {
        if(isAvailable) {
            return appStatistics.totalSucceed;
        } else {
            return 0;
        }
    }

    public static int getTotalFailed() {
        if(isAvailable) {
            return appStatistics.totalFailed;
        } else {
            return 0;
        }
    }

    public static int getStatisticsVisited() {
        if(isAvailable) {
            return appStatistics.statisticsVisited;
        } else {
            return 0;
        }
    }

    public static Date getFirstLaunchedDate() {
        if(isAvailable) {
            return appStatistics.firstLaunchedDate;
        } else {
            return new Date();
        }
    }

    public static Date getLastLaunchedDate() {
        if(isAvailable) {
            return appStatistics.lastLaunchedDate;
        } else {
            return new Date();
        }
    }

    public static ArrayList<StatisticsValue> getStatisticsValues() {
        ArrayList<StatisticsValue> statisticsValues = new ArrayList<StatisticsValue>();
        statisticsValues.add(new StatisticsValue("총 실행 횟수", String.valueOf(getTotalLaunched())));
        statisticsValues.add(new StatisticsValue("총 시도 횟수", String.valueOf(getTotalTries())));
        statisticsValues.add(new StatisticsValue("총 성공 횟수", String.valueOf(getTotalSucceed())));
        statisticsValues.add(new StatisticsValue("총 실패 횟수", String.valueOf(getTotalFailed())));

        return statisticsValues;
    }

    public static ArrayList<StatisticsValue> getAllStatisticsValues() {
        ArrayList<StatisticsValue> statisticsValues = getStatisticsValues();
        statisticsValues.add(new StatisticsValue("첫 실행 날짜", getFirstLaunchedDate().toString()));
        statisticsValues.add(new StatisticsValue("마지막 실행 날짜", getLastLaunchedDate().toString()));
        statisticsValues.add(new StatisticsValue("통계 방문 횟수", String.valueOf(getStatisticsVisited())));

        return statisticsValues;
    }
}


class AppStatistics implements Serializable {
    public int totalTries;
    public int totalSucceed;
    public int totalFailed;
    public int totalLaunched;
    public int statisticsVisited;
    public Date firstLaunchedDate;
    public Date lastLaunchedDate;
}