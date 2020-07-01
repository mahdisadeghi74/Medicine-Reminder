package com.project.jetpack.DrugReminder.ui.category.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Category> categories = new ArrayList<>();
    private SelectCategoryListener selectCategoryListener;

    public CategoryAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        return new ViewHolder(view, selectCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rlSelected.setVisibility(categories.get(position).isSelected() ? View.VISIBLE : View.GONE);
        holder.tvCategoryTitle.setText(categories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rlSelected;
        TextView tvCategoryTitle;
        SelectCategoryListener selectCategoryListener;
        public ViewHolder(@NonNull View itemView, SelectCategoryListener selectCategoryListener) {
            super(itemView);
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle);
            rlSelected = itemView.findViewById(R.id.rlSelected);
            this.selectCategoryListener = selectCategoryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectCategoryListener != null)
                selectCategoryListener.selectCategoryPosition(getAdapterPosition());
        }
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setSelected(int lastPosition, int newPosition){
        if (lastPosition != -1)
            categories.get(lastPosition).setSelected(false);
        categories.get(newPosition).setSelected(true);
        notifyDataSetChanged();
    }

    public interface SelectCategoryListener {
        void selectCategoryPosition(int position);
    }

    public void setSelectCategoryListener(SelectCategoryListener selectCategoryListener) {
        this.selectCategoryListener = selectCategoryListener;
    }
}
