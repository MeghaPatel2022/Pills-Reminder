package com.cubezytech.pillsreminder.Activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.cubezytech.pillsreminder.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayReminderActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_time_title)
    TextView tv_time_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.check_morning_reminder)
    AppCompatCheckBox check_morning_reminder;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_day_reminder);

        ButterKnife.bind(DayReminderActivity.this);

        check_morning_reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("LLLL_Check: ", String.valueOf(isChecked));

                if (isChecked) {
                    tv_time_title.setTextColor(getResources().getColor(R.color.black));
                    ll_time.setEnabled(true);
                    ll_time.setOnClickListener(DayReminderActivity.this);
                } else {
                    tv_time_title.setTextColor(getResources().getColor(R.color.string_color));
                    ll_time.setEnabled(false);
                    ll_time.setOnClickListener(null);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_time) {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(DayReminderActivity.this, R.style.CustomPickerTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    String AM_PM = " AM";
                    String mm_precede = "";
                    if (hourOfDay >= 12) {
                        AM_PM = " PM";
                        if (hourOfDay >= 13 && hourOfDay < 24) {
                            hourOfDay -= 12;
                        } else {
                            hourOfDay = 12;
                        }
                    } else if (hourOfDay == 0) {
                        hourOfDay = 12;
                    }
                    if (minute < 10) {
                        mm_precede = "0";
                    }
                    tv_time.setText(hourOfDay + ":" + mm_precede + minute + AM_PM);
                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.show();
        }
    }
}