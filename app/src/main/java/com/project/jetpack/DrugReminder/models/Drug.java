package com.project.jetpack.DrugReminder.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "tbl_drug")
public class Drug implements Serializable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "field_name")
    private String name;

    public Drug(){
    }
    public Drug(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
