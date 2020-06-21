package com.example.customalarms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/***
 * Deprecated and no longer needed. See AlarmMediaPlayer class as it has taken over background functionality
 */
public class RingtoneService extends Service {
    private String channelId = "125";

    private Ringtone ringtone;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        this.ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        buildNotification(getApplicationContext());
        ringtone.play();

        return START_STICKY;
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

    @Override
    public void onDestroy()
    {
        ringtone.stop();
    }
}
