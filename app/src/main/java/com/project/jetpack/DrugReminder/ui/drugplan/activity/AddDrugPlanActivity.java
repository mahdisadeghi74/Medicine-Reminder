package com.project.jetpack.DrugReminder.ui.drugplan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.models.Plan;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.MoreOptionsActivity;
import com.project.jetpack.DrugReminder.ui.drug.PlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.callback.AddDrugPlanCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.ChooseDrugCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.NumberOfRepetationCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetStartTimeCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTimeToTakeCallback;
import com.project.jetpack.DrugReminder.ui.drug.callback.SetTotalNumberDrugCallback;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.AddDrugAdapter;
import com.project.jetpack.DrugReminder.utils.Constant;
import com.project.jetpack.DrugReminder.utils.NonSwipeAbleViewPager;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AddDrugPlanActivity extends DaggerAppCompatActivity implements ChooseDrugCallback, AddDrugPlanCallback, NumberOfRepetationCallback, SetStartTimeCallback, SetTotalNumberDrugCallback, SetTimeToTakeCallback {
    private NonSwipeAbleViewPager vpAddDrug;
    private Toolbar tlbMain;
    private int currentItem = 0;
    private PlanViewModel planViewModel;
    private DrugPlanViewModel drugPlanViewModel;
    private Drug drug;
    private int totalCount, takeTime, numerCount, color;
    private Date startTime;
    private boolean smartNotification = true;
    private int minuteBefore = 5;
    @Inject
    MainDataBase mainDataBase;

    @Inject
    AddDrugAdapter addDrugAdapter;

    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;


    @Inject
    SharedPreferences sharedPreferences;
    private String TAG = "sss";
    private int selected_categoty = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        setContentView(R.layout.activity_add_drug);
        configuration();
        initial();

        planViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(PlanViewModel.class);
        drugPlanViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugPlanViewModel.class);
        observe();
     }

    private void observe() {
        planViewModel.observe().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                Log.d(TAG, "onChanged: rrrr" + plans.size());
            }
        });
    }


    private void initial() {
        addDrugAdapter.setChooseDrugCallback(this, this,
                this, this, this,
                this);
        vpAddDrug.setAdapter(addDrugAdapter);
        setSupportActionBar(tlbMain);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        selected_categoty = sharedPreferences.getInt(Constant.SELECTED_CATEGORY, -1);
        if (selected_categoty == -1)
            selected_categoty = 1;

        color = getResources().getColor(R.color.colorTwitterBlue);
    }

    private void configuration() {
        vpAddDrug = findViewById(R.id.vpAddDrug);
        tlbMain = findViewById(R.id.tlbMain);
    }



    @Override
    public void onBackPressed() {
        if (currentItem == 0)
            super.onBackPressed();
        else
            vpAddDrug.setCurrentItem(--currentItem);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void nextPage(){
        if (currentItem < addDrugAdapter.getCount() - 1)
            vpAddDrug.setCurrentItem(++currentItem);
    }

    @Override
    public void addDrugPlan() {
        long result = planViewModel.addPlan(new Plan(color, startTime.getTime(), totalCount, takeTime, numerCount, smartNotification, minuteBefore, selected_categoty));
        if (result != -1){
            planViewModel.getPlanById(Integer.parseInt(String.valueOf(result))).observe(this, new Observer<Plan>() {
                @Override
                public void onChanged(Plan plan) {
                    long[] results = drugPlanViewModel.addDrugPlan(plan, drug.getName());
                    finish();
                }
            });

        }

    }

    @Override
    public void moreOptions() {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddDrugPlanActivity.this);
        Intent intent = new Intent(AddDrugPlanActivity.this, MoreOptionsActivity.class);
        intent.putExtra("numberCount", numerCount);
        intent.putExtra("startTime", startTime);
        intent.putExtra("totalCount", totalCount);
        intent.putExtra("takeTime", takeTime);
        intent.putExtra("color", color);
        intent.putExtra("smartNotification", smartNotification);
        intent.putExtra("minutesBefore", minuteBefore);
        startActivityForResult(intent, Constant.MORE_OPTIONS_CODE, options.toBundle());
    }

    @Override
    public void numberOfRepetation(int count) {
        numerCount = count;
        nextPage();
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        nextPage();
    }

    @Override
    public void setTotalNumberDrug(int count) {
        totalCount = count;
        nextPage();
    }

    @Override
    public void setTimeToTake(int timeToTake) {
        takeTime = timeToTake;
        nextPage();
    }

    @Override
    public void chooseDrug(Drug drug) {
        this.drug = drug;
        if (drug != null) {
            nextPage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Constant.MORE_OPTIONS_CODE:
                if (data != null){
                    numerCount = data.getIntExtra("numberCount", numerCount);
                    startTime = (Date) data.getSerializableExtra("startTime");
                    totalCount = data.getIntExtra("totalCount", totalCount);
                    takeTime = data.getIntExtra("takeTime", takeTime);
                    color = data.getIntExtra("color", getResources().getColor(R.color.colorTwitterBlue));
                    smartNotification = data.getBooleanExtra("smartNotification", true);
                    minuteBefore = data.getIntExtra("minutesBefore", 5);
                }
                break;
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSelectedTheme(newConfig);
        recreate();
    }

    private void setSelectedTheme(Configuration configuration) {
        String them = sharedPreferences.getString(Constant.THEME_KEY, Constant.THEME_DEFAULT);
        switch (them) {
            case Constant.THEME_DEFAULT:
                switch (configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        setTheme(R.style.AppBaseThemeLight);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        setTheme(R.style.AppBaseThemeDark);
                        break;
                }
                break;
            case Constant.THEME_DARK:
                setTheme(R.style.AppBaseThemeDark);
                break;
            case Constant.THEME_LIGHT:
                setTheme(R.style.AppBaseThemeLight);
                break;
        }
    }
}
