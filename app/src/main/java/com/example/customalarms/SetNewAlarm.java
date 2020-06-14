package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class SetNewAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_alarm);
        Intent intent = getIntent();
        String recid = intent.getStringExtra("alarmRecid");

        if (!(recid == null)) {
            setupEditable(recid);
            loadExistingAlarm(recid);
        }
        else {
            setupButtons();
        }

    }

    private void setupButtons() {
        Button saveAlarm = (Button) findViewById(R.id.btnSaveAlarm);
        Button pickTime = (Button) findViewById(R.id.btnPickTime);
        Button cancel = (Button)findViewById(R.id.btnCancel);

        pickTime.setText("Set Time");
        cancel.setText("Cancel");
        saveAlarm.setText("Save Alarm");

        setCancelListener(cancel);
        setSaveAlarmListener(saveAlarm);
        setPickTimeListener(pickTime);
    }

    private void setupEditable(String recid) {
        Button saveAlarm = (Button) findViewById(R.id.btnSaveAlarm);
        Button pickTime = (Button) findViewById(R.id.btnPickTime);
        Button cancel = (Button)findViewById(R.id.btnCancel);

        pickTime.setText("Set Time");
        cancel.setText("Cancel");
        saveAlarm.setText("Save Alarm");

        setCancelListener(cancel);
        setUpdateAlarmListener(saveAlarm, recid);
        setPickTimeListener(pickTime);
    }

    private void loadExistingAlarm(String recid) {
        MyDBHandler dbHandler = new MyDBHandler(SetNewAlarm.this, "alarms_data.db", null, 1);
        String existingText = dbHandler.loadByRecID(recid);
        TextView timeDisplay = (TextView) findViewById(R.id.showTime);
        timeDisplay.setText(existingText);
    }

    private void setCancelListener(Button cancel) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(SetNewAlarm.this, MainActivity.class);
                startActivity(backToHome);
                finish();
            }
        });
    }

    private void setPickTimeListener(Button pickTime) {
        final TextView  timeDisplay = (TextView) findViewById(R.id.showTime);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetNewAlarm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String newSelectedMinute;
                        String newSelectedHour;
                        boolean isMorning = true;

                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            isMorning = false;
                        }
                        if (selectedMinute < 10) {
                            newSelectedMinute = "0" + selectedMinute;
                        }
                        else {
                            newSelectedMinute = Integer.toString(selectedMinute);
                        }
                        newSelectedHour = Integer.toString(selectedHour);

                        String formattedTime;

                        if (isMorning){
                            formattedTime = newSelectedHour + ":" + newSelectedMinute + " AM";
                        }
                        else {
                            formattedTime = newSelectedHour + ":" + newSelectedMinute + " PM";
                        }
                        timeDisplay.setText(formattedTime);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select time");
                mTimePicker.show();
            }
        });
    }

    private void setSaveAlarmListener(Button saveAlarm) {
        final TextView  timeDisplay = (TextView) findViewById(R.id.showTime);
        saveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alarmTime = timeDisplay.getText().toString();
                MyDBHandler dbHandler = new MyDBHandler(SetNewAlarm.this, "alarms_data.db",null, 1);
                dbHandler.addHandler(alarmTime);
                Intent backToBase = new Intent(v.getContext(), MainActivity.class);
                backToBase.putExtra("alarmtime", alarmTime);
                startActivity(backToBase);
                finish();
            }
        });
    }

    private void setUpdateAlarmListener(Button saveAlarm, final String recid) {
        final TextView  timeDisplay = (TextView) findViewById(R.id.showTime);

        saveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String alarmTime = timeDisplay.getText().toString();
                MyDBHandler dbHandler = new MyDBHandler(SetNewAlarm.this, "alarms_data.db",null, 1);
                dbHandler.updateHandler(recid, alarmTime);
                Intent backToBase = new Intent(v.getContext(), MainActivity.class);
                backToBase.putExtra("alarmtime", alarmTime);
                startActivity(backToBase);
                finish();
            }
        });
    }
}
