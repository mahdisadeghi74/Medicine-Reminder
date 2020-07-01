package com.project.jetpack.DrugReminder.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.jetpack.DrugReminder.models.Plan;

import java.util.List;

@Dao
public interface PlanDao {
    ////// plan commands
    @Query("SELECT * FROM tbl_plan")
    LiveData<List<Plan>> getPlans();

    @Query("SELECT * FROM tbl_plan WHERE id = :id")
    LiveData<Plan> getPlanById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addPlan(Plan plan);

    @Delete
    int delete(Plan plan);

    @Update
    int update(Plan plan);
}
