package com.project.jetpack.DrugReminder.ui.drug;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Plan;

import java.util.List;

import javax.inject.Inject;

public class PlanViewModel extends ViewModel {

    MediatorLiveData<Plan> plans = new MediatorLiveData<>();

    @Inject
    MainDataBase mainDataBase;
    @Inject
    public PlanViewModel() {
    }

    public LiveData<List<Plan>> observe(){
        return mainDataBase.planDao().getPlans();
    }

    public long addPlan(Plan plan){
        return mainDataBase.planDao().addPlan(plan);
    }

    public LiveData<Plan> getPlanById(int id){
        return mainDataBase.planDao().getPlanById(id);
    }

    public int delete(Plan plan){
        return mainDataBase.planDao().delete(plan);
    }

    public int update(Plan plan){
        return mainDataBase.planDao().update(plan);
    }
}
