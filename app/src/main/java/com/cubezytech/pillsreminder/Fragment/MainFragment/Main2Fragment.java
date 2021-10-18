package com.cubezytech.pillsreminder.Fragment.MainFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubezytech.pillsreminder.Activity.AddMedicineNameActivity;
import com.cubezytech.pillsreminder.Activity.SettingsActivity;
import com.cubezytech.pillsreminder.Adapter.HistoryAdapter;
import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Model.Common;
import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.R;
import com.daimajia.swipe.util.Attributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Main2Fragment extends Fragment implements View.OnClickListener {


    private final ArrayList<Common> commonArrayList = new ArrayList<>();
    HorizontalCalendar horizontalCalendar;
    HorizontalCalendarView horizontalCalendar1;
    TextView tv_current_month;
    RecyclerView rv_history;
    ImageView img_last_month, img_next_month, img_add_new_med, img_setting;
    ArrayList<TimeInDay> dayList = new ArrayList<>();
    ArrayList<TimeInWeek> weekDayList = new ArrayList<>();
    DBHelper dbHelper;
    private RecyclerView.Adapter mAdapter;
    private String selectedDate;

    private Date current_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_data, container, false);

        ButterKnife.bind(this, v);

        dbHelper = new DBHelper(getContext());

        horizontalCalendar1 = v.findViewById(R.id.calanderView);
        tv_current_month = v.findViewById(R.id.tv_current_month);
        img_last_month = v.findViewById(R.id.img_last_month);
        img_next_month = v.findViewById(R.id.img_next_month);
        img_add_new_med = v.findViewById(R.id.img_add_new_med);
        rv_history = v.findViewById(R.id.rv_history);
        img_setting = v.findViewById(R.id.img_setting);


        img_add_new_med.setOnClickListener(this);
        img_setting.setOnClickListener(this);


        setCalender(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add_new_med:
                Intent intent = new Intent(getContext(), AddMedicineNameActivity.class);
                startActivity(intent);
                break;
            case R.id.img_setting:
                Intent settingIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingIntent);
                break;
        }
    }

    private void setAdapter() {

        dayList.clear();
        weekDayList.clear();

        if (!dbHelper.getPillsNoRecords().isEmpty()) {
            dayList = dbHelper.getPillsNoRecords();
        }

        if (!dbHelper.getPillsNoRecordsYes().isEmpty()) {
            weekDayList = dbHelper.getPillsNoRecordsYes();
        }

        commonArrayList.clear();
        if (!weekDayList.isEmpty()) {
            for (int i = 0; i < weekDayList.size(); i++) {

                TimeInWeek timeInDay = weekDayList.get(i);
                ArrayList<String> medType = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();
                ArrayList<String> from = new ArrayList<>();
                if (selectedDate.equals(timeInDay.getDate())) {

                    Common common = new Common();
                    medType.add(timeInDay.getMed_type());
                    ids.add(timeInDay.getY_id());
                    from.add("TimeInWeek");
                    common.setIds(ids);
                    common.setFrom(from);
                    common.setMedType(medType);
                    common.setPills(timeInDay.getPills());
                    common.setDate(timeInDay.getDate());
                    common.setTime(timeInDay.getTime());
                    common.setTaken(timeInDay.getTaken());
                    common.setTaken_time(timeInDay.getTaken_time());

                    commonArrayList.add(common);

                }
            }
        }

        if (!commonArrayList.isEmpty()) {
            for (int i = 0; i < commonArrayList.size(); i++) {
                if (commonArrayList.size() > 0) {

                    for (int k = i + 1; k < commonArrayList.size(); k++) {
                        Common timeInDay = commonArrayList.get(i);
                        Common common1 = commonArrayList.get(k);

                        if (timeInDay.getTime().equals(common1.getTime())) {
                            Common common = new Common();
                            ArrayList<String> medType = new ArrayList<>();
                            ArrayList<String> ids = new ArrayList<>();
                            ArrayList<String> from = new ArrayList<>();

                            medType.addAll(common1.getMedType());
                            medType.addAll(timeInDay.getMedType());

                            ids.addAll(common1.getIds());
                            ids.addAll(timeInDay.getIds());

                            from.addAll(common1.getFrom());
                            from.addAll(timeInDay.getFrom());

                            common.setIds(ids);
                            common.setFrom(from);
                            common.setFrom(common1.getFrom());
                            common.setMedType(medType);
                            common.setPills(String.valueOf(Integer.parseInt(timeInDay.getPills()) + Integer.parseInt(common1.getPills())));
                            common.setDate(common1.getDate());
                            common.setTime(common1.getTime());
                            common.setTaken(common1.getTaken());
                            common.setTaken_time(common1.getTaken_time());

                            commonArrayList.set(i, common);
                            commonArrayList.remove(common1);
                        }
                    }
                }
            }
        }

        if (!dayList.isEmpty()) {
            for (int i = 0; i < dayList.size(); i++) {

                TimeInDay timeInDay = dayList.get(i);
                ArrayList<String> medType = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();
                ArrayList<String> from = new ArrayList<>();
                if (selectedDate.equals(timeInDay.getDate())) {

                    Common common = new Common();
                    medType.add(timeInDay.getMed_type());
                    ids.add(timeInDay.getR_id());
                    from.add("TimeInDay");
                    common.setIds(ids);
                    common.setFrom(from);
                    common.setMedType(medType);
                    common.setPills(timeInDay.getPills());
                    common.setDate(timeInDay.getDate());
                    common.setTime(timeInDay.getTime());
                    common.setTaken(timeInDay.getTaken());
                    common.setTaken_time(timeInDay.getTaken_time());

                    commonArrayList.add(common);

                }
            }
        }

        if (!commonArrayList.isEmpty()) {
            for (int i = 0; i < commonArrayList.size(); i++) {
                if (commonArrayList.size() > 0) {

                    for (int k = i + 1; k < commonArrayList.size(); k++) {
                        Common timeInDay = commonArrayList.get(i);
                        Common common1 = commonArrayList.get(k);

                        if (timeInDay.getTime().equals(common1.getTime())) {
                            Common common = new Common();
                            ArrayList<String> medType = new ArrayList<>();
                            ArrayList<String> ids = new ArrayList<>();
                            ArrayList<String> from = new ArrayList<>();

                            medType.addAll(common1.getMedType());
                            medType.addAll(timeInDay.getMedType());

                            ids.addAll(common1.getIds());
                            ids.addAll(timeInDay.getIds());

                            from.addAll(common1.getFrom());
                            from.addAll(timeInDay.getFrom());

                            common.setIds(ids);
                            common.setFrom(from);

                            common.setFrom(common1.getFrom());
                            common.setMedType(medType);
                            common.setPills(String.valueOf(Integer.parseInt(timeInDay.getPills()) + Integer.parseInt(common1.getPills())));
                            common.setDate(common1.getDate());
                            common.setTime(common1.getTime());
                            common.setTaken(common1.getTaken());
                            common.setTaken_time(common1.getTaken_time());

                            commonArrayList.set(i, common);
                            commonArrayList.remove(common1);
                            k--;
                        }
                    }
                }
            }
        }

        rv_history.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mAdapter = new HistoryAdapter(getActivity(), commonArrayList);
        ((HistoryAdapter) mAdapter).setMode(Attributes.Mode.Single);
        rv_history.setAdapter(mAdapter);

    }

    private void setCalender(View v) {

        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -1);
        startDate.add(Calendar.MONTH, -1);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
