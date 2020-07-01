package com.project.jetpack.DrugReminder.ui.drug.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTimeToTakeCallback;
import com.project.jetpack.DrugReminder.utils.Constant;

import dagger.android.support.DaggerFragment;

public class TakeTimeFragment extends DaggerFragment implements View.OnClickListener {
    private SetTimeToTakeCallback setTimeToTakeCallback;
    private Button btnEveryDay, btnEveryOtherDay, btnMonthOfDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_take, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEveryDay = view.findViewById(R.id.btnEveryDay);
        btnEveryOtherDay = view.findViewById(R.id.btnEveryOtherDay);
        btnMonthOfDay = view.findViewById(R.id.btnMonthOfDay);

        btnEveryDay.setOnClickListener(this);
        btnEveryOtherDay.setOnClickListener(this);
        btnMonthOfDay.setOnClickListener(this);


    }



    @Override
    public void onResume() {
        super.onResume();

    }

    public void setSetTimeToTakeCallback(SetTimeToTakeCallback setTimeToTakeCallback){
        this.setTimeToTakeCallback = setTimeToTakeCallback;
    }

    @Override
    public void onClick(View v) {
        if (setTimeToTakeCallback != null) {
            switch (v.getId()) {
                case R.id.btnEveryDay:
                    setTimeToTakeCallback.setTimeToTake(Constant.EVERYDAY);
                    break;
                case R.id.btnEveryOtherDay:
                    setTimeToTakeCallback.setTimeToTake(Constant.EVERY_OTHER_DAY);
                    break;
                case R.id.btnMonthOfDay:
                    setTimeToTakeCallback.setTimeToTake(Constant.ONE_O_MONTH);
                    break;
            }
        }
    }
}
