package com.example.customalarms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String extras = i.getStringExtra("alarmtime");

        if (extras != null) {
            setupListViewTest(extras);
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

    private void setupListViewTest(String alarmTime) {

            final TextView tvTest = new TextView(this);
            tvTest.setClickable(true);

            tvTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    tvTest.setBackgroundColor(Color.parseColor("#CAC8C8"));
                    Intent goToCreateAlarm = new Intent(v.getContext(), SetNewAlarm.class);
                    startActivity(goToCreateAlarm);
                    finish();

                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
            params.setMargins(0, 10, 10, 0);

            LinearLayout lv = (LinearLayout)findViewById(R.id.LinearLayout_List);
            tvTest.setBackgroundColor(Color.parseColor("#E0DEDE"));
            tvTest.setLayoutParams(params);
            tvTest.setText(alarmTime);
            tvTest.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            tvTest.setTextColor(Color.parseColor("#000000"));
            lv.addView(tvTest);

    }
}