//        endDate.add(Calendar.YEAR, 1);
        endDate.add(Calendar.MONTH, 1);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
        defaultSelectedDate.set(Calendar.HOUR, 1);
        defaultSelectedDate.set(Calendar.MINUTE, 0);
        defaultSelectedDate.set(Calendar.AM_PM, Calendar.AM);

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);
        tv_current_month.setText(month_date.format(defaultSelectedDate.getTime()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        selectedDate = dateFormat.format(defaultSelectedDate.getTime());

        horizontalCalendar = new HorizontalCalendar.Builder(v, R.id.calanderView)
                .range(startDate, endDate)
                .datesNumberOnScreen(8)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.BLACK, Color.WHITE)
                .colorTextMiddle(Color.BLACK, Color.WHITE)
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {

                    final Random rnd = new Random();

                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        int count = rnd.nextInt(6);

                        for (int i = 0; i <= count; i++) {
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }

                        return events;
                    }
                })
                .build();

        Log.i("Default Date", DateFormat.format("dd-MMM-yyyy", defaultSelectedDate).toString());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("dd-MMM-yyyy", date).toString();

                SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date.getTime());
                calendar1.add(Calendar.DATE, -1);
                String yesterdayAsString = dateFormat.format(calendar1.getTime());

                selectedDate = yesterdayAsString;

                if (!month_date.format(calendar1.getTime()).equals(tv_current_month.getText().toString().trim()))
                    tv_current_month.setText(month_date.format(calendar1.getTime()));

                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });

            }

        });

    }

}
