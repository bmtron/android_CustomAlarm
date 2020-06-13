package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.customalarms.GlobalVars;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String extras = i.getStringExtra("alarmtime");

        ArrayList<ArrayList<String>> alarmTime;

        MyDBHandler dbHandler = new MyDBHandler(MainActivity.this, "alarms_data.db", null, 1);
        alarmTime = dbHandler.loadHandler();
        if (alarmTime.size() != 0) {
            setupListViewTest(alarmTime);
        }
        setupButtons();
    }

    private void setupButtons() {
        final Button newAlarm = (Button) findViewById(R.id.btnNewAlarm);
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
    private void setupListViewTest(ArrayList<ArrayList<String>> alarmTime) {

        for (final ArrayList<String> var : alarmTime)
        {
            final RelativeLayout alarmLayout = new RelativeLayout(this);
            final TextView alarmItem = new TextView(this);
            final Button btnEditAlarm = new Button(this);

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
            setupTimeDisplay(alarmItem);
            setupEditButton(btnEditAlarm);
        }


    }

    private void setupTimeDisplay(TextView time) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)time.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        time.setLayoutParams(params);
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
}
