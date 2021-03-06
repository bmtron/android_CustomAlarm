package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import com.example.customalarms.GlobalVars;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MathProblems extends AppCompatActivity {
    Button AlarmOff;
    private TextView MathQuestion;
    private AlarmsDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;

        getWindow().addFlags(flags);
        setContentView(R.layout.layout_mathproblems);
        db = Room.databaseBuilder(getApplicationContext(), AlarmsDB.class, "alarms_data.db").createFromAsset("databases/alarms_data.db").allowMainThreadQueries().build();
        setupAlarmOff();

    }

    public void setupAlarmOff() {
        AlarmOff = (Button) findViewById(R.id.btnAlarmOff);
        MathQuestion = (TextView) findViewById(R.id.mathQuestion);
        int randomRecId = getRandomRecID();
        final Math mathProblem = db.mathDao().getRandomByRecId(randomRecId);
        MathQuestion.setText(mathProblem.Questions);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        final Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);
        AlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usrAnswer = (EditText) findViewById(R.id.usrAnswer);
                String answer = usrAnswer.getText().toString();
                if (!answer.equals(mathProblem.Answers)) {
                    Toast.makeText(MathProblems.this, "Wrong Answer, try again!", Toast.LENGTH_LONG).show();
                    usrAnswer.setText("");
                }
                else {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(GlobalVars.channelId);
                    Intent stopIntent = new Intent(MathProblems.this, RingtoneService.class);
                    AlarmMediaPlayer.stopAlarmAudio();

                    Intent i = new Intent(MathProblems.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private int getRandomRecID() {
        List<Math> math = db.mathDao().getAllMath();

        int size = math.size();
        int test = size;

        Random random = new Random();
        int rand = random.nextInt(math.size()) + 1;

        return rand;
    }
}
