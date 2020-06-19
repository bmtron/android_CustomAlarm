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
    private String channelId = "125";
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context con, Intent intent) {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(con, ringtoneUri);

        //r.play();
        Intent startIntent = new Intent(con, RingtoneService.class);
        PendingIntent pIntent = PendingIntent.getActivity(con, 0, startIntent, 0);
        con.startService(startIntent);

        buildNotification(con);
        Toast.makeText(con, "ALARM", Toast.LENGTH_LONG).show();
    }

    private void buildNotification(Context con) {
        Intent intent = new Intent(con, MathProblems.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(con, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(con, channelId)
                .setSmallIcon(R.drawable.notification_small)
                .setContentTitle("Alarm is ringing")
                .setContentText("Tap here to solve a puzzle and turn off the alarm.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        NotificationManager notificationManager = (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(125, builder.build());
    }

}
