package com.project.jetpack.DrugReminder.ui.drug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetStartTimeCallback;

import java.util.Calendar;
import java.util.Date;

import dagger.android.support.DaggerFragment;

public class StartTimeFragment extends DaggerFragment implements View.OnClickListener {

    private TimePicker tpStartTime;
    private FloatingActionButton fabSubmit;
    SetStartTimeCallback setStartTimeCallback;
    View view;

    Date date = new Date();
    public StartTimeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start_time, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
    @Override
    public void onResume() {
        super.onResume();
        fabSubmit = view.findViewById(R.id.fabSubmit);
        tpStartTime = view.findViewById(R.id.tpStartTime);
        fabSubmit.setOnClickListener(this);
        tpStartTime.setIs24HourView(true);


        tpStartTime.setCurrentHour(date.getHours());
        tpStartTime.setCurrentMinute(date.getMinutes());
    }

    @Override
    public void onClick(View v) {
        if (setStartTimeCallback != null){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, tpStartTime.getCurrentHour());
            calendar.set(Calendar.MINUTE, tpStartTime.getCurrentMinute());
            date = calendar.getTime();
            setStartTimeCallback.setStartTime(date);
        }
    }

    public void setSetStartTimeCallback(SetStartTimeCallback setStartTimeCallback){
        this.setStartTimeCallback = setStartTimeCallback;
    }
}
