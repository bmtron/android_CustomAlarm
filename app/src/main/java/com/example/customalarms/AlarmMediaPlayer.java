package com.example.customalarms;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;


public class AlarmMediaPlayer {

    public static MediaPlayer mediaPlayer;
    private static Uri ringtone;

    public static boolean isPlaying = false;
    public static void playAlarmAudio(Context con) {
        ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(con, ringtone);
        if (!mediaPlayer.isPlaying()) {
            isPlaying = true;
            mediaPlayer.start();
        }
    }
    public static void stopAlarmAudio() {
        isPlaying = false;
        mediaPlayer.stop();
    }
}
