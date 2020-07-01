package com.project.jetpack.DrugReminder.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_plan")
public class Plan {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    private int id;

    @ColumnInfo
    private int color;

    @NonNull
    @ColumnInfo
    private long startTime;

    @NonNull
    @ColumnInfo
    private int total;

    @NonNull
    @ColumnInfo
    private int takeTime;

    @NonNull
    @ColumnInfo
    private int numberOfDayCount;

    @NonNull
    @ColumnInfo
    private boolean smartNotification;

    @NonNull
    @ColumnInfo
    private int minuteBefore;

    @NonNull
    @ColumnInfo
    @ForeignKey(entity = Category.class, childColumns = "field_fk_category", parentColumns = "field_id")
    private int fk_category;

    public Plan() {
    }

    public Plan(int color, long startTime, int total, int takeTime, int numberOfDayCount, boolean smartNotification, int minuteBefore, int fk_category) {
        this.color = color;
        this.startTime = startTime;
        this.total = total;
        this.takeTime = takeTime;
        this.numberOfDayCount = numberOfDayCount;
        this.smartNotification = smartNotification;
        this.minuteBefore = minuteBefore;
        this.fk_category = fk_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(int takeTime) {
        this.takeTime = takeTime;
    }

    public int getNumberOfDayCount() {
        return numberOfDayCount;
    }

    public void setNumberOfDayCount(int numberOfDayCount) {
        this.numberOfDayCount = numberOfDayCount;
    }

    public int getFk_category() {
        return fk_category;
    }

    public void setFk_category(int fk_category) {
        this.fk_category = fk_category;
    }

    public boolean isSmartNotification() {
        return smartNotification;
    }

    public void setSmartNotification(boolean smartNotification) {
        this.smartNotification = smartNotification;
    }

    public int getMinuteBefore() {
        return minuteBefore;
    }

    public void setMinuteBefore(int minuteBefore) {
        this.minuteBefore = minuteBefore;
    }
}
