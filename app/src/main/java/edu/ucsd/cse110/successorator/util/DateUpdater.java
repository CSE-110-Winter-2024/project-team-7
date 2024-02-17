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

    public static void scheduleDateUpdates(DateHandler dateData) {
        handler = new Handler(Looper.getMainLooper());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dateData.updateDate((String) null);
                    }
                });
            }
        }, calculateDelayToMidnight(Calendar.getInstance()), 24 * 60 * 60 * 1000);
    }

    public static long calculateDelayToMidnight(Calendar currentTime) {
        Calendar midnight = (Calendar) currentTime.clone();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.add(Calendar.DAY_OF_YEAR, 1);
        return midnight.getTimeInMillis() - currentTime.getTimeInMillis();
    }

    public static void cancelDateUpdates() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

