package com.cubezytech.pillsreminder.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cubezytech.pillsreminder.DBHelper.DBHelper;
import com.cubezytech.pillsreminder.Model.TimeInDay;
import com.cubezytech.pillsreminder.Model.TimeInWeek;
import com.cubezytech.pillsreminder.Pref.Constant;
import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineNameActivity extends AppCompatActivity {

    @BindView(R.id.et_med_name)
    EditText et_med_name;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.img_next_select)
    ImageView img_next_select;
    @BindView(R.id.rl_blurr)
    RelativeLayout rl_blurr;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rl_toolbar;
    @BindView(R.id.tv_toolbar_Name)
    TextView tv_toolbar_Name;
    @BindView(R.id.img_toolbar)
    ImageView img_toolbar;
    @BindView(R.id.imgBgBlur)
    ImageView imgBgBlur;

    DBHelper dbHelper;
    ArrayList<TimeInDay> dayList = new ArrayList<>();
    ArrayList<TimeInWeek> weekDayList = new ArrayList<>();

    int height = 0;
    int width = 0;

    public static Bitmap getBitmapFromView(View v) {
        Log.e("LLLLLL_Width: ", String.valueOf(v.getMeasuredWidth()));
        Log.e("LLLLLL_Height: ", String.valueOf(v.getMeasuredHeight()));
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_medicine_name);

        ButterKnife.bind(AddMedicineNameActivity.this);

//        int specWidth = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED);
//        int spec1Height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, View.MeasureSpec.UNSPECIFIED);
//        rl_toolbar.measure(specWidth, spec1Height);
//        rl_toolbar.layout(0, 0, rl_toolbar.getMeasuredWidth(), rl_toolbar.getMeasuredHeight());
//
//        height = rl_toolbar.getHeight();
//        width = rl_toolbar.getWidth();
//
//        Log.e("LLLL_Height: ", String.valueOf(height));
//        Log.e("LLLL_Width: ", String.valueOf(width));
//
//        Bitmap mBlurBitmap = createBlurBitmap();
//        imgBgBlur.setImageBitmap(mBlurBitmap);
//
//        tv_toolbar_Name.setVisibility(View.GONE);
//        img_toolbar.setVisibility(View.GONE);

        dbHelper = new DBHelper(AddMedicineNameActivity.this);

        et_med_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    et_med_name.setBackground(getResources().getDrawable(R.drawable.ic_text_back_select));
                    img_next.setVisibility(View.GONE);
                    img_next_select.setVisibility(View.VISIBLE);
                } else {
                    et_med_name.setBackground(getResources().getDrawable(R.drawable.ic_text_back));
                    img_next.setVisibility(View.VISIBLE);
                    img_next_select.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        img_next_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dbHelper.getPillsNoRecords().isEmpty()) {
                    dayList = dbHelper.getPillsNoRecords();
                }

                if (!dbHelper.getPillsNoRecordsYes().isEmpty()) {
                    weekDayList = dbHelper.getPillsNoRecordsYes();
                }

                TimeInDay timeInDay = new TimeInDay();
                TimeInWeek timeInWeek = new TimeInWeek();
                if (dayList.isEmpty()) {
                    timeInDay.setR_id("0");
                } else {
                    timeInDay.setR_id(String.valueOf(Integer.parseInt(dayList.get(dayList.size() - 1).getR_id()) + 1));
                }

                if (weekDayList.isEmpty()) {
                    timeInWeek.setY_id("0");
                } else {
                    timeInWeek.setY_id(String.valueOf(Integer.parseInt(weekDayList.get(weekDayList.size() - 1).getY_id()) + 1));
                }
                timeInDay.setMed_Name(et_med_name.getText().toString().trim());
                timeInWeek.setMed_name(et_med_name.getText().toString().trim());
                Constant.TIMEINDAYSLIST.add(timeInDay);
                Constant.TIMEINWEEKLIST.add(timeInWeek);

                Intent intent = new Intent(AddMedicineNameActivity.this, AddMedicineTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    public Bitmap createBlurBitmap() {
        Bitmap bitmap = getBitmapFromView(rl_toolbar);
        if (bitmap != null) {
            Constant.blurBitmapWithRenderscript(
                    RenderScript.create(this),
                    bitmap);
        }
        return bitmap;
    }

    public Bitmap captureView(View view) {

        //Create a Bitmap with the same dimensions as the View
        Bitmap image = Bitmap.createBitmap(height,
                width,
                Bitmap.Config.ARGB_4444); //reduce quality
        //Draw the view inside the Bitmap
        Canvas canvas = new Canvas(image);
        view.draw(canvas);

        //Make it frosty
        Paint paint = new Paint();
        paint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        ColorFilter filter =
                new LightingColorFilter(0xFFFFFFFF, 0x00222222); // lighten
        //ColorFilter filter =
        //   new LightingColorFilter(0xFF7F7F7F, 0x00000000); // darken
        paint.setColorFilter(filter);
        canvas.drawBitmap(image, 0, 0, paint);
        return image;
    }


}