package com.project.jetpack.DrugReminder.ui.drug;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Drug;

import java.util.List;

import javax.inject.Inject;

public class DrugViewModel extends ViewModel {

    MediatorLiveData<List<Drug>> drugs = new MediatorLiveData<>();
    private MainDataBase mainDataBase;

    @Inject
    public DrugViewModel(MainDataBase mainDataBase) {
        this.mainDataBase = mainDataBase;
    }

    public LiveData<List<Drug>> observe(){
        return mainDataBase.drugDao().getDrugs();
    }

    public long addDrug(Drug drug){
        return mainDataBase.drugDao().addDrug(drug);
    }
    public LiveData<Drug> getDrugByName(String name){
        return mainDataBase.drugDao().getDrugByName(name);
    }
}
