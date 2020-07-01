package com.project.jetpack.DrugReminder.ui.drug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTotalNumberDrugCallback;

import dagger.android.support.DaggerFragment;

public class TotalDrugsFragment extends DaggerFragment implements View.OnClickListener {
    private SetTotalNumberDrugCallback setTotalNumberDrugCallback;
    private NumberPicker npTotal;
    private FloatingActionButton fabSubmit;
    private View view;
    private int count = 10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_total_drugs, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        npTotal = view.findViewById(R.id.npTotal);
        fabSubmit = view.findViewById(R.id.fabSubmit);
        npTotal.setMinValue(1);
        npTotal.setMaxValue(200);

        npTotal.setValue(count);
        fabSubmit.setOnClickListener(this);
    }

    public void setSetTotalNumberDrugCallback(SetTotalNumberDrugCallback setTotalNumberDrugCallback) {
        this.setTotalNumberDrugCallback = setTotalNumberDrugCallback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabSubmit:
                count = npTotal.getValue();
                setTotalNumberDrugCallback.setTotalNumberDrug(npTotal.getValue());
                break;
        }
    }
}
