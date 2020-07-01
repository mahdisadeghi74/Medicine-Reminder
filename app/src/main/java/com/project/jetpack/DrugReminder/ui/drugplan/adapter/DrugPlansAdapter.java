package com.project.jetpack.DrugReminder.ui.drugplan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanDetailActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.listeners.SetOnClickItemListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrugPlansAdapter extends RecyclerView.Adapter<DrugPlansAdapter.ViewHolder> implements StickyHeaderInterface {

    private final int HEADER_TYPE = 1;
    private final int ITEM_TYPE = 2;
    private List<Object> drugPlans;
    private Context context;
    private SetOnClickItemListener setOnClickItemListener;

    public DrugPlansAdapter(Context context) {
        this.context = context;
        drugPlans = new ArrayList<>();
    }

    @NonNull
    @Override
    public DrugPlansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEADER_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drug_plan_header, parent, false);
            return new HeaderViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drug_plan_item, parent, false);
            return new ItemViewHolder(view, setOnClickItemListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull DrugPlansAdapter.ViewHolder holder, int i) {
        if (getItemViewType(holder.getAdapterPosition()) == HEADER_TYPE){
            ((HeaderViewHolder) holder).tvDay.setText((String)drugPlans.get(holder.getAdapterPosition()));
        }else {
            ((ItemViewHolder) holder).tvDrugName.setText(((DrugPlan)drugPlans.get(holder.getAdapterPosition())).getFk_drugId());
            ((ItemViewHolder) holder).tvTime.setText(getTime(((DrugPlan)drugPlans.get(holder.getAdapterPosition())).getDate()));
            ((ItemViewHolder) holder).vTokeIt.setVisibility(((DrugPlan)drugPlans.get(holder.getAdapterPosition())).isTookIt() ? View.VISIBLE : View.GONE);
            ((ItemViewHolder) holder).cnlMain.setBackgroundTintList(ColorStateList.valueOf(((DrugPlan)drugPlans.get(holder.getAdapterPosition())).getColor()));
        }
    }

    @SuppressLint("DefaultLocale")
    private String getTime(long timeStamp) {
        Date date = new Date();
        date.setTime(timeStamp);
        return String.format("%02d:%02d", date.getHours(), date.getMinutes());
    }

    @Override
    public int getItemCount() {
        return drugPlans.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.layout_drug_plan_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        if(drugPlans.get(headerPosition) instanceof String)
            ((TextView)(((ConstraintLayout) header).getChildAt(3))).setText((String)drugPlans.get(headerPosition));
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (drugPlans.get(itemPosition) instanceof String)
            return true;
        else
            return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends ViewHolder {
        TextView tvDay, tvMonth;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }

    public class ItemViewHolder extends ViewHolder implements View.OnClickListener {
        ConstraintLayout cnlMain;
        TextView tvDrugName, tvTime;
        View vTokeIt;
        private SetOnClickItemListener OnClickItemListener;

        public ItemViewHolder(@NonNull View itemView, SetOnClickItemListener setOnClickItemListener) {
            super(itemView);
            cnlMain = itemView.findViewById(R.id.cnlMain);
            tvDrugName = itemView.findViewById(R.id.tvDrugName);
            tvTime = itemView.findViewById(R.id.tvTime);
            vTokeIt = itemView.findViewById(R.id.vTokeIt);
            this.OnClickItemListener = setOnClickItemListener;

            itemView.setOnClickListener(this);
            itemView.setTransitionName("android");
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == v.getId()){
                if (getItemViewType() == ITEM_TYPE) {
                    if (OnClickItemListener != null) {
                        OnClickItemListener.setOnClickItem(getAdapterPosition(), v);
                    }
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (drugPlans.get(position) instanceof String) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }

    public void setDrugPlans(List<Object> drugPlans) {
        if (this.drugPlans == null)
            this.drugPlans = drugPlans;
        else {
            this.drugPlans.clear();
            this.drugPlans.addAll(drugPlans);
        }
        notifyDataSetChanged();
    }

    public List<Object> getDrugPlans() {
        return drugPlans;
    }

    public void setSetOnClickItemListener(SetOnClickItemListener setOnClickItemListener) {
        this.setOnClickItemListener = setOnClickItemListener;
    }

    public void addDrugPlan(DrugPlan drugPlan, int position){
        drugPlans.set(position, drugPlan);
        notifyDataSetChanged();
    }
}
