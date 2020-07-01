package com.project.jetpack.DrugReminder.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.jetpack.DrugReminder.models.Category;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.models.Plan;

@Database(entities = {Drug.class, Plan.class, Category.class, DrugPlan.class}, version = 1, exportSchema = false)
public abstract class MainDataBase extends RoomDatabase {
    public abstract DrugDao drugDao();
    public abstract PlanDao planDao();
    public abstract CategoryDao categoryDao();
    public abstract DrugPlanDao drugPlanDao();
}
