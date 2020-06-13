package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetNewAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_alarm);
        setupButtons();
    }

    private void setupButtons() {
        Button saveAlarm = (Button) findViewById(R.id.btnSaveAlarm);
        Button pickTime = (Button) findViewById(R.id.btnPickTime);

        saveAlarm.setText("Save Alarm");

        setSaveAlarmListener(saveAlarm);
        setPickTimeListener(pickTime);

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

                Intent backToBase = new Intent(v.getContext(), MainActivity.class);
                backToBase.putExtra("alarmtime", alarmTime);
                startActivity(backToBase);
                finish();
            }
        });
    }
}
