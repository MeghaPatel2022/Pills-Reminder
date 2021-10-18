package com.cubezytech.pillsreminder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubezytech.pillsreminder.R;

import java.util.ArrayList;

public class MedTypeAdapter extends RecyclerView.Adapter<MedTypeAdapter.MyClassView> {

    ArrayList<String> medList;
    Context mContext;

    public MedTypeAdapter(ArrayList<String> medList, Context mContext) {
        this.medList = medList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tablet, parent, false);
        return new MyClassView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassView holder, int position) {
        String medType = medList.get(position);

        switch (medType) {
            case "Capsule":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_capsual_box));
                break;
            case "Injection":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_injection_box));
                break;
            case "Drops":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_drops_box));
                break;
            case "Cream":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cream_box));
                break;
            case "Powder":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_powder_box));
                break;
            case "Inhalation":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_inhalation_box));
                break;
            case "Spray":
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_spray_box));
                break;
            case "Tablet":
            default:
                holder.img_med_type.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_tablet_box));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return medList.size();
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        ImageView img_med_type;

        public MyClassView(@NonNull View itemView) {
            super(itemView);

            img_med_type = itemView.findViewById(R.id.img_med_type);
        }
    }
}
