package com.project.jetpack.DrugReminder.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.notification.NotificationService;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanListActivity;
import com.project.jetpack.DrugReminder.utils.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static MainDataBase mainDataBase(Application application) {
        return Room.databaseBuilder(application, MainDataBase.class, "db_main_drug16").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    static SharedPreferences sharedPreferences(Application application) {
        return application.getSharedPreferences(Constant.SHARED_PREFRENCES_KEY, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static Drug drug() {
        return new Drug();
    }



}
