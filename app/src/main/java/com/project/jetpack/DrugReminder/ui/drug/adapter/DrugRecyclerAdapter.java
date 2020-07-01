package com.project.jetpack.DrugReminder.ui.drug.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.ui.drug.callback.ChooseDrugCallback;

import java.util.ArrayList;
import java.util.List;

public class DrugRecyclerAdapter extends RecyclerView.Adapter<DrugRecyclerAdapter.ViewHolder> {

    private List<Drug> drugs;
    private ChooseDrugCallback chooseDrugCallback;

    public DrugRecyclerAdapter() {
        drugs = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drug_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(drugs.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseDrugCallback != null)
                    chooseDrugCallback.chooseDrug(drugs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public void setDrugs(List<Drug> drugs){
        this.drugs = drugs;
        notifyDataSetChanged();
    }

    public void setChooseDrugCallback(ChooseDrugCallback chooseDrugCallback){
        this.chooseDrugCallback = chooseDrugCallback;
    }
}
