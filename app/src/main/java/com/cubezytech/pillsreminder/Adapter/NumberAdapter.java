package com.cubezytech.pillsreminder.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.MyClassView> {

    ArrayList<Integer> numberList;
    Activity activity;

    public NumberAdapter(ArrayList<Integer> numberList, Activity activity) {
        this.numberList = numberList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {
        Integer integer = numberList.get(position);
        holder.tv_number.setText(String.valueOf(integer));
        if (position == 0) {
            holder.itemView.setBackground(activity.getResources().getDrawable(R.drawable.select_without_bg));
        }
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }


    public class MyClassView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tv_number;

        public MyClassView(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
