package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customalarms.GlobalVars;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Intent mServiceIntent;
    private RingtoneService mRingtoneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Intent i = getIntent();
        String extras = i.getStringExtra("alarmtime");

        ArrayList<ArrayList<String>> alarmTime;

        MyDBHandler dbHandler = new MyDBHandler(MainActivity.this, "alarms_data.db", null, 1);
        alarmTime = dbHandler.loadHandler();
        if (alarmTime.size() != 0) {
            setupListViewTest(alarmTime);
        }
        int switchOnCount = 1000;
        for (ArrayList<String> item : alarmTime) {
            String test = item.get(2);
            if (item.get(2).equals("1")) {
                Switch s = (Switch) findViewById(switchOnCount);
                s.setChecked(true);
            }
            else {
                Switch s = (Switch) findViewById(switchOnCount);
                s.setChecked(false);
            }
            switchOnCount++;
        }
        setupButtons();
        mRingtoneService = new RingtoneService();
        mServiceIntent = new Intent(this, RingtoneService.class);


    }

    private void setupButtons() {
        final Button newAlarm = (Button) findViewById(R.id.btnNewAlarm);
        final Button clearDB = (Button) findViewById(R.id.btnClearDb);

        setClearDbClick(clearDB);
        newAlarm.setText("New Alarm");

        newAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent goToCreateAlarm = new Intent(v.getContext(), SetNewAlarm.class);
                startActivity(goToCreateAlarm);
                finish();
            }
        });
    }
    private void setAlarmItemClick(final TextView alarm) {
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                alarm.setBackgroundColor(Color.parseColor("#CAC8C8"));
            }
        });
    }

    private void setClearDbClick(final Button clear) {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler db = new MyDBHandler(MainActivity.this, "alarms_data.db", null, 1);
                db.clearDB();
                Toast.makeText(MainActivity.this, "Database cleared successfully.", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        });
    }
    private void setupListViewTest(ArrayList<ArrayList<String>> alarmTime) {
        int count = 1000;
        for (final ArrayList<String> var : alarmTime)
        {
            final RelativeLayout alarmLayout = new RelativeLayout(this);
            final TextView alarmItem = new TextView(this);
            final Button btnEditAlarm = new Button(this);
            final Switch toggleAlarm = new Switch(this);
            toggleAlarm.setId(count);
            count++;

            String time = var.get(1);
            String[] timeTextArr = time.split(" ");
            String[] timeHoursMinsArr = timeTextArr[0].split(":");
            setLayoutAndListeners(alarmLayout, alarmItem, btnEditAlarm, toggleAlarm, var);
            boolean morning = parseMorning(timeTextArr[1]);
            int hour = parseHour(timeHoursMinsArr[0]);
            int minute = parseMinute((timeHoursMinsArr[1]));
            setupSwitchLogic(toggleAlarm, hour, minute, morning, var.get(0));


        }


    }

    private void setLayoutAndListeners(RelativeLayout alarmLayout, TextView alarmItem, Button btnEditAlarm, Switch toggleAlarm, ArrayList<String> var) {
        alarmItem.setClickable(true);
        setAlarmItemClick(alarmItem);
        setEditAlarmClick(btnEditAlarm, var.get(0));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        params.setMargins(0, 10, 10, 10);

        LinearLayout rl = (LinearLayout) findViewById(R.id.LinearLayout_List);
        rl.addView(alarmLayout);

        alarmLayout.setBackgroundColor(Color.parseColor("#E0DEDE"));
        alarmLayout.setLayoutParams(params);
        alarmItem.setTag(var.get(0));
        alarmItem.setText(var.get(1));
        alarmItem.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        alarmItem.setTextColor(Color.parseColor("#000000"));
        alarmLayout.addView(alarmItem);
        alarmLayout.addView(btnEditAlarm);
        alarmLayout.addView(toggleAlarm);
        setupTimeDisplay(alarmItem);
        setupEditButton(btnEditAlarm);
        setupToggleSwitch(toggleAlarm);
    }
    private void setupSwitchLogic(final Switch alarmOnOff, final int hour, final int minute, final boolean morning, final String recid) {
        final MyDBHandler db = new MyDBHandler(MainActivity.this, "alarms_data.db", null, 1);


        alarmOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked) {
                  db.updateAlarmOn(recid, "1");
                  setAlarm(hour, minute, morning);
              }
              else {
                  db.updateAlarmOn(recid, "0");
                  AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                  Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                  PendingIntent pIntent = PendingIntent.getBroadcast(MainActivity.this, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                  pIntent.cancel();
                  manager.cancel(pIntent);
              }
            }
        });
    }
    private void setupTimeDisplay(TextView time) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)time.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        time.setLayoutParams(params);
    }

    private void setupToggleSwitch(Switch s) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)s.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        s.setLayoutParams(params);

    }
    private void setupEditButton(Button edit) {

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)edit.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        edit.setLayoutParams(params);
        edit.setText("Edit");
        //params.addRule(RelativeLayout.RIGHT_OF, );
    }

    private void setEditAlarmClick(Button btnEditAlarm, final String recid) {
        btnEditAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateAlarm = new Intent(MainActivity.this, SetNewAlarm.class);
                goToCreateAlarm.putExtra("alarmRecid", recid);
                startActivity(goToCreateAlarm);
                finish();
            }
        });
    }

    private int parseHour(String hour) {
        int newHour = Integer.parseInt(hour);

        return newHour;
    }
    private int parseMinute(String minute) {
        int newMinute = Integer.parseInt(minute);

        return newMinute;
    }
    private boolean parseMorning(String morning) {
        if (morning.equals("AM")) {
            return true;
        }
        else {
            return false;
        }
    }
    public void setAlarm(int hour, int minute, boolean morning) {
        if (!morning) {
            hour += 12;
        }
        if (morning && (hour == 12)) {
            hour = 0;
        }

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(date);
        cal_alarm.setTime(date);
        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);

        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent nIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, nIntent, 0);

        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pIntent);
    }

    private void createNotificationChannel() {
        CharSequence name = "Alarm Notification";
        String description = "The main alarm notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("125", name, importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
