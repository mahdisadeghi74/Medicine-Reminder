package com.project.jetpack.DrugReminder.ui.drug;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Category;

import java.util.List;

import javax.inject.Inject;

public class CategoryViewModel extends ViewModel {
    MediatorLiveData<List<Category>> categories;

    @Inject
    MainDataBase mainDataBase;

    @Inject
    public CategoryViewModel() {
    }

    public LiveData<List<Category>> getCategories() {

        return mainDataBase.categoryDao().getCategory();

    }

    public long addCategory(Category category) {
        return mainDataBase.categoryDao().addCategory(category);
    }

    public LiveData<Category> getCategoryById(int id){
        return mainDataBase.categoryDao().getCategoryById(id);
    }
}
