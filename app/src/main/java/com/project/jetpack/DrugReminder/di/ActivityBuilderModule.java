package com.project.jetpack.DrugReminder.di;

import com.project.jetpack.DrugReminder.di.drug.AddDrugFragmentModule;
import com.project.jetpack.DrugReminder.di.drug.DrugModule;
import com.project.jetpack.DrugReminder.di.drug.DrugScope;
import com.project.jetpack.DrugReminder.di.drug.DrugViewModelModule;
import com.project.jetpack.DrugReminder.ui.category.activity.AddCategoryActivity;
import com.project.jetpack.DrugReminder.ui.drug.MoreOptionsActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.AddDrugPlanActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanDetailActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanListActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.EditDrugPlanActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {


    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class
            }
    )
    abstract DrugPlanListActivity constributDrugPlanListActivty();

    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class,
                    AddDrugFragmentModule.class,
            }
    )
    abstract AddDrugPlanActivity constributAddDrugActivity();


    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class
            }
    )
    abstract AddCategoryActivity constributAddCategoryActivity();

    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class
            }
    )
    abstract MoreOptionsActivity constributMoreOptions();

    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class
            }
    )
    abstract DrugPlanDetailActivity constributDrugPlanDetailActivity();

    @DrugScope
    @ContributesAndroidInjector(
            modules = {
                    DrugModule.class,
                    DrugViewModelModule.class
            }
    )
    abstract EditDrugPlanActivity constributEditDrugPlanActivity();


}
