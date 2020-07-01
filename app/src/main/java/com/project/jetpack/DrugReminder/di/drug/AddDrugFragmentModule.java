package com.project.jetpack.DrugReminder.di.drug;

import com.project.jetpack.DrugReminder.notification.NotificationService;
import com.project.jetpack.DrugReminder.ui.drug.fragments.AddDrugPlanFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.DrugsFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.RepeatationNumberFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.StartTimeFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.TakeTimeFragment;
import com.project.jetpack.DrugReminder.ui.drug.fragments.TotalDrugsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddDrugFragmentModule {

    @ContributesAndroidInjector
    abstract DrugsFragment contributeDrugFragment();

    @ContributesAndroidInjector
    abstract StartTimeFragment contributeStartTimeFragment();

    @ContributesAndroidInjector
    abstract TotalDrugsFragment contributeTotalDrugsFragment();

    @ContributesAndroidInjector
    abstract RepeatationNumberFragment contributeRepeatationNumberFragment();

    @ContributesAndroidInjector
    abstract TakeTimeFragment contributeTakeTimeFragment();

    @ContributesAndroidInjector
    abstract AddDrugPlanFragment contributeAddDrugPlanFragment();

}
