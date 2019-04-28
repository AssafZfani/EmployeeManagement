package zfani.assaf.employee_management;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

import zfani.assaf.employee_management.receivers.NotifyReceiver;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (BuildConfig.DEBUG && !preferences.getBoolean("AlarmManagerSetUp", false)) {
            preferences.edit().putBoolean("AlarmManagerSetUp", true).apply();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            ((AlarmManager) getSystemService(ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    PendingIntent.getBroadcast(this, AlarmManager.RTC_WAKEUP, new Intent(this, NotifyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }
}
