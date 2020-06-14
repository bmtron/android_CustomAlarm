package com.example.customalarms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private String channelId = "125";
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context con, Intent intent) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(con, alarmUri);
        ringtone.play();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        ringtone.stop();
                    }
                },
        5000);
        buildNotification(con);
        Toast.makeText(con, "ALARM", Toast.LENGTH_LONG).show();
    }

    private void buildNotification(Context con) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(con, channelId)
                .setSmallIcon(R.drawable.notification_small)
                .setContentTitle("Alarm is ringing")
                .setContentText("Alarm is going off")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(125, builder.build());
    }

}
