package devs.mulham.horizontalcalendar.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import devs.mulham.horizontalcalendar.R;

/**
 * @author Mulham-Raee
 * @since v1.0.0
 */
class DateViewHolder extends RecyclerView.ViewHolder {

    TextView textTop;
    TextView textMiddle;
    TextView textBottom;
    View selectionView;
    View layoutContent;
    RecyclerView eventsRecyclerView;
    ConstraintLayout cn_main;

    DateViewHolder(View rootView) {
        super(rootView);
        textTop = rootView.findViewById(R.id.hc_text_top);
        textMiddle = rootView.findViewById(R.id.hc_text_middle);
        textBottom = rootView.findViewById(R.id.hc_text_bottom);
        layoutContent = rootView.findViewById(R.id.hc_layoutContent);
        cn_main = rootView.findViewById(R.id.cn_main);
        selectionView = rootView.findViewById(R.id.hc_selector);
        eventsRecyclerView = rootView.findViewById(R.id.hc_events_recyclerView);
    }
}
