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

public class PillsToneAdapter extends RecyclerView.Adapter<PillsToneAdapter.MyClassView> {

    ArrayList<String> nameList;
    ArrayList<String> actualNameList;
    Activity mActivity;

    AdapterItemClickListener listener;

    public PillsToneAdapter(ArrayList<String> nameList, ArrayList<String> actualNameList, Activity mActivity) {
        this.nameList = nameList;
        this.actualNameList = actualNameList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sounds, parent, false);
        return new MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {
        holder.tv_tone_name.setText(nameList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showImageClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public void setAdapterItemClickListener(AdapterItemClickListener listener) {
        this.listener = listener;
    }

    public interface AdapterItemClickListener {
        void showImageClicked(int pos);
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        TextView tv_tone_name;

        public MyClassView(@NonNull View itemView) {
            super(itemView);

            tv_tone_name = itemView.findViewById(R.id.tv_tone_name);
        }
    }


}
