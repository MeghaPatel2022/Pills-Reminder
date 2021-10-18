package com.cubezytech.pillsreminder.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubezytech.pillsreminder.Activity.EditMedActivity;
import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Model.Common;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.R;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HistoryAdapter extends RecyclerSwipeAdapter<HistoryAdapter.MyClassView> {

    Activity mContext;
    ArrayList<Common> commonArrayList;
    DBHelper dbHelper;
    Dialog dialog;
    Dialog dialog1;
    private MedTypeAdapter medTypeAdapter;


    public HistoryAdapter(Activity mContext, ArrayList<Common> commonArrayList) {
        this.mContext = mContext;
        this.commonArrayList = commonArrayList;
        dbHelper = new DBHelper(mContext);

    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView viewHolder, int position) {

        Common common = commonArrayList.get(position);
        viewHolder.tv_time.setText(common.getTime());
        if (common.getPills().equals("1"))
            viewHolder.tv_quantity.setText(common.getPills() + " Pill");
        else
            viewHolder.tv_quantity.setText(common.getPills() + " Pills");

        if (common.getTaken().equals(Constant.MISSED)) {
            viewHolder.tv_missed.setVisibility(View.VISIBLE);
            viewHolder.tv_taken.setVisibility(View.GONE);
        } else if (common.getTaken().equals(Constant.TAKEN)) {
            viewHolder.tv_missed.setVisibility(View.GONE);
            viewHolder.tv_taken.setVisibility(View.VISIBLE);
            viewHolder.tv_taken.setText("Taken at " + common.getTaken_time());
        } else {
            viewHolder.tv_missed.setVisibility(View.GONE);
            viewHolder.tv_taken.setVisibility(View.GONE);
        }

        ArrayList<String> medTypeList = new ArrayList<>();
        medTypeList.addAll(common.getMedType());

        if (medTypeList.size() > 2) {
            for (int i = 0; i < medTypeList.size(); i++) {
                for (int j = i + 1; j < medTypeList.size(); j++) {
                    if (medTypeList.get(i).equals(medTypeList.get(j))) {
                        medTypeList.remove(medTypeList.get(j));
                        j--;
                    }
                }
            }
        }

        if (medTypeList.size() >= 3 && medTypeList.size() < 7) {
            viewHolder.rv_med_type.setLayoutManager(new GridLayoutManager(mContext, 3));
            medTypeAdapter = new MedTypeAdapter(medTypeList, mContext);
            viewHolder.rv_med_type.setAdapter(medTypeAdapter);
        } else if (medTypeList.size() >= 7) {
            viewHolder.rv_med_type.setLayoutManager(new GridLayoutManager(mContext, 4));
            medTypeAdapter = new MedTypeAdapter(medTypeList, mContext);
            viewHolder.rv_med_type.setAdapter(medTypeAdapter);
        } else {
            viewHolder.rv_med_type.setLayoutManager(new GridLayoutManager(mContext, medTypeList.size()));
            medTypeAdapter = new MedTypeAdapter(medTypeList, mContext);
            viewHolder.rv_med_type.setAdapter(medTypeAdapter);
        }

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                viewHolder.img_more.setVisibility(View.GONE);
                viewHolder.rl_main.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
                viewHolder.img_more.setVisibility(View.VISIBLE);
                viewHolder.rl_main.setBackground(mContext.getResources().getDrawable(R.drawable.ic_blue_bg));
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.closeAllItems();

                for (int i = 0; i < common.getIds().size(); i++) {
                    for (int j = 0; j < common.getFrom().size(); j++) {
                        if (common.getFrom().get(j).equals("TimeInDay")) {
                            Intent intent = new Intent(v.getContext(), EditMedActivity.class);
                            intent.putExtra("from", "TimeInDay");
                            intent.putExtra("date", common.getDate());
                            intent.putExtra("time", common.getTime());
                            intent.putExtra("id", common.getIds().get(i));
                            v.getContext().startActivity(intent);
                        } else if (common.getFrom().get(j).equals("TimeInWeek")) {
                            Intent intent = new Intent(v.getContext(), EditMedActivity.class);
                            intent.putExtra("from", "TimeInWeek");
                            intent.putExtra("date", common.getDate());
                            intent.putExtra("time", common.getTime());
                            intent.putExtra("id", common.getIds().get(i));
                            v.getContext().startActivity(intent);
                        }
                    }
                }

            }
        });

        viewHolder.img_delete.setOnClickListener(v -> {

            for (int i = 0; i < common.getIds().size(); i++) {
                for (int j = 0; j < common.getFrom().size(); j++) {
                    if (common.getFrom().get(j).equals("TimeInDay"))
                        dbHelper.deletePillsNoRecords(common.getIds().get(i), common.getDate(), common.getTime());
                    else if (common.getFrom().get(j).equals("TimeInWeek"))
                        dbHelper.deletePillsRecordsYes(common.getIds().get(i), common.getDate(), common.getTime());
                }
            }

            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
            commonArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, commonArrayList.size());
            mItemManger.closeAllItems();
        });

        viewHolder.itemView.setOnClickListener(v -> {

            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_detail_med);
            dialog.getWindow().setLayout(v.getContext().getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            LinearLayout ll_missed, ll_taken;
            RecyclerView rv_med_type;
            ImageView img_close;
            TextView tv_schedule_time, tv_time, tv_quantity;

            tv_schedule_time = dialog.findViewById(R.id.tv_schedule_time);
            tv_time = dialog.findViewById(R.id.tv_time);
            tv_quantity = dialog.findViewById(R.id.tv_quantity);
            img_close = dialog.findViewById(R.id.img_close);
            ll_missed = dialog.findViewById(R.id.ll_missed);
            ll_taken = dialog.findViewById(R.id.ll_taken);
            rv_med_type = dialog.findViewById(R.id.rv_med_type);

            ArrayList<String> medTypeList1 = new ArrayList<>();
            medTypeList1.addAll(common.getMedType());

            tv_schedule_time.setText("Scheduled for " + common.getTime() + ", " + common.getDate());
            tv_time.setText(common.getTime());
            if (common.getPills().equals("1"))
                tv_quantity.setText(common.getPills() + " Pill");
            else
                tv_quantity.setText(common.getPills() + " Pills");

            if (medTypeList1.size() > 2) {
                for (int i = 0; i < medTypeList1.size(); i++) {
                    for (int j = i + 1; j < medTypeList1.size(); j++) {
                        if (medTypeList1.get(i).equals(medTypeList1.get(j))) {
                            medTypeList1.remove(medTypeList1.get(j));
                            j--;
                        }
                    }
                }
            }

            if (medTypeList1.size() >= 3 && medTypeList1.size() < 7) {
                rv_med_type.setLayoutManager(new GridLayoutManager(mContext, 3));
                medTypeAdapter = new MedTypeAdapter(medTypeList1, mContext);
                rv_med_type.setAdapter(medTypeAdapter);
            } else if (medTypeList1.size() >= 7) {
                rv_med_type.setLayoutManager(new GridLayoutManager(mContext, 4));
                medTypeAdapter = new MedTypeAdapter(medTypeList1, mContext);
                rv_med_type.setAdapter(medTypeAdapter);
            } else {
                rv_med_type.setLayoutManager(new GridLayoutManager(mContext, medTypeList1.size()));
                medTypeAdapter = new MedTypeAdapter(medTypeList1, mContext);
                rv_med_type.setAdapter(medTypeAdapter);
            }

            ll_missed.setOnClickListener(v14 -> {

                for (int i = 0; i < common.getIds().size(); i++) {
                    for (int j = 0; j < common.getFrom().size(); j++) {
                        if (common.getFrom().get(j).equals("TimeInDay")) {
                            dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.MISSED, common.getDate(), common.getTime(), "");
                        } else if (common.getFrom().get(j).equals("TimeInWeek")) {
                            dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.MISSED, common.getDate(), common.getTime(), "");
                        }
                    }
                }

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(position);
                        notifyItemRangeChanged(position, commonArrayList.size());
                    }
                });


                if (dialog.isShowing())
                    dialog.dismiss();
            });

            ll_taken.setOnClickListener(v15 -> {

//                        for (int i = 0; i < common.getIds().size(); i++) {
//                            for (int j = 0; j < common.getFrom().size(); j++) {
//                                if (common.getFrom().get(j).equals("TimeInDay")) {
//                                    dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime());
//                                } else if (common.getFrom().get(j).equals("TimeInWeek")) {
//                                    dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime());
//                                }
//                            }
//                        }
//
//                        notifyItemRangeChanged(position, commonArrayList.size());

                if (dialog.isShowing())
                    dialog.dismiss();

                dialog1 = new Dialog(mContext);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog1.setContentView(R.layout.dialog_take_time);
                dialog1.getWindow().setLayout(v15.getContext().getResources().getDisplayMetrics().widthPixels * 90 / 100, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog1.setCancelable(true);
                dialog1.setCanceledOnTouchOutside(true);

                TextView tv_time_now, tv_pick_time;
                ImageView img_close1;

                tv_time_now = dialog1.findViewById(R.id.tv_time_now);
                tv_pick_time = dialog1.findViewById(R.id.tv_pick_time);
                img_close1 = dialog1.findViewById(R.id.img_close);

                Calendar defaultSelectedDate = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("hh:mm ", Locale.US);
                if (defaultSelectedDate.get(Calendar.AM_PM) == Calendar.AM)
                    tv_time_now.setText("Now (" + month_date.format(defaultSelectedDate.getTime()) + "AM)");
                else
                    tv_time_now.setText("Now (" + month_date.format(defaultSelectedDate.getTime()) + "PM)");

                tv_pick_time.setOnClickListener(v1 -> {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.CustomPickerTheme, (view, year1, month1, dayOfMonth) -> {
                        int myYear = year1;
                        int myday = day;
                        int myMonth = month1;
                        Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR);
                        int minute = c.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, R.style.CustomPickerTheme, (view1, hourOfDay, minute1) -> {
                            int myHour = hourOfDay;
                            int myMinute = minute1;

                            Calendar takenDate = Calendar.getInstance();
                            takenDate.set(Calendar.YEAR, myYear);
                            takenDate.set(Calendar.MONTH, myMonth);
                            takenDate.set(Calendar.DAY_OF_MONTH, myday);
                            takenDate.set(Calendar.HOUR_OF_DAY, myHour);
                            takenDate.set(Calendar.MINUTE, minute1);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

                            Log.e("LLLL_Selected_Time: ", "Year: " + myYear + "\n" +
                                    "Month: " + myMonth + "\n" +
                                    "Day: " + myday + "\n" +
                                    "Hour: " + myHour + "\n" +
                                    "Minute: " + myMinute);

                            for (int i = 0; i < common.getIds().size(); i++) {
                                for (int j = 0; j < common.getFrom().size(); j++) {
                                    if (common.getFrom().get(j).equals("TimeInDay")) {

                                        if (defaultSelectedDate.get(Calendar.AM_PM) == Calendar.AM)
                                            dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " AM, " + dateFormat.format(takenDate.getTime()));
                                        else
                                            dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " PM, " + dateFormat.format(takenDate.getTime()));
                                    } else if (common.getFrom().get(j).equals("TimeInWeek")) {
                                        if (defaultSelectedDate.get(Calendar.AM_PM) == Calendar.AM)
                                            dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " AM, " + dateFormat.format(takenDate.getTime()));
                                        else
                                            dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " PM, " + dateFormat.format(takenDate.getTime()));
                                    }
                                }
                            }
//
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(position);
                                    notifyItemRangeChanged(position, commonArrayList.size());
                                }
                            });


                            if (dialog1.isShowing())
                                dialog1.dismiss();

                        }, hour, minute, DateFormat.is24HourFormat(mContext));
                        timePickerDialog.show();
                    }, year, month, day);
                    datePickerDialog.show();
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemRangeChanged(position, commonArrayList.size());
                        }
                    });

                });

                tv_time_now.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar takenDate = Calendar.getInstance();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

                        for (int i = 0; i < common.getIds().size(); i++) {
                            for (int j = 0; j < common.getFrom().size(); j++) {
                                if (common.getFrom().get(j).equals("TimeInDay")) {

                                    if (defaultSelectedDate.get(Calendar.AM_PM) == Calendar.AM)
                                        dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " AM, " + dateFormat.format(takenDate.getTime()));
                                    else
                                        dbHelper.updatePillsNoRecordsTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " PM, " + dateFormat.format(takenDate.getTime()));
                                } else if (common.getFrom().get(j).equals("TimeInWeek")) {
                                    if (defaultSelectedDate.get(Calendar.AM_PM) == Calendar.AM)
                                        dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " AM, " + dateFormat.format(takenDate.getTime()));
                                    else
                                        dbHelper.updateNoPillsDetailsYesTaken(common.getIds().get(i), Constant.TAKEN, common.getDate(), common.getTime(), timeFormat.format(takenDate.getTime()) + " PM, " + dateFormat.format(takenDate.getTime()));
                                }
                            }
                        }

                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyItemChanged(position);
                                notifyItemRangeChanged(position, commonArrayList.size());
                            }
                        });

                        if (dialog1.isShowing())
                            dialog1.dismiss();
                    }
                });

                img_close1.setOnClickListener(v12 -> {
                    if (dialog1.isShowing())
                        dialog1.dismiss();
                });

                dialog1.show();
            });

            img_close.setOnClickListener(v13 -> {
                if (dialog.isShowing())
                    dialog.dismiss();
            });

            dialog.show();
        });


//        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                mDataset.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, mDataset.size());
//                mItemManger.closeAllItems();
//                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        viewHolder.textViewPos.setText((position + 1) + ".");
//        viewHolder.textViewData.setText(item);
        mItemManger.bind(viewHolder.itemView, position);
    }

    public void addAll(ArrayList<Common> commons) {
        commonArrayList.clear();
        commonArrayList.addAll(commons);
        notifyDatasetChanged();
    }

    @Override
    public int getItemCount() {
        return commonArrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        SwipeLayout swipeLayout;
        RelativeLayout rl_main;
        ImageView img_edit, img_delete, img_more;
        RecyclerView rv_med_type;
        TextView tv_time, tv_quantity, tv_missed, tv_taken;

        public MyClassView(@NonNull View itemView) {
            super(itemView);

            swipeLayout = itemView.findViewById(R.id.swipe);
            rl_main = itemView.findViewById(R.id.rl_main);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);
            img_more = itemView.findViewById(R.id.img_more);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_missed = itemView.findViewById(R.id.tv_missed);
            tv_taken = itemView.findViewById(R.id.tv_taken);
            rv_med_type = itemView.findViewById(R.id.rv_med_type);
        }
    }


}
