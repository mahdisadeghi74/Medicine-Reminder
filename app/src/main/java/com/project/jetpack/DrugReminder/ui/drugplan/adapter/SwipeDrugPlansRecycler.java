package com.project.jetpack.DrugReminder.ui.drugplan.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jetpack.DrugReminder.ui.drugplan.listeners.TookItDrugListener;

public class SwipeDrugPlansRecycler extends ItemTouchHelper.SimpleCallback {
    private TookItDrugListener tookItDrugListener;
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    public SwipeDrugPlansRecycler(int dragDirs, int swipeDirs, TookItDrugListener tookItDrugListener) {
        super(dragDirs, swipeDirs);
        this.tookItDrugListener = tookItDrugListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (tookItDrugListener != null){
            tookItDrugListener.tookItdrug(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof DrugPlansAdapter.HeaderViewHolder)
            return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }
}
