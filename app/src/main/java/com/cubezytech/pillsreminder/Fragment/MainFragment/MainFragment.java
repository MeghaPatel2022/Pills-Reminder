package com.cubezytech.pillsreminder.Fragment.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.cubezytech.pillsreminder.Activity.AddMedicineNameActivity;
import com.cubezytech.pillsreminder.Activity.SettingsActivity;
import com.cubezytech.pillsreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.img_add_med)
    ImageView img_add_med;
    @BindView(R.id.img_toolbar)
    ImageView img_toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, v);

        img_add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMedicineNameActivity.class);
                startActivity(intent);
            }
        });

        img_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
