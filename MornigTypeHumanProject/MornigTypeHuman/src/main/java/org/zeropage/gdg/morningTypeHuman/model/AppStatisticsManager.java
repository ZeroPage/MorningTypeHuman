package org.zeropage.gdg.morningTypeHuman.model;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AppStatisticsManager {
    private static boolean isAvailable = false;
    private static AppStatistics appStatistics;

    private AppStatisticsManager() {

    }

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
        if(!isAvailable) {
            return;
        }
        appStatistics.totalLaunched++;
        appStatistics.lastLaunchedDate = new Date();
        try {
            FileStorage.saveAppStats(appStatistics);
        } catch (IOException e) {
            Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void alarmSucceed(Context context) {
        if(!isAvailable) {
            return;
        }
        appStatistics.totalTries++;
        appStatistics.totalSucceed++;
        appStatistics.currentSuccessStreak++;
        if(appStatistics.currentSuccessStreak > appStatistics.bestSuccessStreak) {
            appStatistics.bestSuccessStreak = appStatistics.currentSuccessStreak;
        }

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        appStatistics.dayOfWeekSucceed[dayOfWeek]++;

        try {
            FileStorage.saveAppStats(appStatistics);
        } catch (IOException e) {
            Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void alarmFailed(Context context) {
        if(!isAvailable) {
            return;
        }
        appStatistics.totalTries++;
        appStatistics.totalFailed++;
        appStatistics.currentSuccessStreak = 0;

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        appStatistics.dayOfWeekFailed[dayOfWeek]++;

        try {
            FileStorage.saveAppStats(appStatistics);
        } catch (IOException e) {
            Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static void visitStatistics(Context context) {
        if(!isAvailable) {
            return;
        }
        appStatistics.statisticsVisited++;
        try {
            FileStorage.saveAppStats(appStatistics);
        } catch (IOException e) {
            Toast.makeText(context, "통계 저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static int getTotalLaunched() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.totalLaunched;
    }

    public static int getTotalTries() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.totalTries;
    }

    public static int getTotalSucceed() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.totalSucceed;
    }

    public static int getTotalFailed() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.totalFailed;
    }

    public static int getStatisticsVisited() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.statisticsVisited;
    }

    public static int getCurrentSuccessStreak() {
        if(!isAvailable) {
            return 0;
        }
            return appStatistics.currentSuccessStreak;
    }

    public static int getBestSuccessStreak() {
        if(!isAvailable) {
            return 0;
        }
        return appStatistics.bestSuccessStreak;
    }

    public static int getDayOfWeekSucceed(int dayOfWeek) {
        if(!isAvailable) {
            return 0;
        }

        return appStatistics.dayOfWeekSucceed[dayOfWeek];
    }

    public static int getDayOfWeekFailed(int dayOfWeek) {
        if(!isAvailable) {
            return 0;
        }

        return appStatistics.dayOfWeekFailed[dayOfWeek];
    }

    public static Date getFirstLaunchedDate() {
        if(!isAvailable) {
            return new Date(0);
        }
        return appStatistics.firstLaunchedDate;
    }

    public static Date getLastLaunchedDate() {
        if(!isAvailable) {
            return new Date(0);
        }
        return appStatistics.lastLaunchedDate;
    }

    public static ArrayList<StatisticsValue> getStatisticsValues() {
        ArrayList<StatisticsValue> statisticsValues = new ArrayList<StatisticsValue>();
        statisticsValues.add(new StatisticsValue("총 실행 횟수", String.valueOf(getTotalLaunched())));
        statisticsValues.add(new StatisticsValue("총 시도 횟수", String.valueOf(getTotalTries())));
        statisticsValues.add(new StatisticsValue("총 성공 횟수", String.valueOf(getTotalSucceed())));
        statisticsValues.add(new StatisticsValue("총 실패 횟수", String.valueOf(getTotalFailed())));
        statisticsValues.add(new StatisticsValue("현재 연속 성공 횟수", String.valueOf(getCurrentSuccessStreak())));
        statisticsValues.add(new StatisticsValue("최고 연속 성공 횟수", String.valueOf(getBestSuccessStreak())));

        return statisticsValues;
    }

    public static ArrayList<StatisticsValue> getAllStatisticsValues() {
        String temp = "";

        ArrayList<StatisticsValue> statisticsValues = getStatisticsValues();
        statisticsValues.add(new StatisticsValue("첫 실행 날짜", getFirstLaunchedDate().toString()));
        statisticsValues.add(new StatisticsValue("마지막 실행 날짜", getLastLaunchedDate().toString()));
        statisticsValues.add(new StatisticsValue("통계 방문 횟수", String.valueOf(getStatisticsVisited())));

        for(int i = 0; i < 7; i++) {
            temp += String.valueOf(getDayOfWeekSucceed(i + 1));
        }
        statisticsValues.add(new StatisticsValue("요일별 성공 횟수(일~토)", temp));

        temp = "";
        for(int i = 0; i < 7; i++) {
            temp += String.valueOf(getDayOfWeekFailed(i + 1));
        }
        statisticsValues.add(new StatisticsValue("요일별 실패 횟수(일~토)", temp));

        return statisticsValues;
    }
}

class AppStatistics implements Serializable {
    public int totalTries;
    public int totalSucceed;
    public int totalFailed;
    public int totalLaunched;
    public int statisticsVisited;
    public int currentSuccessStreak;
    public int bestSuccessStreak;
    
    public int[] dayOfWeekSucceed = new int[8];
    public int[] dayOfWeekFailed = new int[8];

    public Date firstLaunchedDate;
    public Date lastLaunchedDate;
}