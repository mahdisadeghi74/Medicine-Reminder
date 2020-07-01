package com.project.jetpack.DrugReminder.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.jetpack.DrugReminder.models.DrugPlan;

import java.util.List;

@Dao
public interface DrugPlanDao {
    @Query("SELECT * FROM tbl_drug_plan ORDER BY date ASC")
    LiveData<List<DrugPlan>> getDrugPlan();

    @Query("SELECT * FROM tbl_drug_plan WHERE fk_categoryId = :categoryId ORDER BY date ASC")
    LiveData<List<DrugPlan>> getDrugPlan(int categoryId);

    @Query("SELECT * FROM tbl_drug_plan WHERE date >= :startDate And date <= :endDate And fk_categoryId = :categoryId ORDER BY date ASC")
    LiveData<List<DrugPlan>> getDrugPlanWithDate(int categoryId, long startDate, long endDate);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addDrugPlan(DrugPlan drugPlan);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] addDrugPlanList(List<DrugPlan> drugPlans);

    @Query("UPDATE tbl_drug_plan SET tookIt = :tookIt WHERE fk_drugId = :fk_drugId" +
            " AND fk_categoryId = :fk_categoryId AND fk_planId = :fk_planId AND date = :date" )
    long setTookitStatus(boolean tookIt, String fk_drugId, int fk_categoryId, int fk_planId, long date);

    @Query("UPDATE tbl_drug_plan SET color = :color WHERE fk_drugId = :fk_drugId" +
            " AND fk_categoryId = :fk_categoryId AND fk_planId = :fk_planId" )
    long updateColor(int color, String fk_drugId, int fk_categoryId, int fk_planId);

    @Delete
    int delete(DrugPlan drugPlan);

    @Update
    int update(DrugPlan drugPlan);
}
