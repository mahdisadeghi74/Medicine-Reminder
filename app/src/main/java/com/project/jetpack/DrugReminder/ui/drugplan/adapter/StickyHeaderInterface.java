package com.project.jetpack.DrugReminder.ui.drugplan.adapter;

import android.view.View;

public interface StickyHeaderInterface {

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to fetch the position of the header item in the CategoryAdapter
     * that is used for (represents) item at specified position.
     * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
     * @return int. Position of the header item in the CategoryAdapter.
     */
    int getHeaderPositionForItem(int itemPosition);

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to get layout resource id for the header item at specified CategoryAdapter's position.
     * @param headerPosition int. Position of the header item in the CategoryAdapter.
     * @return int. Layout resource id.
     */
    int getHeaderLayout(int headerPosition);

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to setup the header View.
     * @param header View. Header to set the data on.
     * @param headerPosition int. Position of the header item in the CategoryAdapter.
     */
    void bindHeaderData(View header, int headerPosition);

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to verify whether the item represents a header.
     * @param itemPosition int.
     * @return true, if item at the specified CategoryAdapter's position represents a header.
     */
    boolean isHeader(int itemPosition);
}
