package com.cubezytech.pillsreminder.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedStrengthActivity extends AppCompatActivity {

    final List<String> mOptionsItems = new ArrayList<>();
    @BindView(R.id.wheelview)
    WheelView wheelview;
    @BindView(R.id.rl_blurr)
    RelativeLayout rl_blurr;
    @BindView(R.id.img_next_select)
    ImageView img_next_select;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.et_med_strength)
    EditText et_med_strength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_medicine_type);

        setContentView(R.layout.activity_add_med_strength);

        ButterKnife.bind(AddMedStrengthActivity.this);

        mOptionsItems.add("g");
        mOptionsItems.add("IU");
        mOptionsItems.add("mcg");
        mOptionsItems.add("mEq");
        mOptionsItems.add("mg");

        wheelview.setTextColorCenter(getResources().getColor(R.color.blue));
        wheelview.setTypeface(Typeface.createFromAsset(getAssets(), "mont_book.otf"));
        wheelview.setCurrentItem(2);
        wheelview.setCyclic(false);
        wheelview.setItemsVisibleCount(3);
        wheelview.setDividerWidth(4);
        wheelview.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(AddMedStrengthActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                TimeInDay timeInDay1 = new TimeInDay();
                timeInDay1.setR_id(timeInDay.getR_id());
                timeInDay1.setMed_Name(timeInDay.getMed_Name());
                timeInDay1.setMed_type(timeInDay.getMed_type());
                timeInDay1.setMed_Strength(et_med_strength.getText().toString().trim() + " " + mOptionsItems.get(wheelview.getCurrentItem()));
                Constant.TIMEINDAYSLIST.set(Constant.TIMEINDAYSLIST.size() - 1, timeInDay1);

                TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(Constant.TIMEINWEEKLIST.size() - 1);
                TimeInWeek timeInWeek1 = new TimeInWeek();
                timeInWeek1.setY_id(timeInWeek.getY_id());
                timeInWeek1.setMed_name(timeInWeek.getMed_name());
                timeInWeek1.setMed_type(timeInWeek.getMed_type());
                timeInWeek1.setMed_strength(et_med_strength.getText().toString().trim() + " " + mOptionsItems.get(wheelview.getCurrentItem()));
                Constant.TIMEINWEEKLIST.set(Constant.TIMEINWEEKLIST.size() - 1, timeInWeek1);

                Intent intent = new Intent(AddMedStrengthActivity.this, AddReasonActivity.class);
                startActivity(intent);
            }
        });
    }
}