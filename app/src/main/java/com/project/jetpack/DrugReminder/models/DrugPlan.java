package com.project.jetpack.DrugReminder.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "tbl_drug_plan",
        primaryKeys = {"fk_planId", "fk_drugId", "date"},
        foreignKeys = {
                @ForeignKey(onDelete = CASCADE, entity = Plan.class, parentColumns = "id", childColumns = "fk_planId"),
                @ForeignKey(onDelete = CASCADE, entity = Drug.class, parentColumns = "field_name", childColumns = "fk_drugId"),
                @ForeignKey(onDelete = CASCADE, entity = Category.class, parentColumns = "field_id", childColumns = "fk_categoryId")
        })
public class DrugPlan implements Serializable {

    @ColumnInfo
    @NonNull
    private int fk_planId;

    @ColumnInfo
    @NonNull
    private String fk_drugId;

    @ColumnInfo
    @NonNull
    private long date;

    @ColumnInfo
    @Nullable
    private boolean tookIt;

    @ColumnInfo
    @Nullable
    private int fk_categoryId;

    @ColumnInfo
    @Nullable
    private int color;
    public DrugPlan() {
        tookIt = false;
    }

    public DrugPlan(int fk_planId, String fk_drugId, int fk_categoryId, long date, int color) {
        this.fk_planId = fk_planId;
        this.fk_drugId = fk_drugId;
        this.fk_categoryId = fk_categoryId;
        this.date = date;
        this.color = color;
    }

    public int getFk_planId() {
        return fk_planId;
    }

    public void setFk_planId(int fk_planId) {
        this.fk_planId = fk_planId;
    }

    public String getFk_drugId() {
        return fk_drugId;
    }

    public void setFk_drugId(String fk_drugId) {
        this.fk_drugId = fk_drugId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isTookIt() {
        return tookIt;
    }

    public void setTookIt(boolean tookIt) {
        this.tookIt = tookIt;
    }

    public int getFk_categoryId() {
        return fk_categoryId;
    }

    public void setFk_categoryId(int fk_categoryId) {
        this.fk_categoryId = fk_categoryId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
