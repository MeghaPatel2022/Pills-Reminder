package com.cubezytech.pillsreminder.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cubezytech.pillsreminder.Pref.SharedPrefrance;
import com.cubezytech.pillsreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ll_reminder)
    LinearLayout ll_reminder;
    @BindView(R.id.ll_notification)
    LinearLayout ll_notification;
    @BindView(R.id.ll_rx_refill)
    LinearLayout ll_rx_refill;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.ll_rate)
    LinearLayout ll_rate;
    @BindView(R.id.ll_about)
    LinearLayout ll_about;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(SettingsActivity.this);

        ll_reminder.setOnClickListener(this);
        ll_notification.setOnClickListener(this);
        ll_rx_refill.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_rate.setOnClickListener(this);
        ll_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reminder:
                Intent reminderIntent = new Intent(SettingsActivity.this, ReminderSettingActivity.class);
                startActivity(reminderIntent);
                break;
            case R.id.ll_notification:
                Intent notificationIntent = new Intent(SettingsActivity.this, NotificationActivity.class);
                startActivity(notificationIntent);
                break;
            case R.id.ll_rx_refill:
                showRefillDialog();
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_rate:
                break;
            case R.id.ll_about:
                break;
        }
    }

    private void showRefillDialog() {

        String[] refill_time = {"No reminder", "2", "5", "10", "15", "20", "Custom"};

        dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_rx_refill);
        dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);

        tv_title.setText("Default number of pills reminder");

        for (int i = 0; i < refill_time.length; i++) {
            String account = refill_time[i];
            String possibleEmail = account;
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(30, 30, 7, 30);
            radioButton.setText(possibleEmail);
            radioButton.setId(i);

            radioGroup.addView(radioButton);
            if (possibleEmail.equals(SharedPrefrance.getRefillTime(SettingsActivity.this))) {
                radioButton.setChecked(true);
                radioButton.setButtonTintList(getResources().getColorStateList(R.color.blue));
            }
        }

        //set listener to radio button group
        radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
            int checkedRadioButtonId = group.getCheckedRadioButtonId();
            RadioButton radioBtn = (RadioButton) dialog.findViewById(checkedRadioButtonId);
            radioBtn.setButtonTintList(getResources().getColorStateList(R.color.blue));
            Log.e("LLLLL_Refill_time: ", String.valueOf(radioBtn.getText()));
            SharedPrefrance.setRefillTimee(SettingsActivity.this, String.valueOf(radioBtn.getText()));
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}