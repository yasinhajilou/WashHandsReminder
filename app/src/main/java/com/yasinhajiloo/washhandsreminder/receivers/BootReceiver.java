package com.yasinhajiloo.washhandsreminder.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.yasinhajiloo.washhandsreminder.constants.MySharedPreferenceConstants;
import com.yasinhajiloo.washhandsreminder.utils.MyAlarmManager;

import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {

    SharedPreferences mSharedPreferences;
    AlarmManager mAlarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            mSharedPreferences = context.getSharedPreferences(MySharedPreferenceConstants.sharedPreferenceName, Context.MODE_PRIVATE);
            mAlarmManager = MyAlarmManager.getAlarmManager(context);
            long time = mSharedPreferences.getLong(MySharedPreferenceConstants.KEY_LONG_TIME, 0);
            if (time > 0){
                mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + time , time , MyAlarmManager.getPendingIntent(context ,
                                PendingIntent.FLAG_UPDATE_CURRENT));
            }

        }
    }
}
