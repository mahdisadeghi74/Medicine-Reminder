package com.project.jetpack.DrugReminder.ui.drugplan.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.project.jetpack.DrugReminder.R;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_bottom_sheets, container, false);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.dialog_bottom_sheets, null);
        dialog.setContentView(inflatedView);


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

//        if (behavior != null && behavior instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//        }

        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

//        if (params.getBehavior() instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//        }

        params.height = screenHeight;
        parent.setLayoutParams(params);
    }
}
