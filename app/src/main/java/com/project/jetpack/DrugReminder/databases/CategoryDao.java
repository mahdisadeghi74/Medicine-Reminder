package com.project.jetpack.DrugReminder.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project.jetpack.DrugReminder.models.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    /////// drug commands
    @Query("SELECT * FROM tbl_category")
    LiveData<List<Category>> getCategory();

    @Query("SELECT * FROM tbl_category WHERE field_id = :id")
    LiveData<Category> getCategoryById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addCategory(Category category);
}
