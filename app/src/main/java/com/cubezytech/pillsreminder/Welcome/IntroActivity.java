package com.cubezytech.pillsreminder.Welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cubezytech.pillsreminder.Activity.MainActivity;
import com.cubezytech.pillsreminder.Fragment.IntroFragment.FragmentIntro1;
import com.cubezytech.pillsreminder.Fragment.IntroFragment.FragmentIntro2;
import com.cubezytech.pillsreminder.Fragment.IntroFragment.FragmentIntro3;
import com.cubezytech.pillsreminder.Pref.SharedPrefrance;
import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.tv_finished)
    ImageView tv_finished;
    @BindView(R.id.tv_skip)
    TextView tv_skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedPrefrance.getLogin(IntroActivity.this)) {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(IntroActivity.this);
        setViewPager();

        img_next.setOnClickListener(this);
        tv_finished.setOnClickListener(this);
        tv_skip.setOnClickListener(this);
    }

    private void setViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentIntro1(), "ONE");
        adapter.addFragment(new FragmentIntro2(), "TWO");
        adapter.addFragment(new FragmentIntro3(), "THREE");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tv_skip.setVisibility(View.GONE);
                    img_next.setVisibility(View.GONE);
                    tv_finished.setVisibility(View.VISIBLE);
                } else {
                    tv_skip.setVisibility(View.VISIBLE);
                    img_next.setVisibility(View.VISIBLE);
                    tv_finished.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                } else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(2);
                    tv_skip.setVisibility(View.GONE);
                    img_next.setVisibility(View.GONE);
                    tv_finished.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_finished:
            case R.id.tv_skip:
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                SharedPrefrance.setLogin(IntroActivity.this, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else finish();
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mList = new ArrayList<>();
        private final List<String> mTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            return mList.get(i);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mList.add(fragment);
            mTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

}