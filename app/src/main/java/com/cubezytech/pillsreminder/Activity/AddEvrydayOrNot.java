package com.cubezytech.pillsreminder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.bumptech.glide.Glide;
import com.cubezytech.pillsreminder.Adapter.NumberAdapter;
import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.ycuwq.datepicker.date.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEvrydayOrNot extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rl_first)
    RelativeLayout rl_first;
    @BindView(R.id.rl_second)
    RelativeLayout rl_second;
    @BindView(R.id.rl_third)
    RelativeLayout rl_third;
    @BindView(R.id.rl_forth)
    RelativeLayout rl_forth;
    @BindView(R.id.rl_fifth)
    RelativeLayout rl_fifth;
    @BindView(R.id.rl_six)
    RelativeLayout rl_six;
    @BindView(R.id.rl_seven)
    RelativeLayout rl_seven;
    @BindView(R.id.rl_second_no)
    RelativeLayout rl_second_no;
    @BindView(R.id.rl_days_list)
    RelativeLayout rl_days_list;
    @BindView(R.id.rl_eight)
    RelativeLayout rl_eight;
    @BindView(R.id.rl_nine)
    RelativeLayout rl_nine;
    @BindView(R.id.rl_loader)
    RelativeLayout rl_loader;

    @BindView(R.id.segmented)
    SegmentedButtonGroup segmented;

    @BindView(R.id.tv_yes)
    TextView tv_yes;
    @BindView(R.id.tv_no)
    TextView tv_no;

    @BindView(R.id.tv_once_daily)
    TextView tv_once_daily;
    @BindView(R.id.tv_twice_daily)
    TextView tv_twice_daily;
    @BindView(R.id.tv_3_daily)
    TextView tv_3_daily;
    @BindView(R.id.tv_4_daily)
    TextView tv_4_daily;
    @BindView(R.id.tv_6_daily)
    TextView tv_6_daily;
    @BindView(R.id.tv_custom)
    TextView tv_custom;

    @BindView(R.id.tv_morning)
    TextView tv_morning;
    @BindView(R.id.tv_after_noon)
    TextView tv_after_noon;
    @BindView(R.id.tv_evening)
    TextView tv_evening;
    @BindView(R.id.tv_night)
    TextView tv_night;

    @BindView(R.id.tv_once_week)
    TextView tv_once_week;
    @BindView(R.id.tv_2days_week)
    TextView tv_2days_week;
    @BindView(R.id.tv_3days_week)
    TextView tv_3days_week;
    @BindView(R.id.tv_4days_week)
    TextView tv_4days_week;
    @BindView(R.id.tv_5days_week)
    TextView tv_5days_week;
    @BindView(R.id.tv_6days_week)
    TextView tv_6days_week;
    @BindView(R.id.tv_only_need)
    TextView tv_only_need;
    @BindView(R.id.tv_other)
    TextView tv_other;

    @BindView(R.id.tv_days_title)
    TextView tv_days_title;
    @BindView(R.id.img_next_days)
    ImageView img_next_days;
    @BindView(R.id.img_next_select_days)
    ImageView img_next_select_days;
    @BindView(R.id.tv_sunday)
    TextView tv_sunday;
    @BindView(R.id.tv_monday)
    TextView tv_monday;
    @BindView(R.id.tv_tuesday)
    TextView tv_tuesday;
    @BindView(R.id.tv_wednesday)
    TextView tv_wednesday;
    @BindView(R.id.tv_thursday)
    TextView tv_thursday;
    @BindView(R.id.tv_friday)
    TextView tv_friday;
    @BindView(R.id.tv_saturday)
    TextView tv_saturday;

    @BindView(R.id.img_edit_pills)
    ImageView img_edit_pills;
    @BindView(R.id.img_next_select_day_time)
    ImageView img_next_select_day_time;
    @BindView(R.id.img_save)
    ImageView img_save;
    @BindView(R.id.et_hours)
    EditText et_hours;
    @BindView(R.id.et_min)
    EditText et_min;

    @BindView(R.id.tv_specific_time)
    TextView tv_specific_time;
    @BindView(R.id.tv_every_num_hrs)
    TextView tv_every_num_hrs;
    @BindView(R.id.img_next_6)
    ImageView img_next_6;

    @BindView(R.id.date_picker)
    DatePicker date_picker;
    @BindView(R.id.img_next_8)
    ImageView img_next_8;
    @BindView(R.id.tv_num_days)
    TextView tv_num_days;

    @BindView(R.id.img_loader)
    ImageView img_loader;

    @BindView(R.id.rv_picker)
    DiscreteScrollView rv_picker;

    ArrayList<Integer> list = new ArrayList<>();
    NumberAdapter numberAdapter;

    ArrayList<String> amPmList = new ArrayList<>();

    DBHelper dbHelper;
    private Date current_date;
    private Date current_date1;

    private int startDay;
    private int startMonth;
    private int startYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.activity_add_evryday_or_not);

        dbHelper = new DBHelper(AddEvrydayOrNot.this);
        ButterKnife.bind(AddEvrydayOrNot.this);

        Glide.with(AddEvrydayOrNot.this)
                .load(R.drawable.loding_gif)
                .into(img_loader);

        amPmList.clear();
        amPmList.add("AM");
        amPmList.add("PM");
        segmented.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                Log.e("LLLLL_Segment_Pos: ", String.valueOf(position));
            }
        });

        setAdapter(24);
        initByOnClick();
    }

    private void initByOnClick() {
        tv_yes.setOnClickListener(this);
        tv_no.setOnClickListener(this);
        tv_once_daily.setOnClickListener(this);
        tv_twice_daily.setOnClickListener(this);
        tv_3_daily.setOnClickListener(this);
        tv_4_daily.setOnClickListener(this);
        tv_6_daily.setOnClickListener(this);
        tv_custom.setOnClickListener(this);
        tv_morning.setOnClickListener(this);
        tv_after_noon.setOnClickListener(this);
        tv_evening.setOnClickListener(this);
        tv_night.setOnClickListener(this);
        img_edit_pills.setOnClickListener(this);
        tv_once_week.setOnClickListener(this);
        tv_2days_week.setOnClickListener(this);
        tv_3days_week.setOnClickListener(this);
        tv_4days_week.setOnClickListener(this);
        tv_5days_week.setOnClickListener(this);
        tv_6days_week.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_num_days.setOnClickListener(this);
        img_next_8.setOnClickListener(this);
        tv_sunday.setOnClickListener(this);
        tv_monday.setOnClickListener(this);
        tv_tuesday.setOnClickListener(this);
        tv_wednesday.setOnClickListener(this);
        tv_thursday.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_saturday.setOnClickListener(this);
        img_next_select_days.setOnClickListener(this);
        img_next_select_day_time.setOnClickListener(this);
        tv_specific_time.setOnClickListener(this);
        tv_every_num_hrs.setOnClickListener(this);
        img_next_6.setOnClickListener(this);
        img_save.setOnClickListener(this);
    }

    private void setWeekDays() {
        if (Constant.TIMEINWEEKLIST.size() > 0) {
            TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(Constant.TIMEINWEEKLIST.size() - 1);
            TimeInWeek timeInWeek1 = new TimeInWeek();
            timeInWeek1.setY_id(timeInWeek.getY_id());
            timeInWeek1.setMed_name(timeInWeek.getMed_name());
            timeInWeek1.setMed_type(timeInWeek.getMed_type());
            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
            timeInWeek1.setWeek_days_in(String.valueOf(Constant.WEEK_DAYS));
            Constant.TIMEINWEEKLIST.set(Constant.TIMEINWEEKLIST.size() - 1, timeInWeek1);
        } else {
            TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(Constant.TIMEINWEEKLIST.size() - 1);
            TimeInWeek timeInWeek1 = new TimeInWeek();
            timeInWeek1.setY_id(timeInWeek.getY_id());
            timeInWeek1.setMed_name(timeInWeek.getMed_name());
            timeInWeek1.setMed_type(timeInWeek.getMed_type());
            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
            timeInWeek1.setWeek_days_in(String.valueOf(Constant.WEEK_DAYS));
            Constant.TIMEINWEEKLIST.add(timeInWeek1);
        }
    }

    private void setTimeInDays() {
        TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
        TimeInDay timeInDay1 = new TimeInDay();
        timeInDay1.setR_id(timeInDay.getR_id());
        timeInDay1.setMed_Name(timeInDay.getMed_Name());
        timeInDay1.setMed_type(timeInDay.getMed_type());
        timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
        timeInDay1.setWeekDays(String.valueOf(Constant.TIME_IN_DAYS));
        Constant.TIMEINDAYSLIST.set(Constant.TIMEINDAYSLIST.size() - 1, timeInDay1);
    }

    private void setTimeInDaysYes(String day) {
        for (int i = 0; i < Constant.TIMEINWEEKLIST.size(); i++) {
            if (Constant.TIMEINWEEKLIST.size() > 0) {
                if (Constant.TIMEINWEEKLIST.size() != Constant.TIME_IN_DAYS || Constant.TIMEINWEEKLIST.size() != 1) {
                    TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(i);
                    TimeInWeek timeInWeek1 = new TimeInWeek();
                    timeInWeek1.setY_id(timeInWeek.getY_id());
                    timeInWeek1.setMed_name(timeInWeek.getMed_name());
                    timeInWeek1.setMed_type(timeInWeek.getMed_type());
                    timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                    timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                    timeInWeek1.setDay(timeInWeek.getDay());
                    timeInWeek1.setTime_in_days(day);
                    Constant.TIMEINWEEKLIST.set(i, timeInWeek1);
                }
            }
        }

        if (!Constant.IS_YES) {
            img_next_select_day_time.setVisibility(View.GONE);
            img_save.setVisibility(View.VISIBLE);
        }

    }

    private void setDays() {
        for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
            if (Constant.TIMEINWEEKLIST.size() > 0) {
//                if (Constant.TIMEINWEEKLIST.size() != Constant.WEEK_DAYS) {
                    if (Constant.TIMEINWEEKLIST.size() == 1 && Constant.TIME_IN_WEEK_DONE == 0) {
                        TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(0);
                        TimeInWeek timeInWeek1 = new TimeInWeek();
                        timeInWeek1.setY_id(timeInWeek.getY_id());
                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                        timeInWeek1.setWeek_days_in(String.valueOf(Constant.WEEK_DAYS));
                        timeInWeek1.setDay(Constant.DAYSLIST.get(i));
//                        if (timeInWeek1.getY_id().equals("0")) {
                        Constant.TIMEINWEEKLIST.set(0, timeInWeek1);
//                        }
                        Constant.TIME_IN_WEEK_DONE++;
                    } else {
                        TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(Constant.TIMEINWEEKLIST.size() - 1);
                        TimeInWeek timeInWeek1 = new TimeInWeek();
                        timeInWeek1.setY_id(timeInWeek.getY_id());
                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                        timeInWeek1.setDay(Constant.DAYSLIST.get(i));
                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
                        Constant.TIME_IN_WEEK_DONE++;
                    }
                }
//            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_yes) {
            Constant.IS_YES = true;
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.GONE) {
                rl_second.setVisibility(View.VISIBLE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_no) {
            Constant.IS_YES = false;
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.GONE) {
                rl_second_no.setVisibility(View.VISIBLE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_once_week) {
            Constant.WEEK_DAYS = 1;
            tv_days_title.setText("Choose 1 day from the list:");

            setWeekDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            if (!Constant.IS_YES) {

            }
        } else if (id == R.id.tv_2days_week) {
            Constant.WEEK_DAYS = 2;

            setWeekDays();

            tv_days_title.setText("Choose 2 days from the list:");
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_3days_week) {
            Constant.WEEK_DAYS = 3;

            setWeekDays();

            tv_days_title.setText("Choose 3 days from the list:");
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_4days_week) {
            Constant.WEEK_DAYS = 4;

            setWeekDays();

            tv_days_title.setText("Choose 4 days from the list:");
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_5days_week) {
            Constant.WEEK_DAYS = 5;

            setWeekDays();

            tv_days_title.setText("Choose 5 days from the list:");
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_6days_week) {
            Constant.WEEK_DAYS = 6;

            setWeekDays();

            tv_days_title.setText("Choose 6 days from the list:");
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.GONE) {
                rl_days_list.setVisibility(View.VISIBLE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_other) {
            Constant.IS_OTHER = true;

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            if (rl_nine.getVisibility() == View.VISIBLE) {
                rl_nine.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.GONE) {
                rl_six.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.tv_once_daily) {
            Constant.TIME_IN_DAYS = 1;
            setTimeInDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            if (img_next_select_day_time.getVisibility() == View.VISIBLE) {
                img_next_select_day_time.setVisibility(View.GONE);
            }
            if (img_save.getVisibility() == View.GONE) {
                img_save.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.tv_twice_daily) {
            Constant.TIME_IN_DAYS = 2;
            setTimeInDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_3_daily) {
            Constant.TIME_IN_DAYS = 3;
            setTimeInDays();
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_4_daily) {
            Constant.TIME_IN_DAYS = 4;
            setTimeInDays();
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_6_daily) {
            Constant.TIME_IN_DAYS = 6;
            setTimeInDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_custom) {
            Constant.IS_CUSTOM = true;
//            setTimeInDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            if (rl_seven.getVisibility() == View.GONE) {
                rl_seven.setVisibility(View.VISIBLE);
            }

        } else if (id == R.id.tv_specific_time) {

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.GONE) {
                rl_six.setVisibility(View.VISIBLE);
            }
            if (rl_seven.getVisibility() == View.VISIBLE) {
                rl_seven.setVisibility(View.GONE);
            }

            setAdapter(24);

        } else if (id == R.id.tv_every_num_hrs) {

            Constant.IS_EVERY_DAY_YES = true;

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.GONE) {
                rl_six.setVisibility(View.VISIBLE);
            }
            if (rl_seven.getVisibility() == View.VISIBLE) {
                rl_seven.setVisibility(View.GONE);
            }

            setAdapter(7);

        } else if (id == R.id.img_next_6) {

            if (Constant.IS_OTHER) {
                Constant.WEEK_DAYS = list.get(rv_picker.getCurrentItem());
                setWeekDays();
                if (rl_nine.getVisibility() == View.GONE) {
                    rl_nine.setVisibility(View.VISIBLE);
                }
            } else {
                if (Constant.IS_EVERY_DAY_YES)
                    Constant.TIME_IN_DAYS = 1;
                else
                    Constant.TIME_IN_DAYS = rv_picker.getCurrentItem() + 1;
                Constant.IS_CUSTOM = true;

                setTimeInDays();

                Constant.EVERY_NUM_HOURS = list.get(rv_picker.getCurrentItem());

                if (rl_third.getVisibility() == View.GONE) {
                    rl_third.setVisibility(View.VISIBLE);
                }
            }

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }

            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            img_next_select_day_time.setVisibility(View.GONE);
            img_save.setVisibility(View.VISIBLE);

        } else if (id == R.id.img_next_8) {
            startDay = date_picker.getDay();
            startMonth = date_picker.getMonth();
            startYear = date_picker.getYear();
            if (rl_eight.getVisibility() == View.VISIBLE) {
                rl_eight.setVisibility(View.GONE);
            }

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_num_days) {
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }

            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_nine.getVisibility() == View.VISIBLE) {
                rl_nine.setVisibility(View.GONE);
            }
            if (rl_eight.getVisibility() == View.GONE) {
                rl_eight.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.tv_morning) {

            if (Constant.IS_YES) {
                if (Constant.TIMEINDAYSLIST.size() > 0) {
                    if (Constant.TIMEINDAYSLIST.size() != Constant.TIME_IN_DAYS || Constant.TIMEINDAYSLIST.size() != 1) {
                        if (Constant.TIMEINDAYSLIST.size() == 1 && Constant.TIME_IN_DAYS_DONE == 0) {
                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(0);
                            timeInDay.setR_id(timeInDay.getR_id());
                            timeInDay.setMed_Name(timeInDay.getMed_Name());
                            timeInDay.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay.setMed_type(timeInDay.getMed_type());
                            timeInDay.setWeekDays(timeInDay.getWeekDays());
                            timeInDay.setTime_In_day("Morning");

                            if (timeInDay.getR_id().equals("0")) {
                                Constant.TIMEINDAYSLIST.set(0, timeInDay);
                            }
                            Constant.TIME_IN_DAYS_DONE++;
                        } else {

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                            TimeInDay timeInDay1 = new TimeInDay();
                            timeInDay1.setR_id(timeInDay.getR_id());
                            timeInDay1.setMed_Name(timeInDay.getMed_Name());
                            timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay1.setMed_type(timeInDay.getMed_type());
                            timeInDay1.setWeekDays(timeInDay.getWeekDays());
                            timeInDay1.setTime_In_day("Morning");

                            Constant.TIMEINDAYSLIST.add(timeInDay1);

                        }
                    }
                }
            } else {
                setTimeInDaysYes("Morning");
            }
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.GONE) {
                rl_forth.setVisibility(View.VISIBLE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }

        } else if (id == R.id.tv_after_noon) {

            if (Constant.IS_YES) {
                if (Constant.TIMEINDAYSLIST.size() > 0) {
                    if (Constant.TIMEINDAYSLIST.size() != Constant.TIME_IN_DAYS || Constant.TIMEINDAYSLIST.size() != 1) {
                        if (Constant.TIMEINDAYSLIST.size() == 1 && Constant.TIME_IN_DAYS_DONE == 0) {
                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(0);
                            timeInDay.setR_id(timeInDay.getR_id());
                            timeInDay.setMed_Name(timeInDay.getMed_Name());
                            timeInDay.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay.setMed_type(timeInDay.getMed_type());
                            timeInDay.setWeekDays(timeInDay.getWeekDays());
                            timeInDay.setTime_In_day("After Noon");
                            if (timeInDay.getR_id().equals("0")) {
                                Constant.TIMEINDAYSLIST.set(0, timeInDay);
                            }
                            Constant.TIME_IN_DAYS_DONE++;
                        } else {

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                            TimeInDay timeInDay1 = new TimeInDay();
                            timeInDay1.setR_id(timeInDay.getR_id());
                            timeInDay1.setMed_Name(timeInDay.getMed_Name());
                            timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay1.setMed_type(timeInDay.getMed_type());
                            timeInDay1.setWeekDays(timeInDay.getWeekDays());
                            timeInDay1.setTime_In_day("After Noon");

                            Constant.TIMEINDAYSLIST.add(timeInDay1);


                        }
                    }
                }
            } else {
                setTimeInDaysYes("After Noon");
            }

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.GONE) {
                rl_forth.setVisibility(View.VISIBLE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_evening) {

            if (Constant.IS_YES) {
                if (Constant.TIMEINDAYSLIST.size() > 0) {
                    if (Constant.TIMEINDAYSLIST.size() != Constant.TIME_IN_DAYS || Constant.TIMEINDAYSLIST.size() != 1) {
                        if (Constant.TIMEINDAYSLIST.size() == 1 && Constant.TIME_IN_DAYS_DONE == 0) {

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(0);
                            timeInDay.setR_id(timeInDay.getR_id());
                            timeInDay.setMed_Name(timeInDay.getMed_Name());
                            timeInDay.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay.setMed_type(timeInDay.getMed_type());
                            timeInDay.setWeekDays(timeInDay.getWeekDays());
                            timeInDay.setTime_In_day("Evening");

                            if (timeInDay.getR_id().equals("0")) {
                                Constant.TIMEINDAYSLIST.set(0, timeInDay);
                            }
                            Constant.TIME_IN_DAYS_DONE++;
                        } else {

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                            TimeInDay timeInDay1 = new TimeInDay();
                            timeInDay1.setR_id(timeInDay.getR_id());
                            timeInDay1.setMed_Name(timeInDay.getMed_Name());
                            timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay1.setMed_type(timeInDay.getMed_type());
                            timeInDay1.setWeekDays(timeInDay.getWeekDays());
                            timeInDay1.setTime_In_day("Evening");

                            Constant.TIMEINDAYSLIST.add(timeInDay1);


                        }
                    }
                }
            } else {
                setTimeInDaysYes("Evening");
            }

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.GONE) {
                rl_forth.setVisibility(View.VISIBLE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }

        } else if (id == R.id.tv_night) {

            if (Constant.IS_YES) {
                if (Constant.TIMEINDAYSLIST.size() > 0) {
                    if (Constant.TIMEINDAYSLIST.size() != Constant.TIME_IN_DAYS || Constant.TIMEINDAYSLIST.size() != 1) {
                        if (Constant.TIMEINDAYSLIST.size() == 1 && Constant.TIME_IN_DAYS_DONE == 0) {
                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(0);
                            timeInDay.setR_id(timeInDay.getR_id());
                            timeInDay.setMed_Name(timeInDay.getMed_Name());
                            timeInDay.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay.setMed_type(timeInDay.getMed_type());
                            timeInDay.setWeekDays(timeInDay.getWeekDays());
                            timeInDay.setTime_In_day("Night");

                            if (timeInDay.getR_id().equals("0")) {
                                Constant.TIMEINDAYSLIST.set(0, timeInDay);
                            }
                            Constant.TIME_IN_DAYS_DONE++;
                        } else {

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                            TimeInDay timeInDay1 = new TimeInDay();
                            timeInDay1.setR_id(timeInDay.getR_id());
                            timeInDay1.setMed_Name(timeInDay.getMed_Name());
                            timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay1.setMed_type(timeInDay.getMed_type());
                            timeInDay1.setTime_In_day("Night");

                            Constant.TIMEINDAYSLIST.add(timeInDay1);

                        }
                    }
                }

            } else {
                setTimeInDaysYes("Night");
            }

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.GONE) {
                rl_forth.setVisibility(View.VISIBLE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }

        } else if (id == R.id.img_next_select_day_time) {
            if (Constant.IS_YES) {
                Log.e("LLLL_D11: ", Constant.TIMEINDAYSLIST.size() + "    " + (((Constant.TIME_IN_DAYS - 2) * 30)));
                if (Constant.TIME_IN_DAYS != 1 && Constant.TIMEINDAYSLIST.size() > (((Constant.TIME_IN_DAYS - 2) * 30))) {
                    img_next_select_day_time.setVisibility(View.GONE);
                    img_save.setVisibility(View.VISIBLE);
                } else if (Constant.TIME_IN_DAYS == 1) {
                    img_next_select_day_time.setVisibility(View.GONE);
                    img_save.setVisibility(View.VISIBLE);
                } else {
                    img_next_select_day_time.setVisibility(View.VISIBLE);
                    img_save.setVisibility(View.GONE);
                }
            } else {
                img_next_select_day_time.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
            }
            if (Constant.IS_YES) {

                if (!et_hours.getText().toString().trim().equals("") && et_hours.getText().toString().trim() != null
                        && !et_min.getText().toString().trim().equals("") && et_min.getText().toString().trim() != null) {

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

                    current_date = Calendar.getInstance().getTime();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(current_date);
                    calendar2.add(Calendar.DATE, -1);

                    current_date = calendar2.getTime();

                    for (int i = 0; i < 30; i++) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(current_date);
                        calendar1.add(Calendar.DATE, 1);

                        current_date = calendar1.getTime();

                        Log.e("LLLL_Day_size: ", String.valueOf(Constant.TIMEINDAYSLIST.size()));
                        Log.e("LLLL_", "Start Date: " + df.format(Calendar.getInstance().getTime()) + "      End Date: " + df.format(calendar1.getTime()) + " Between Dates: ");

//                            for (int j = 0; i < Constant.TIMEINDAYSLIST.size(); j++) {
                        TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);

                        TimeInDay timeInDay1 = new TimeInDay();
                        timeInDay1.setR_id(timeInDay.getR_id());
                        timeInDay1.setMed_Name(timeInDay.getMed_Name());
                        timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                        timeInDay1.setMed_type(timeInDay.getMed_type());
                        timeInDay1.setWeekDays(timeInDay.getWeekDays());
                        timeInDay1.setTime_In_day(timeInDay.getTime_In_day());
                        timeInDay1.setPills("1");
                        if (et_hours.getText().toString().trim().length() == 1)
                            timeInDay1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                        else
                            timeInDay1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                        timeInDay1.setDate(df.format(current_date));
                        if (i == 0) {
                            Constant.TIMEINDAYSLIST.set(0, timeInDay1);
                        } else {
                            Constant.TIMEINDAYSLIST.add(timeInDay1);
                        }

//                            }

                    }


                } else {
                    Toast.makeText(this, "Please Enter Time...", Toast.LENGTH_SHORT).show();
                }


                if (rl_first.getVisibility() == View.VISIBLE) {
                    rl_first.setVisibility(View.GONE);
                }
                if (rl_second.getVisibility() == View.VISIBLE) {
                    rl_second.setVisibility(View.GONE);
                }
                if (rl_second_no.getVisibility() == View.VISIBLE) {
                    rl_second_no.setVisibility(View.GONE);
                }
                if (rl_days_list.getVisibility() == View.VISIBLE) {
                    rl_days_list.setVisibility(View.GONE);
                }
                if (rl_third.getVisibility() == View.GONE) {
                    rl_third.setVisibility(View.VISIBLE);
                }
                if (rl_forth.getVisibility() == View.VISIBLE) {
                    rl_forth.setVisibility(View.GONE);
                }
                if (rl_fifth.getVisibility() == View.VISIBLE) {
                    rl_fifth.setVisibility(View.GONE);
                }
                if (rl_six.getVisibility() == View.VISIBLE) {
                    rl_six.setVisibility(View.GONE);
                }

                et_hours.setText("");
                et_min.setText("");

            }
        } else if (id == R.id.img_save) {

            if (rl_loader.getVisibility() == View.GONE)
                rl_loader.setVisibility(View.VISIBLE);

            rl_forth.setVisibility(View.GONE);

            ArrayList<TimeInWeek> timeInWeeks = new ArrayList<>();
            timeInWeeks.addAll(Constant.TIMEINWEEKLIST);
            Constant.TIMEINWEEKLIST.clear();

            current_date = Calendar.getInstance().getTime();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(current_date);
            calendar2.add(Calendar.DATE, -1);

            current_date = calendar2.getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy hh:mm", Locale.getDefault());

            if (Constant.IS_YES) {

                if (Constant.IS_CUSTOM && Constant.IS_EVERY_DAY_YES) {

                    if (!et_hours.getText().toString().trim().equals("") && et_hours.getText().toString().trim() != null
                            && !et_min.getText().toString().trim().equals("") && et_min.getText().toString().trim() != null) {

                        for (int i = 0; i <= 30; i++) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.setTime(current_date);
                            calendar1.add(Calendar.DATE, 1);

                            current_date = calendar1.getTime();

                            current_date1 = current_date;
                            Calendar calendar4 = Calendar.getInstance();
                            calendar4.setTime(current_date1);
                            calendar4.add(Calendar.DATE, -1);
                            calendar4.set(Calendar.HOUR, Integer.parseInt(et_hours.getText().toString().trim()));
                            calendar4.set(Calendar.MINUTE, Integer.parseInt(et_min.getText().toString().trim()));

                            current_date1 = calendar4.getTime();

                            Log.e("LLLLL_Time_Before: ", df.format(current_date1.getTime()) + "     " + et_hours.getText().toString().trim() + " : " + et_min.getText().toString().trim());

//                            Log.e("LLLL_", "Start Date: " + df.format(Calendar.getInstance().getTime()) + "      End Date: " + df.format(calendar1.getTime()) + " Between Dates: ");

                            for (int j = 0; j < (24 / Constant.EVERY_NUM_HOURS); j++) {

                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(current_date1);
                                calendar3.add(Calendar.HOUR_OF_DAY, Constant.EVERY_NUM_HOURS);

                                current_date1 = calendar3.getTime();

                                SimpleDateFormat df1 = new SimpleDateFormat("hh:mm", Locale.getDefault());
                                Log.e("LLLLL_Time_After: ", j + "    " + df2.format(current_date.getTime()) + "       " + df2.format(current_date1.getTime()) + "     " + calendar3.get(Calendar.AM_PM));

                                TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                                TimeInDay timeInDay1 = new TimeInDay();
                                timeInDay1.setR_id(timeInDay.getR_id());
                                timeInDay1.setMed_Name(timeInDay.getMed_Name());
                                timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                                timeInDay1.setMed_type(timeInDay.getMed_type());
                                timeInDay1.setWeekDays(timeInDay.getWeekDays());
                                timeInDay1.setTime_In_day(timeInDay.getTime_In_day());
                                timeInDay1.setPills("1");
                                if (calendar3.get(Calendar.AM_PM) == Calendar.AM)
                                    timeInDay1.setTime(df1.format(current_date1.getTime()) + " " + "AM");
                                else
                                    timeInDay1.setTime(df1.format(current_date1.getTime()) + " " + "PM");
                                timeInDay1.setDate(df.format(current_date1));
                                if (i == 0)
                                    Constant.TIMEINDAYSLIST.set(Constant.TIMEINDAYSLIST.size() - 1, timeInDay1);
                                else
                                    Constant.TIMEINDAYSLIST.add(timeInDay1);
                            }
                        }

                    } else {
                        Toast.makeText(this, "Please Enter Time...", Toast.LENGTH_SHORT).show();
                    }

                    // Temporary DB entry close
                    for (int i = 0; i < Constant.TIMEINDAYSLIST.size(); i++) {
                        TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(i);
                        dbHelper.insertNoPillsDetails(timeInDay.getR_id(),
                                timeInDay.getMed_Name(),
                                timeInDay.getMed_Strength(),
                                timeInDay.getMed_type(),
                                timeInDay.getTime_In_day(),
                                timeInDay.getWeekDays(),
                                timeInDay.getPills(),
                                timeInDay.getTime(),
                                timeInDay.getDate(),
                                "", "");
                    }

                } else {

                    if (!et_hours.getText().toString().trim().equals("") && et_hours.getText().toString().trim() != null
                            && !et_min.getText().toString().trim().equals("") && et_min.getText().toString().trim() != null) {

                        for (int i = 0; i <= 30; i++) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.setTime(current_date);
                            calendar1.add(Calendar.DATE, 1);

                            current_date = calendar1.getTime();

                            Log.e("LLLL_", "Start Date: " + df.format(Calendar.getInstance().getTime()) + "      End Date: " + df.format(calendar1.getTime()) + " Between Dates: ");

                            TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                            TimeInDay timeInDay1 = new TimeInDay();
                            timeInDay1.setR_id(timeInDay.getR_id());
                            timeInDay1.setMed_Name(timeInDay.getMed_Name());
                            timeInDay1.setMed_Strength(timeInDay.getMed_Strength());
                            timeInDay1.setMed_type(timeInDay.getMed_type());
                            timeInDay1.setWeekDays(timeInDay.getWeekDays());
                            timeInDay1.setTime_In_day(timeInDay.getTime_In_day());
                            timeInDay1.setPills("1");
                            if (et_hours.getText().toString().trim().length() == 1)
                                timeInDay1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                            else
                                timeInDay1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));

                            timeInDay1.setDate(df.format(current_date));
                            if (i == 0)
                                Constant.TIMEINDAYSLIST.set(Constant.TIMEINDAYSLIST.size() - 1, timeInDay1);
                            else
                                Constant.TIMEINDAYSLIST.add(timeInDay1);

                        }

                    } else {
                        Toast.makeText(this, "Please Enter Time...", Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < Constant.TIMEINDAYSLIST.size(); i++) {
                        TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(i);
                        dbHelper.insertNoPillsDetails(timeInDay.getR_id(),
                                timeInDay.getMed_Name(),
                                timeInDay.getMed_Strength(),
                                timeInDay.getMed_type(),
                                timeInDay.getTime_In_day(),
                                timeInDay.getWeekDays(),
                                timeInDay.getPills(),
                                timeInDay.getTime(),
                                timeInDay.getDate(),
                                "", "");
                    }

                }


                Constant.clearData();
                Toast.makeText(AddEvrydayOrNot.this, "Saved Successfully....", Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddEvrydayOrNot.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
            } else {

                for (int i = 0; i < timeInWeeks.size(); i++) {
                    current_date = calendar2.getTime();
                    if (!et_hours.getText().toString().trim().equals("") && et_hours.getText().toString().trim() != null
                            && !et_min.getText().toString().trim().equals("") && et_min.getText().toString().trim() != null) {

                        if (Constant.IS_OTHER) {

                            String dtStart = startYear + "-" + startMonth + "-" + startDay;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                            try {
                                Date startDate = format.parse(dtStart);
                                Calendar calendar6 = Calendar.getInstance();
                                calendar6.setTime(startDate);
                                calendar6.add(Calendar.DATE, -Constant.WEEK_DAYS);

                                current_date = calendar6.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            for (int j = 0; j < 30; j++) {
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(current_date);
                                calendar1.add(Calendar.DATE, Constant.WEEK_DAYS);

                                current_date = calendar1.getTime();

                                Log.e("LLLL_", "Start Date: " + df.format(Calendar.getInstance().getTime()) + "      End Date: " + df.format(calendar1.getTime()) + " Between Dates: ");

                                TimeInWeek timeInWeek = timeInWeeks.get(i);
                                int result = calendar1.get(Calendar.DAY_OF_WEEK);
                                switch (result) {
                                    case Calendar.MONDAY:

                                        TimeInWeek timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Monday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));

                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        break;
                                    case Calendar.TUESDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Tuesday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                    case Calendar.WEDNESDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Wednesday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                    case Calendar.THURSDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Thursday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                    case Calendar.FRIDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Friday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                    case Calendar.SATURDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Saturday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                    case Calendar.SUNDAY:

                                        timeInWeek1 = new TimeInWeek();
                                        timeInWeek1.setY_id(timeInWeek.getY_id());
                                        timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                        timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                        timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                        timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                        timeInWeek1.setDay("Sunday");
                                        timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                        timeInWeek1.setPills("1");
                                        if (et_hours.getText().toString().trim().length() == 1)
                                            timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        else
                                            timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                        timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                        Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }


                                        break;
                                }

                            }
                        } else {

                            for (int j = 0; j < 30; j++) {

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(current_date);
                                calendar1.add(Calendar.DATE, 1);

                                current_date = calendar1.getTime();

                                Log.e("LLLL_Day_size: ", j + "   " + timeInWeeks.get(i).getDay());
                                Log.e("LLLL_", "Start Date: " + df.format(Calendar.getInstance().getTime()) + "      End Date: " + df.format(calendar1.getTime()) + " Between Dates: ");

                                TimeInWeek timeInWeek = timeInWeeks.get(i);
                                int result = calendar1.get(Calendar.DAY_OF_WEEK);
                                switch (result) {
                                    case Calendar.MONDAY:
                                        if (timeInWeek.getDay().equals("Monday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Monday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }
                                        }
                                        break;
                                    case Calendar.TUESDAY:
                                        if (timeInWeek.getDay().equals("Tuesday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Tuesday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                    case Calendar.WEDNESDAY:
                                        if (timeInWeek.getDay().equals("Wednesday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Wednesday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                    case Calendar.THURSDAY:
                                        if (timeInWeek.getDay().equals("Thursday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Thursday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                    case Calendar.FRIDAY:
                                        if (timeInWeek.getDay().equals("Friday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Friday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                    case Calendar.SATURDAY:
                                        if (timeInWeek.getDay().equals("Saturday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Saturday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                    case Calendar.SUNDAY:
                                        if (timeInWeek.getDay().equals("Sunday")) {
                                            TimeInWeek timeInWeek1 = new TimeInWeek();
                                            timeInWeek1.setY_id(timeInWeek.getY_id());
                                            timeInWeek1.setMed_name(timeInWeek.getMed_name());
                                            timeInWeek1.setMed_type(timeInWeek.getMed_type());
                                            timeInWeek1.setMed_strength(timeInWeek.getMed_strength());
                                            timeInWeek1.setWeek_days_in(timeInWeek.getWeek_days_in());
                                            timeInWeek1.setDay("Sunday");
                                            timeInWeek1.setTime_in_days(timeInWeek.getTime_in_days());
                                            timeInWeek1.setPills("1");
                                            if (et_hours.getText().toString().trim().length() == 1)
                                                timeInWeek1.setTime("0" + et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            else
                                                timeInWeek1.setTime(et_hours.getText().toString().trim() + ":" + et_min.getText().toString().trim() + " " + amPmList.get(segmented.getPosition()));
                                            timeInWeek1.setDate(df.format(current_date));
//                                            if (j == 0) {
//                                                Constant.TIMEINWEEKLIST.set(j, timeInWeek1);
//                                            } else {
                                            Constant.TIMEINWEEKLIST.add(timeInWeek1);
//                                            }

                                        }
                                        break;
                                }

                            }
                        }

                    } else {
                        Toast.makeText(this, "Please Enter Time...", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

                if (rl_first.getVisibility() == View.VISIBLE) {
                    rl_first.setVisibility(View.GONE);
                }
                if (rl_second.getVisibility() == View.VISIBLE) {
                    rl_second.setVisibility(View.GONE);
                }
                if (rl_second_no.getVisibility() == View.VISIBLE) {
                    rl_second_no.setVisibility(View.GONE);
                }
                if (rl_days_list.getVisibility() == View.VISIBLE) {
                    rl_days_list.setVisibility(View.GONE);
                }
                if (rl_third.getVisibility() == View.GONE) {
                    rl_third.setVisibility(View.VISIBLE);
                }
                if (rl_forth.getVisibility() == View.VISIBLE) {
                    rl_forth.setVisibility(View.GONE);
                }
                if (rl_fifth.getVisibility() == View.VISIBLE) {
                    rl_fifth.setVisibility(View.GONE);
                }
                if (rl_six.getVisibility() == View.VISIBLE) {
                    rl_six.setVisibility(View.GONE);
                }

                et_hours.setText("");
                et_min.setText("");

                for (int i = 0; i < Constant.TIMEINWEEKLIST.size(); i++) {
                    TimeInWeek timeInDay = Constant.TIMEINWEEKLIST.get(i);
                    dbHelper.insertNoPillsDetailsYes(timeInDay.getY_id(),
                            timeInDay.getMed_name(),
                            timeInDay.getMed_strength(),
                            timeInDay.getMed_type(),
                            timeInDay.getDay(),
                            timeInDay.getTime_in_days(),
                            timeInDay.getWeek_days_in(),
                            timeInDay.getPills(),
                            timeInDay.getTime(),
                            timeInDay.getDate(),
                            "", "");
                }

                Constant.clearData();
                Toast.makeText(AddEvrydayOrNot.this, "Saved Successfully....", Toast.LENGTH_LONG).show();
                if (rl_loader.getVisibility() == View.GONE)
                    rl_loader.setVisibility(View.VISIBLE);

                rl_forth.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddEvrydayOrNot.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);

            }

        } else if (id == R.id.img_edit_pills) {
            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.VISIBLE) {
                rl_third.setVisibility(View.GONE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.GONE) {
                rl_fifth.setVisibility(View.VISIBLE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_sunday) {
            boolean is_inList = false;
            int position = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Sunday")) {
                            is_inList = true;
                            position = i;
                            break;
                        }
                    }
                    if (!is_inList) {
                        Constant.DAYSLIST.add("Sunday");
                        tv_sunday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_sunday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position);
                        Constant.DAYSLIST.remove(position);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Sunday")) {
                            is_inList = true;
                            position = i;
                            break;
                        }
                    }
                    if (!is_inList) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Sunday");
                        tv_sunday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_sunday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }

            } else {
                Constant.DAYSLIST.add("Sunday");
                tv_sunday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_sunday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_monday) {
            boolean is_inList1 = false;
            int position1 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Monday")) {
                            is_inList1 = true;
                            position1 = i;
                            break;
                        }
                    }
                    if (!is_inList1) {
                        Constant.DAYSLIST.add("Monday");
                        tv_monday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_monday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position1);
                        Constant.DAYSLIST.remove(position1);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Monday")) {
                            is_inList1 = true;
                            position1 = i;
                            break;
                        }
                    }
                    if (!is_inList1) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Monday");
                        tv_monday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_monday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Monday");
                tv_monday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_monday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_tuesday) {
            boolean is_inList2 = false;
            int position2 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Tuesday")) {
                            is_inList2 = true;
                            position2 = i;
                            break;
                        }
                    }
                    if (!is_inList2) {
                        tv_tuesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_tuesday.setPadding(50, 0, 0, 0);
                        Constant.DAYSLIST.add("Tuesday");
                    } else {
                        changeTextBG(position2);
                        Constant.DAYSLIST.remove(position2);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Tuesday")) {
                            is_inList2 = true;
                            position2 = i;
                            break;
                        }
                    }
                    if (!is_inList2) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Tuesday");
                        tv_tuesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_tuesday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position2);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Tuesday");
                tv_tuesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_tuesday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_wednesday) {
            boolean is_inList3 = false;
            int position3 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Wednesday")) {
                            is_inList3 = true;
                            position3 = i;
                            break;
                        }
                    }
                    if (!is_inList3) {
                        Constant.DAYSLIST.add("Wednesday");
                        tv_wednesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_wednesday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position3);
                        Constant.DAYSLIST.remove(position3);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Wednesday")) {
                            is_inList3 = true;
                            position3 = i;
                            break;
                        }
                    }
                    if (!is_inList3) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Wednesday");
                        tv_wednesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_wednesday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position3);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Wednesday");
                tv_wednesday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_wednesday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_thursday) {
            boolean is_inList4 = false;
            int position4 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Thursday")) {
                            is_inList4 = true;
                            position4 = i;
                            break;
                        }
                    }
                    if (!is_inList4) {
                        Constant.DAYSLIST.add("Thursday");
                        tv_thursday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_thursday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position4);
                        Constant.DAYSLIST.remove(position4);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Thursday")) {
                            is_inList4 = true;
                            position4 = i;
                            break;
                        }
                    }
                    if (!is_inList4) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Thursday");
                        tv_thursday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_thursday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position4);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Thursday");
                tv_thursday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_thursday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_friday) {
            boolean is_inList5 = false;
            int position5 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Friday")) {
                            is_inList5 = true;
                            position5 = i;
                            break;
                        }
                    }
                    if (!is_inList5) {
                        Constant.DAYSLIST.add("Friday");
                        tv_friday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_friday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position5);
                        Constant.DAYSLIST.remove(position5);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Friday")) {
                            is_inList5 = true;
                            position5 = i;
                            break;
                        }
                    }
                    if (!is_inList5) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Friday");
                        tv_friday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_friday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position5);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Friday");
                tv_friday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_friday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.tv_saturday) {
            boolean is_inList6 = false;
            int position6 = 0;
            if (!Constant.DAYSLIST.isEmpty()) {
                if (Constant.DAYSLIST.size() != Constant.WEEK_DAYS) {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Saturday")) {
                            is_inList6 = true;
                            position6 = i;
                            break;
                        }
                    }
                    if (!is_inList6) {
                        Constant.DAYSLIST.add("Saturday");
                        tv_saturday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_saturday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position6);
                        Constant.DAYSLIST.remove(position6);
                    }
                } else {
                    for (int i = 0; i < Constant.DAYSLIST.size(); i++) {
                        if (Constant.DAYSLIST.get(i).equals("Saturday")) {
                            is_inList6 = true;
                            position6 = i;
                            break;
                        }
                    }
                    if (!is_inList6) {
                        changeTextBG(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                        Constant.DAYSLIST.add("Saturday");
                        tv_saturday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                        tv_saturday.setPadding(50, 0, 0, 0);
                    } else {
                        changeTextBG(position6);
                        Constant.DAYSLIST.remove(Constant.DAYSLIST.size() - 1);
                    }
                }
            } else {
                Constant.DAYSLIST.add("Saturday");
                tv_saturday.setBackground(getResources().getDrawable(R.drawable.selcted_bg));
                tv_saturday.setPadding(50, 0, 0, 0);
            }
            if (Constant.DAYSLIST.size() == Constant.WEEK_DAYS) {
                img_next_days.setVisibility(View.GONE);
                img_next_select_days.setVisibility(View.VISIBLE);
            } else {
                img_next_days.setVisibility(View.VISIBLE);
                img_next_select_days.setVisibility(View.GONE);
            }
        } else if (id == R.id.img_next_select_days) {

            setDays();

            if (rl_first.getVisibility() == View.VISIBLE) {
                rl_first.setVisibility(View.GONE);
            }
            if (rl_second.getVisibility() == View.VISIBLE) {
                rl_second.setVisibility(View.GONE);
            }
            if (rl_second_no.getVisibility() == View.VISIBLE) {
                rl_second_no.setVisibility(View.GONE);
            }
            if (rl_days_list.getVisibility() == View.VISIBLE) {
                rl_days_list.setVisibility(View.GONE);
            }
            if (rl_third.getVisibility() == View.GONE) {
                rl_third.setVisibility(View.VISIBLE);
            }
            if (rl_forth.getVisibility() == View.VISIBLE) {
                rl_forth.setVisibility(View.GONE);
            }
            if (rl_fifth.getVisibility() == View.VISIBLE) {
                rl_fifth.setVisibility(View.GONE);
            }
            if (rl_six.getVisibility() == View.VISIBLE) {
                rl_six.setVisibility(View.GONE);
            }
        }
    }

    private void changeTextBG(int position) {
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_monday.getText().toString())) {
            tv_monday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_monday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_tuesday.getText().toString())) {
            tv_tuesday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_tuesday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_wednesday.getText().toString())) {
            tv_wednesday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_wednesday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_thursday.getText().toString())) {
            tv_thursday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_thursday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_friday.getText().toString())) {
            tv_friday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_friday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_saturday.getText().toString())) {
            tv_saturday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_saturday.setPadding(50, 0, 0, 0);
        }
        if (Constant.DAYSLIST.get(position).equalsIgnoreCase(tv_sunday.getText().toString())) {
            tv_sunday.setBackground(getResources().getDrawable(R.drawable.not_selct_bg));
            tv_sunday.setPadding(50, 0, 0, 0);
        }

    }

    private void setAdapter(int size) {

        list.clear();
        rv_picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        if (Constant.IS_OTHER) {
            if (size != 7) {
                for (int i = 0; i < size; i++) {
                    list.add(i);
                }
            } else {
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.add(6);
                list.add(8);
                list.add(12);
            }
        } else {
            for (int i = 0; i <= 28; i++) {
                list.add(i + 2);
            }
        }

        numberAdapter = new NumberAdapter(list, AddEvrydayOrNot.this);
        rv_picker.setAdapter(numberAdapter);

        rv_picker.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                currentItemHolder.itemView.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                currentItemHolder.itemView.setBackground(getResources().getDrawable(R.drawable.select_without_bg));
            }

            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.DAYSLIST = new ArrayList<>();
        Constant.WEEK_DAYS = 1;
        Constant.IS_YES = true;
    }
}