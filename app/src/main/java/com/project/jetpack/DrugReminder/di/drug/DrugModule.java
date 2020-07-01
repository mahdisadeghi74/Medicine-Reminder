package com.project.jetpack.DrugReminder.di.drug;

import android.app.Application;

import androidx.fragment.app.FragmentStatePagerAdapter;

import com.project.jetpack.DrugReminder.notification.NotificationService;
import com.project.jetpack.DrugReminder.ui.category.adapter.CategoryAdapter;
import com.project.jetpack.DrugReminder.ui.drug.adapter.DrugRecyclerAdapter;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.AddDrugPlanActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanListActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.AddDrugAdapter;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.DrugPlansAdapter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DrugModule {

    @DrugScope
    @Provides
    static AddDrugAdapter addDrugAdapter(AddDrugPlanActivity fragmentManager){
        return new AddDrugAdapter(fragmentManager.getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
    }

    @DrugScope
    @Provides
    static DrugPlansAdapter drugPlansAdapter(Application application){
        return new DrugPlansAdapter(application);
    }

    @DrugScope
    @Provides
    static DrugRecyclerAdapter drugRecyclerAdapter(){
        return new DrugRecyclerAdapter();
    }


    @DrugScope
    @Provides
    static CategoryAdapter categoryRecyclerAdapter(){
        return new CategoryAdapter();
    }

}
