package com.cubezytech.pillsreminder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cubezytech.pillsreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderSettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ll_med_reminder)
    LinearLayout ll_med_reminder;
    @BindView(R.id.ll_morning_reminder)
    LinearLayout ll_morning_reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reminder_setting);

        ButterKnife.bind(ReminderSettingActivity.this);

        ll_med_reminder.setOnClickListener(this);
        ll_morning_reminder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_med_reminder:
                Intent medIntent = new Intent(ReminderSettingActivity.this, MedicationReminderActivity.class);
                startActivity(medIntent);
                break;
            case R.id.ll_morning_reminder:
                Intent eveReminderIntent = new Intent(ReminderSettingActivity.this, DayReminderActivity.class);
                startActivity(eveReminderIntent);
                break;
        }
    }
}