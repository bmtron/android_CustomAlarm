package com.example.customalarms;

import android.app.NotificationManager;
import android.app.PendingIntent;
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


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context con, Intent intent) {
        AlarmMediaPlayer.playAlarmAudio(con);
        buildNotification(con);

    }

    private void buildNotification(Context con) {
        Intent intent = new Intent(con, MathProblems.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(con, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(con, Integer.toString(GlobalVars.channelId))
                .setSmallIcon(R.drawable.notification_small)
                .setContentTitle("Alarm is ringing")
                .setContentText("Tap here to solve a puzzle and turn off the alarm.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                //.setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true)
                .setOngoing(true);

        NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(GlobalVars.channelId, builder.build());
    }

}
