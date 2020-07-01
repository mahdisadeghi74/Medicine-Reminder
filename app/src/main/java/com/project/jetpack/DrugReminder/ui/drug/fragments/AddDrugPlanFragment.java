package com.project.jetpack.DrugReminder.ui.drug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.ui.drug.callback.AddDrugPlanCallback;

import dagger.android.support.DaggerFragment;

public class AddDrugPlanFragment extends DaggerFragment implements View.OnClickListener {
    private AddDrugPlanCallback addDrugPlanCallback;
    private Button btnMoreOptions;
    private FloatingActionButton fabSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_drug_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnMoreOptions = view.findViewById(R.id.btnMoreOptions);
        fabSubmit = view.findViewById(R.id.fabSubmit);

        btnMoreOptions.setOnClickListener(this);
        fabSubmit.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public AddDrugPlanCallback getAddDrugPlanCallback() {
        return addDrugPlanCallback;
    }

    public void setAddDrugPlanCallback(AddDrugPlanCallback addDrugPlanCallback) {
        this.addDrugPlanCallback = addDrugPlanCallback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabSubmit:
                addDrugPlanCallback.addDrugPlan();
                break;
            case R.id.btnMoreOptions:
                addDrugPlanCallback.moreOptions();
        }

    }
}
