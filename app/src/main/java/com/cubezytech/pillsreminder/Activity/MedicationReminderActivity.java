package com.cubezytech.pillsreminder.Activity;

import android.app.Dialog;
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

public class MedicationReminderActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ll_medication)
    LinearLayout ll_medication;
    @BindView(R.id.ll_snooze)
    LinearLayout ll_snooze;
    @BindView(R.id.ll_popup)
    LinearLayout ll_popup;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medication_reminder);

        ButterKnife.bind(MedicationReminderActivity.this);

        ll_medication.setOnClickListener(this);
        ll_snooze.setOnClickListener(this);
        ll_popup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_medication:
                showMedicationDialog();
                break;
            case R.id.ll_snooze:
                showSnoozeDialog();
                break;
            case R.id.ll_popup:
                showPopUpDialoge();
                break;
        }
    }

    private void showPopUpDialoge() {
        String[] snooze_time = {"No popup", "Only when screen is ON", "Always show popup"};
        dialog = new Dialog(MedicationReminderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_rx_refill);
        dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);

        tv_title.setText("Popup notification");

        for (int i = 0; i < snooze_time.length; i++) {
            String account = snooze_time[i];
            String possibleEmail = account;
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(30, 30, 7, 30);
            radioButton.setText(possibleEmail);
            radioButton.setId(i);

            radioGroup.addView(radioButton);
            if (possibleEmail.equals(SharedPrefrance.getPopup(MedicationReminderActivity.this))) {
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
            SharedPrefrance.setPopup(MedicationReminderActivity.this, String.valueOf(radioBtn.getText()));
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showSnoozeDialog() {

        String[] snooze_time = {"5", "10", "15", "30", "60", "90", "120"};
        dialog = new Dialog(MedicationReminderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_rx_refill);
        dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);

        tv_title.setText("Snooze duration");

        for (int i = 0; i < snooze_time.length; i++) {
            String account = snooze_time[i];
            String possibleEmail = account;
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(30, 30, 7, 30);
            radioButton.setText(possibleEmail);
            radioButton.setId(i);

            radioGroup.addView(radioButton);
            if (possibleEmail.equals(SharedPrefrance.getSnoozeTime(MedicationReminderActivity.this))) {
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
            SharedPrefrance.setSnoozeTime(MedicationReminderActivity.this, String.valueOf(radioBtn.getText()));
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showMedicationDialog() {

        dialog = new Dialog(MedicationReminderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_rx_refill);
        dialog.getWindow().setLayout(getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);

        tv_title.setText("Default number of pills");

        for (int i = 0; i < 10; i++) {
            String account = String.valueOf(i + 1);
            String possibleEmail = account;
            RadioButton radioButton = new RadioButton(this);
            radioButton.setPadding(30, 30, 7, 30);
            radioButton.setText(possibleEmail);
            radioButton.setId(i);

            radioGroup.addView(radioButton);
            if (possibleEmail.equals(SharedPrefrance.getMedReminder(MedicationReminderActivity.this))) {
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
            SharedPrefrance.setMedReminder(MedicationReminderActivity.this, String.valueOf(radioBtn.getText()));
            dialog.dismiss();
        });

        dialog.show();
    }
}