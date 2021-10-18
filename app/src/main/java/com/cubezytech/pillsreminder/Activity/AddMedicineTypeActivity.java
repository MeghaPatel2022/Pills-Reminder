package com.cubezytech.pillsreminder.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineTypeActivity extends AppCompatActivity implements View.OnClickListener {

    static String MED_TYPE = "Tablet";

    @BindView(R.id.img_next_select)
    ImageView img_next_select;
    @BindView(R.id.img_next)
    ImageView img_next;

    @BindView(R.id.ll_tablet)
    LinearLayout ll_tablet;
    @BindView(R.id.ll_capsule)
    LinearLayout ll_capsule;
    @BindView(R.id.ll_injection)
    LinearLayout ll_injection;
    @BindView(R.id.ll_drops)
    LinearLayout ll_drops;
    @BindView(R.id.ll_cream)
    LinearLayout ll_cream;
    @BindView(R.id.ll_powder)
    LinearLayout ll_powder;
    @BindView(R.id.ll_inhalation)
    LinearLayout ll_inhalation;
    @BindView(R.id.ll_spray)
    LinearLayout ll_spray;

    @BindView(R.id.img_tablet)
    ImageView img_tablet;
    @BindView(R.id.img_capsule)
    ImageView img_capsule;
    @BindView(R.id.img_injection)
    ImageView img_injection;
    @BindView(R.id.img_drop)
    ImageView img_drop;
    @BindView(R.id.img_cream)
    ImageView img_cream;
    @BindView(R.id.img_powder)
    ImageView img_powder;
    @BindView(R.id.img_inhalation)
    ImageView img_inhalation;
    @BindView(R.id.img_spray)
    ImageView img_spray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_medicine_type);

        ButterKnife.bind(AddMedicineTypeActivity.this);

        img_next_select.setOnClickListener(this);

        ll_tablet.setOnClickListener(this);
        ll_capsule.setOnClickListener(this);
        ll_injection.setOnClickListener(this);
        ll_drops.setOnClickListener(this);
        ll_cream.setOnClickListener(this);
        ll_powder.setOnClickListener(this);
        ll_inhalation.setOnClickListener(this);
        ll_spray.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next_select:

                TimeInDay timeInDay = Constant.TIMEINDAYSLIST.get(Constant.TIMEINDAYSLIST.size() - 1);
                TimeInDay timeInDay1 = new TimeInDay();
                timeInDay1.setR_id(timeInDay.getR_id());
                timeInDay1.setMed_Name(timeInDay.getMed_Name());
                timeInDay1.setMed_type(MED_TYPE);
                Constant.TIMEINDAYSLIST.set(Constant.TIMEINDAYSLIST.size() - 1, timeInDay1);

                TimeInWeek timeInWeek = Constant.TIMEINWEEKLIST.get(Constant.TIMEINWEEKLIST.size() - 1);
                TimeInWeek timeInWeek1 = new TimeInWeek();
                timeInWeek1.setY_id(timeInWeek.getY_id());
                timeInWeek1.setMed_name(timeInWeek.getMed_name());
                timeInWeek1.setMed_type(MED_TYPE);
                Constant.TIMEINWEEKLIST.set(Constant.TIMEINWEEKLIST.size() - 1, timeInWeek1);

                Intent intent = new Intent(AddMedicineTypeActivity.this, AddMedStrengthActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_tablet:
                MED_TYPE = "Tablet";
                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet_select));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_capsule:
                MED_TYPE = "Capsule";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsule_select));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_injection:
                MED_TYPE = "Injection";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection_select));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_drops:
                MED_TYPE = "Drops";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drop_select));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_cream:
                MED_TYPE = "Cream";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream_select));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_powder:
                MED_TYPE = "Powder";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder_select));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_inhalation:
                MED_TYPE = "Inhalation";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhalation_select));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_sprey));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_spray:
                MED_TYPE = "Spray";

                img_tablet.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablet));
                img_capsule.setImageDrawable(getResources().getDrawable(R.drawable.ic_capsual));
                img_injection.setImageDrawable(getResources().getDrawable(R.drawable.ic_injection));
                img_drop.setImageDrawable(getResources().getDrawable(R.drawable.ic_drops));
                img_cream.setImageDrawable(getResources().getDrawable(R.drawable.ic_cream));
                img_powder.setImageDrawable(getResources().getDrawable(R.drawable.ic_powder));
                img_inhalation.setImageDrawable(getResources().getDrawable(R.drawable.ic_inhelation));
                img_spray.setImageDrawable(getResources().getDrawable(R.drawable.ic_spray_select));

                img_next.setVisibility(View.GONE);
                img_next_select.setVisibility(View.VISIBLE);
                break;
        }
    }
}