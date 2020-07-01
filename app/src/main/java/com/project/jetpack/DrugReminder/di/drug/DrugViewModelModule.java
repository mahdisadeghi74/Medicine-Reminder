package com.project.jetpack.DrugReminder.di.drug;

import androidx.lifecycle.ViewModel;

import com.project.jetpack.DrugReminder.di.ViewModelKey;
import com.project.jetpack.DrugReminder.ui.drug.CategoryViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugViewModel;
import com.project.jetpack.DrugReminder.ui.drug.PlanViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class DrugViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DrugViewModel.class)
    abstract ViewModel bindDrugViewModel(DrugViewModel drugViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlanViewModel.class)
    abstract ViewModel bindPlanViewModel(PlanViewModel planViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(DrugPlanViewModel.class)
    abstract ViewModel bindDrugPlanViewModel(DrugPlanViewModel DrugPlanViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    abstract ViewModel bindCategoryViewModel(CategoryViewModel DrugPlanViewModel);
}
