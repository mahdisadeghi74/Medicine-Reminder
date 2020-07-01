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
import com.project.jetpack.DrugReminder.ui.drug.callback.NumberOfRepetationCallback;

import dagger.android.support.DaggerFragment;

public class RepeatationNumberFragment extends DaggerFragment implements View.OnClickListener {
    private NumberOfRepetationCallback numberOfRepetationCallback;
    private NumberPicker npRepetitions;
    private FloatingActionButton fabSubmit;
    private int count = 3;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repeatation_number, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        npRepetitions = view.findViewById(R.id.npRepetitions);
        fabSubmit = view.findViewById(R.id.fabSubmit);
        fabSubmit.setOnClickListener(this);

        npRepetitions.setMinValue(1);
        npRepetitions.setMaxValue(48);
        npRepetitions.setValue(count);
    }


    public void setNumberOfRepetationCallback(NumberOfRepetationCallback numberOfRepetationCallback) {
        this.numberOfRepetationCallback = numberOfRepetationCallback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabSubmit:
                count = npRepetitions.getValue();
                numberOfRepetationCallback.numberOfRepetation(npRepetitions.getValue());
                break;
        }
    }
}
