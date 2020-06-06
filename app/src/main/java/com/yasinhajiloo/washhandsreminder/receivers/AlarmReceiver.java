package com.yasinhajiloo.washhandsreminder.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.yasinhajiloo.washhandsreminder.R;
import com.yasinhajiloo.washhandsreminder.views.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private String NOTIFICATION_CHANNEL_REMINDER = "channel_reminder";
    private int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        setUpNotification(context);
    }

    private void setUpNotification(Context context) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel;
        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_REMINDER
                    , "شست و شوی دست", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("ایادآوری شست و شو یا ضدعفونی دست");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_REMINDER)
                .setContentTitle("یادت نره دستات رو بشوری!")
                .setSmallIcon(R.drawable.ic_washing_hands)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
