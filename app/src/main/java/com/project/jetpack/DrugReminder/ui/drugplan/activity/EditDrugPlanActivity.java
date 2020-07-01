package com.project.jetpack.DrugReminder.ui.drugplan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.models.Plan;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.PlanViewModel;
import com.project.jetpack.DrugReminder.utils.Constant;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class EditDrugPlanActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private TextView tvDrugName;
    private Button btnMinutesBefore, btnColor, btnSave;
    private Switch swSmartNotification;
    private Toolbar tlbEditDrugPlan;

    private DrugPlan drugPlan;
    private Plan plan;
    private DrugPlanViewModel drugPlanViewModel;
    private PlanViewModel planViewModel;

    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        setContentView(R.layout.activity_edit_drug_plan);
        configuration();
        initial();
        observer();

        btnSave.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnMinutesBefore.setOnClickListener(this);


        swSmartNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (plan != null) {
                    plan.setSmartNotification(isChecked);
                    btnMinutesBefore.setText(String.format("%d %s", plan.getMinuteBefore(), getResources().getString(R.string.minute_before)));
                }
            }
        });
    }

    private void observer() {

    }

    private void initial() {
        setSupportActionBar(tlbEditDrugPlan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle("");
        drugPlanViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugPlanViewModel.class);
        planViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(PlanViewModel.class);
        drugPlan = (DrugPlan) getIntent().getSerializableExtra("drugPlan");
        if (drugPlan == null)
            finish();
        else {
            planViewModel.getPlanById(drugPlan.getFk_planId()).observe(this, new Observer<Plan>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onChanged(Plan p) {
                    plan = p;
                    swSmartNotification.setChecked(plan.isSmartNotification());
                    btnMinutesBefore.setText(String.format("%d %s", plan.getMinuteBefore(), getResources().getString(R.string.minute_before)));
                }
            });
        }
        tvDrugName.setText(drugPlan.getFk_drugId());

    }

    private void configuration() {
        tvDrugName = findViewById(R.id.tvDrugName);
        btnMinutesBefore = findViewById(R.id.btnMinutesBefore);
        btnColor = findViewById(R.id.btnColor);
        btnSave = findViewById(R.id.btnSave);
        swSmartNotification = findViewById(R.id.swSmartNotification);
        tlbEditDrugPlan = findViewById(R.id.tlbEditDrugPlan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                saveAndClose();
                break;
            case R.id.btnMinutesBefore:
                final Dialog dialog4 = new Dialog(this, R.style.ThemeDialog);
                dialog4.setContentView(R.layout.dialog_minute_before);
                dialog4.show();

                final NumberPicker npMinuteBefore = dialog4.findViewById(R.id.npMinuteBefore);
                final Button btnOk4 = dialog4.findViewById(R.id.btnOk);
                final Button btnCancel4 = dialog4.findViewById(R.id.btnCancel);

                npMinuteBefore.setMaxValue(30);
                npMinuteBefore.setMinValue(0);
                npMinuteBefore.setValue(plan.getMinuteBefore());

                btnOk4.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onClick(View v) {
                        plan.setMinuteBefore(npMinuteBefore.getValue());
                        btnMinutesBefore.setText(String.format("%d %s", plan.getMinuteBefore(), getResources().getString(R.string.minute_before)));
                        dialog4.dismiss();
                    }
                });

                btnCancel4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                    }
                });
                break;
            case R.id.btnColor:
                final Dialog dialog5 = new Dialog(this, R.style.ThemeDialog);
                dialog5.setContentView(R.layout.dialog_color);
                dialog5.show();

                final Button btnOk5 = dialog5.findViewById(R.id.btnOk);
                final Button btnCancel5 = dialog5.findViewById(R.id.btnCancel);
                final RadioGroup rgColors = dialog5.findViewById(R.id.rgColors);

                final RadioButton rbBlackBerry = dialog5.findViewById(R.id.rbBlackBerry);
                final RadioButton rbEggplantPurple = dialog5.findViewById(R.id.rbEggplantPurple);
                final RadioButton rbPomegranate = dialog5.findViewById(R.id.rbPomegranate);
                final RadioButton rbTomatoRed = dialog5.findViewById(R.id.rbTomatoRed);
                final RadioButton rbBlueBerry = dialog5.findViewById(R.id.rbBlueBerry);
                final RadioButton rbEucalyptusBlue = dialog5.findViewById(R.id.rbEucalyptusBlue);
                final RadioButton rbOceanicGreen = dialog5.findViewById(R.id.rbOceanicGreen);
                final RadioButton rbMangoYellow = dialog5.findViewById(R.id.rbMangoYellow);
                final RadioButton rbStrawBerryPink = dialog5.findViewById(R.id.rbStrawBerryPink);
                final RadioButton rbOrange = dialog5.findViewById(R.id.rbOrange);
                final RadioButton rbDefault = dialog5.findViewById(R.id.rbDefault);


                if (drugPlan.getColor() == getResources().getColor(R.color.colorBlackBerry))
                    rbBlackBerry.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorEggplantPurple))
                    rbEggplantPurple.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorPomegranateRedDark))
                    rbPomegranate.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorTomatoRed))
                    rbTomatoRed.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorBlueBerry))
                    rbBlueBerry.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorEucalyptusBlue))
                    rbEucalyptusBlue.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorOceanicGreen))
                    rbOceanicGreen.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorMangoColor))
                    rbMangoYellow.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorStrawberryPink))
                    rbStrawBerryPink.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorOrangeFruit))
                    rbOrange.setChecked(true);
                else if (drugPlan.getColor() == getResources().getColor(R.color.colorTwitterBlue))
                    rbDefault.setChecked(true);

                btnOk5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                        if (rgColors.getCheckedRadioButtonId() == R.id.rbBlackBerry)
                            drugPlan.setColor(getResources().getColor(R.color.colorBlackBerry));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbEggplantPurple)
                            drugPlan.setColor(getResources().getColor(R.color.colorEggplantPurple));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbPomegranate)
                            drugPlan.setColor(getResources().getColor(R.color.colorPomegranateRedDark));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbTomatoRed)
                            drugPlan.setColor(getResources().getColor(R.color.colorTomatoRed));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbBlueBerry)
                            drugPlan.setColor(getResources().getColor(R.color.colorBlueBerry));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbEucalyptusBlue)
                            drugPlan.setColor(getResources().getColor(R.color.colorEucalyptusBlue));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbOceanicGreen)
                            drugPlan.setColor(getResources().getColor(R.color.colorOceanicGreen));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbMangoYellow)
                            drugPlan.setColor(getResources().getColor(R.color.colorMangoColor));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbStrawBerryPink)
                            drugPlan.setColor(getResources().getColor(R.color.colorStrawberryPink));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbOrange)
                            drugPlan.setColor(getResources().getColor(R.color.colorOrangeFruit));
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbDefault)
                            drugPlan.setColor(getResources().getColor(R.color.colorTwitterBlue));
                    }
                });

                btnCancel5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndClose() {
        drugPlanViewModel.update(drugPlan);
        planViewModel.update(plan);
        finish();
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this, R.style.ThemeDialog);
        dialog.setContentView(R.layout.dialog_yes_no);
        dialog.show();

        final Button btnOk = dialog.findViewById(R.id.btnYes);
        final Button btnCancel = dialog.findViewById(R.id.btnNo);
        final TextView tvContent = dialog.findViewById(R.id.tvContent);
        tvContent.setText(getResources().getString(R.string.are_you_sure));
        btnOk.setText(getResources().getString(R.string.keep_edditing));
        btnCancel.setText(getResources().getString(R.string.discard));


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveAndClose();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
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
