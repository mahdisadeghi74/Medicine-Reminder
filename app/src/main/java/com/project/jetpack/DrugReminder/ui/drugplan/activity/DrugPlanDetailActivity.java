package com.project.jetpack.DrugReminder.ui.drugplan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.models.Plan;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugViewModel;
import com.project.jetpack.DrugReminder.ui.drug.PlanViewModel;
import com.project.jetpack.DrugReminder.utils.Constant;
import com.project.jetpack.DrugReminder.utils.PersianCalander;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DrugPlanDetailActivity extends DaggerAppCompatActivity {

    private TextView tvDrugName, tvDate, tvTakeTime, tvNumberDay, tvMinuteBefore, tvSmartNotification;
    private SwitchCompat swTookIt;
    private Toolbar tlbPlanDetail;


    private boolean changeUser = false;

    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;

    @Inject
    SharedPreferences sharedPreferences;

    private DrugPlanViewModel drugPlanViewModel;
    private PlanViewModel planViewModel;
    private DrugViewModel drugViewModel;
    private DrugPlan drugPlan;
    private Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        setContentView(R.layout.activity_drug_plan_detail);
        configuration();
        initial();
        observe();

        swTookIt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (changeUser)
                    drugPlanViewModel.changeTookItStatus(isChecked, drugPlan);
            }
        });
    }

    private void observe() {
        drugPlanViewModel.getDrugPlans(drugPlan.getFk_categoryId()).observe(this, new Observer<List<DrugPlan>>() {
            @Override
            public void onChanged(List<DrugPlan> drugPlans) {

            }
        });
        drugViewModel.getDrugByName(drugPlan.getFk_drugId()).observe(this, new Observer<Drug>() {
            @Override
            public void onChanged(Drug drug) {
                if (drug != null)
                    tvDrugName.setText(drug.getName());
            }
        });
        if (drugPlan != null) {
            planViewModel.getPlanById(drugPlan.getFk_planId()).observe(this, new Observer<Plan>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onChanged(Plan newPlan) {
                    plan = newPlan;
                    if (newPlan != null) {
                        switch (newPlan.getTakeTime()) {
                            case Constant.EVERYDAY:
                                tvTakeTime.setText(getResources().getString(R.string.every_day));
                                break;
                            case Constant.EVERY_OTHER_DAY:
                                tvTakeTime.setText(getResources().getString(R.string.every_other_day));
                                break;
                            case Constant.ONE_O_MONTH:
                                tvTakeTime.setText(getResources().getString(R.string.month_of_day));
                                break;
                        }

                        tvNumberDay.setText(String.format("%d %s", newPlan.getNumberOfDayCount(), getResources().getString(R.string.time_a_day)));
                        tvMinuteBefore.setText(String.format("%d %s", newPlan.getMinuteBefore(), getResources().getString(R.string.minute_before)));
                        tvSmartNotification.setText(String.format("%s %s", getResources().getString(R.string.smart_notification), newPlan.isSmartNotification() ?
                                getResources().getString(R.string.is_on) : getResources().getString(R.string.is_off)));

                        changeUser = false;
                        if ((DrugPlan) getIntent().getSerializableExtra("drugPlanTookIt") != null){
                            changeUser = true;
                            swTookIt.setChecked(true);
                        }else {
                            swTookIt.setChecked(drugPlan.isTookIt());
                            changeUser = true;
                        }
                    }
                }
            });


        }
    }


    private void initial() {
        setSupportActionBar(tlbPlanDetail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle("");

        drugPlanViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugPlanViewModel.class);
        planViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(PlanViewModel.class);
        drugViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugViewModel.class);

        drugPlan = (DrugPlan) getIntent().getSerializableExtra("DrugPlan");

        if (getIntent().getSerializableExtra("drugPlanTookIt") != null)
            drugPlan = (DrugPlan) getIntent().getSerializableExtra("drugPlanTookIt");

        if (drugPlan == null) {
            onBackPressed();
        }

        if (drugPlan != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(drugPlan.getDate());
            List<Integer> integers = PersianCalander.getCurrentShamsidate(calendar);
            tvDate.setText(String.format("%04d/%02d/%02d - %s - %02d:%02d", integers.get(0), integers.get(1), integers.get(2), PersianCalander.getWeekName(calendar.get(Calendar.DAY_OF_WEEK), this), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        }
    }

    private void configuration() {
        tvDrugName = findViewById(R.id.tvDrugName);
        tvDate = findViewById(R.id.tvDate);
        tvTakeTime = findViewById(R.id.tvTakeTime);
        tvNumberDay = findViewById(R.id.tvNumberDay);
        tvMinuteBefore = findViewById(R.id.tvMinuteBefore);
        tvSmartNotification = findViewById(R.id.tvSmartNotification);
        tlbPlanDetail = findViewById(R.id.tlbPlanDetail);
        swTookIt = findViewById(R.id.swTookIt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnEdit:
                Intent intent = new Intent(DrugPlanDetailActivity.this, EditDrugPlanActivity.class);
                intent.putExtra("drugPlan", drugPlan);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                startActivity(intent, options.toBundle());
                break;
            case R.id.mnDelete:
                showDeleteDialog();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        final Dialog dialog = new Dialog(this, R.style.ThemeDialog);
        dialog.setContentView(R.layout.dialog_delete_drug_plan);
        dialog.show();

        final RadioGroup rgDeleteDrugPlan = dialog.findViewById(R.id.rgDeleteDrugPlan);
        final RadioButton rbThisTime = dialog.findViewById(R.id.rbThisTime);
        final RadioButton rbAllFollowingTime = dialog.findViewById(R.id.rbAllFollowingTime);
        final Button btnOk = dialog.findViewById(R.id.btnOk);
        final Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOk.setEnabled(false);
        btnOk.setTextColor(getResources().getColor(R.color.colorDarkShadow));

        rgDeleteDrugPlan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                btnOk.setEnabled(true);
                btnOk.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = rgDeleteDrugPlan.getCheckedRadioButtonId();
                int result = -1;
                if (drugPlan != null && plan != null) {
                    if (checked == rbThisTime.getId())
                        result = drugPlanViewModel.delete(drugPlan);
                    else result = planViewModel.delete(plan);
                }
                dialog.dismiss();

                if (result != -1)
                    onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
                        setTheme(R.style.AppTheme);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        setTheme(R.style.AppThemeDark);
                        break;
                }
                break;
            case Constant.THEME_DARK:
                setTheme(R.style.AppThemeDark);
                break;
            case Constant.THEME_LIGHT:
                setTheme(R.style.AppTheme);
                break;
        }
    }


}
