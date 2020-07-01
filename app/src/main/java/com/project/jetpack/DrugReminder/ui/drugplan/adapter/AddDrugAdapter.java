package com.project.jetpack.DrugReminder.ui.drugplan.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.jetpack.DrugReminder.ui.drug.callback.AddDrugPlanCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.NumberOfRepetationCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetStartTimeCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTimeToTakeCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTotalNumberDrugCallback;
import com.project.jetpack.DrugReminder.ui.drug.fragments.AddDrugPlanFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.DrugsFragment;
import com.project.jetpack.DrugReminder.ui.drug.callback.ChooseDrugCallback;
import com.project.jetpack.DrugReminder.ui.drug.fragments.RepeatationNumberFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.StartTimeFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.TakeTimeFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.TotalDrugsFragment;

public class AddDrugAdapter extends FragmentPagerAdapter {

    private ChooseDrugCallback chooseDrugCallback;
    private SetStartTimeCallback setStartTimeCallback;
    private SetTotalNumberDrugCallback setTotalNumberDrugCallback;
    private NumberOfRepetationCallback numberOfRepetationCallback;
    private AddDrugPlanCallback addDrugPlanCallback;
    private SetTimeToTakeCallback setTimeToTakeCallback;
    public AddDrugAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    DrugsFragment drugsFragment = new DrugsFragment();
    StartTimeFragment  startTimeFragment = new StartTimeFragment();
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                if (chooseDrugCallback != null)
                drugsFragment.setChooseDrugCallback(chooseDrugCallback);
                return drugsFragment;
            case 1:
                if (setStartTimeCallback != null)
                startTimeFragment.setSetStartTimeCallback(setStartTimeCallback);
                return startTimeFragment;
            case 2:
                TakeTimeFragment takeTimeFragment = new TakeTimeFragment();
                takeTimeFragment.setSetTimeToTakeCallback(setTimeToTakeCallback);
                return takeTimeFragment;
            case 3:
                TotalDrugsFragment totalDrugsFragment = new TotalDrugsFragment();
                totalDrugsFragment.setSetTotalNumberDrugCallback(setTotalNumberDrugCallback);
                return totalDrugsFragment;
            case 4:
                RepeatationNumberFragment repeatationNumberFragment = new RepeatationNumberFragment();
                repeatationNumberFragment.setNumberOfRepetationCallback(numberOfRepetationCallback);
                return repeatationNumberFragment;
            case 5:
                AddDrugPlanFragment addDrugPlanFragment = new AddDrugPlanFragment();
                addDrugPlanFragment.setAddDrugPlanCallback(addDrugPlanCallback);
                return addDrugPlanFragment;
                default:
                    DrugsFragment drugsFragment2 = new DrugsFragment();
                    drugsFragment2.setChooseDrugCallback(chooseDrugCallback);
                    return drugsFragment2;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    public void setChooseDrugCallback(
            ChooseDrugCallback chooseDrugCallback,
            AddDrugPlanCallback addDrugPlanCallback,
            SetTotalNumberDrugCallback setTotalNumberDrugCallback,
            SetStartTimeCallback setStartTimeCallback,
            NumberOfRepetationCallback numberOfRepetationCallback,
            SetTimeToTakeCallback setTimeToTakeCallback
            ){
        this.chooseDrugCallback = chooseDrugCallback;
        this.addDrugPlanCallback = addDrugPlanCallback;
        this.setTotalNumberDrugCallback = setTotalNumberDrugCallback;
        this.setStartTimeCallback = setStartTimeCallback;
        this.numberOfRepetationCallback = numberOfRepetationCallback;
        this.setTimeToTakeCallback = setTimeToTakeCallback;

    }

}
