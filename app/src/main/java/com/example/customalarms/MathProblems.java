package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import com.example.customalarms.GlobalVars;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MathProblems extends AppCompatActivity {
    Button AlarmOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mathproblems);
        setupAlarmOff();
    }

    public void setupAlarmOff() {
        AlarmOff = (Button) findViewById(R.id.btnAlarmOff);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);
        AlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(125);
                Intent stopIntent = new Intent(MathProblems.this, RingtoneService.class);
                stopService(stopIntent);

                Intent i = new Intent(MathProblems.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
