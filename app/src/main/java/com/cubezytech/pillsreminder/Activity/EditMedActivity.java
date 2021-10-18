package com.cubezytech.pillsreminder.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMedActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.tv_medName)
    TextView tv_medName;
    @BindView(R.id.tv_schedule)
    TextView tv_schedule;
    @BindView(R.id.et_strength)
    EditText et_strength;
    @BindView(R.id.et_pills)
    EditText et_pills;
    DBHelper dbHelper;
    TimeInWeek timeInWeek;
    TimeInDay timeInDay;
    private String id1 = "";
    private String from = "";
    private String date = "";
    private String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_med);

        id1 = getIntent().getStringExtra("id");
        from = getIntent().getStringExtra("from");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");

        ButterKnife.bind(EditMedActivity.this);

        dbHelper = new DBHelper(EditMedActivity.this);

        if (from.equals("TimeInDay")) {
            timeInDay = dbHelper.getPillsNoRecord(id1, date, time);
            tv_medName.setText(timeInDay.getMed_Name());
            tv_schedule.setText("Scheduled for " + timeInDay.getTime() + ", " + ", " + timeInDay.getDate());
        } else if (from.equals("TimeInWeek")) {
            timeInWeek = dbHelper.getPillsNoRecordYes(id1, date, time);
            tv_medName.setText(timeInWeek.getMed_name());
            tv_schedule.setText("Scheduled for " + timeInWeek.getTime() + ", " + ", " + timeInWeek.getDate());
        }

        tv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_save) {
            if (from.equals("TimeInDay"))
                dbHelper.updatePillsNoRecords(id1, et_strength.getText().toString().trim(), et_pills.getText().toString().trim(), date, time);
            else if (from.equals("TimeInWeek"))
                dbHelper.updateNoPillsDetailsYes(id1, et_strength.getText().toString().trim(), et_pills.getText().toString().trim(), date, time);

            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}