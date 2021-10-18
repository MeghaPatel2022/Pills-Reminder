package com.cubezytech.pillsreminder.Fragment.IntroFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cubezytech.pillsreminder.R;


public class FragmentIntro1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro1, container, false);

        return v;
    }

}
