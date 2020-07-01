package com.project.jetpack.DrugReminder.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.jetpack.DrugReminder.models.Drug;

import java.util.List;

@androidx.room.Dao
public interface DrugDao {

    /////// drug commands
    @Query("SELECT * FROM tbl_drug")
    LiveData<List<Drug>> getDrugs();

    @Query("SELECT * FROM tbl_drug WHERE field_name = :name")
    LiveData<Drug> getDrugByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addDrug(Drug drug);


}
