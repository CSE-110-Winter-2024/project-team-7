package edu.ucsd.cse110.successorator.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import edu.ucsd.cse110.successorator.lib.domain.DateHandler;

import android.os.Handler;
import android.os.Looper;

public class DateUpdater {

    private static Timer timer;
    private static Handler handler;

    // Scheduler to run updateDate() every 24 hours at 2AM
    public static void scheduleDateUpdates(DateHandler dateData) {
        handler = new Handler(Looper.getMainLooper());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dateData.updateTodayDate((String) null);
                        System.out.println("dateupdater print");
                    }
                });
            }
        }, calculateDelayTo2AM(Calendar.getInstance()), 24 * 60 * 60 * 1000);
    }

    public static long calculateDelayTo2AM(Calendar currentTime) {
        Calendar next2AM = (Calendar) currentTime.clone();
        next2AM.set(Calendar.HOUR_OF_DAY, 2);
        next2AM.set(Calendar.MINUTE, 0);
        next2AM.set(Calendar.SECOND, 0);
        if (next2AM.before(currentTime)) {
            next2AM.add(Calendar.DAY_OF_YEAR, 1);
        }
        return next2AM.getTimeInMillis() - currentTime.getTimeInMillis();
    }

    public static void cancelDateUpdates() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
