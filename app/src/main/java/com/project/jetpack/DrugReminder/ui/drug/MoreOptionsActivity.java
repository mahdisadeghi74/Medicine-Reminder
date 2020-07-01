package com.project.jetpack.DrugReminder.ui.drug;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.utils.Constant;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MoreOptionsActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private Button btnTakeTime, btnDayTime, btnStartTime, btnMinutesBefore, btnColor, btnSave;
    private Switch swSmartNotification;
    private Toolbar tlbMoteOptions;


    private int numbercount = 3, totalCount = 10, takeTime = Constant.EVERYDAY, minutesBefore = 5;
    private Date startTime;
    private int color;
    private boolean smartNotification = true;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        setContentView(R.layout.activity_more_options);
        configuration();
        initial();

        if (startTime == null) {
            Calendar calendar = Calendar.getInstance();
            startTime = calendar.getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime.getTime());
        btnStartTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

        btnStartTime.setOnClickListener(this);
        btnDayTime.setOnClickListener(this);
        btnTakeTime.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnMinutesBefore.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        btnDayTime.setText(String.format("%d %s", numbercount, getResources().getString(R.string.time_a_day)));
        btnMinutesBefore.setText(String.format("%d %s", minutesBefore, getResources().getString(R.string.minute_before)));
        swSmartNotification.setChecked(smartNotification);
        switch (takeTime) {
            case Constant.EVERYDAY:
                btnTakeTime.setText(getResources().getString(R.string.every_day));
                break;
            case Constant.EVERY_OTHER_DAY:
                btnTakeTime.setText(getResources().getString(R.string.every_other_day));
                break;
            case Constant.ONE_O_MONTH:
                btnTakeTime.setText(getResources().getString(R.string.month_of_day));
                break;
        }


    }

    private void initial() {
        setSupportActionBar(tlbMoteOptions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle("");

        startTime = (Date) getIntent().getSerializableExtra("startTime");
        totalCount = getIntent().getIntExtra("totalCount", totalCount);
        numbercount = getIntent().getIntExtra("numberCount", numbercount);
        takeTime = getIntent().getIntExtra("takeTime", takeTime);
        minutesBefore = getIntent().getIntExtra("minutesBefore", minutesBefore);
        color = getIntent().getIntExtra("color", getResources().getColor(R.color.colorTwitterBlue));
        smartNotification = getIntent().getBooleanExtra("smartNotification", smartNotification);
    }

    private void configuration() {
        btnTakeTime = findViewById(R.id.btnTakeTime);
        btnDayTime = findViewById(R.id.btnDayTime);
        btnStartTime = findViewById(R.id.btnStartTime);
        btnMinutesBefore = findViewById(R.id.btnMinutesBefore);
        btnColor = findViewById(R.id.btnColor);
        swSmartNotification = findViewById(R.id.swSmartNotification);
        btnSave = findViewById(R.id.btnSave);
        tlbMoteOptions = findViewById(R.id.tlbMoteOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartTime:
                final Dialog dialog = new Dialog(this, R.style.ThemeDialog);
                dialog.setContentView(R.layout.dialog_time);
                dialog.show();

                final TimePicker tpStartTime = dialog.findViewById(R.id.tpStartTime);
                final Button btnOk = dialog.findViewById(R.id.btnOk);
                final Button btnCancel = dialog.findViewById(R.id.btnCancel);

                tpStartTime.setIs24HourView(true);
                tpStartTime.setCurrentHour(startTime.getHours());
                tpStartTime.setCurrentMinute(startTime.getMinutes());

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, tpStartTime.getCurrentHour());
                        calendar.set(Calendar.MINUTE, tpStartTime.getCurrentMinute());

                        startTime = calendar.getTime();
                        btnStartTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;

            case R.id.btnTakeTime:
                final Dialog dialog2 = new Dialog(this, R.style.ThemeDialog);
                dialog2.setContentView(R.layout.dialog_time_take);
                dialog2.show();


                final Button btnOk2 = dialog2.findViewById(R.id.btnOk);
                final Button btnCancel2 = dialog2.findViewById(R.id.btnCancel);
                final RadioGroup rgTimeTake = dialog2.findViewById(R.id.rgTimeTake);
                final RadioButton rbEveryDay = dialog2.findViewById(R.id.rbEveryDay);
                final RadioButton rbEveryOtherDay = dialog2.findViewById(R.id.rbEveryOtherDay);
                final RadioButton rbMonthOfDay = dialog2.findViewById(R.id.rbMonthOfDay);

                switch (takeTime) {
                    case Constant.EVERYDAY:
                        rbEveryDay.setChecked(true);
                        break;
                    case Constant.EVERY_OTHER_DAY:
                        rbEveryOtherDay.setChecked(true);
                        break;
                    case Constant.ONE_O_MONTH:
                        rbMonthOfDay.setChecked(true);
                        break;
                }
                btnOk2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (rgTimeTake.getCheckedRadioButtonId()) {
                            case R.id.rbEveryDay:
                                btnTakeTime.setText(getResources().getString(R.string.every_day));
                                takeTime = Constant.EVERYDAY;
                                break;
                            case R.id.rbEveryOtherDay:
                                btnTakeTime.setText(getResources().getString(R.string.every_other_day));
                                takeTime = Constant.EVERY_OTHER_DAY;
                                break;
                            case R.id.rbMonthOfDay:
                                btnTakeTime.setText(getResources().getString(R.string.month_of_day));
                                takeTime = Constant.ONE_O_MONTH;
                                break;
                        }
                        dialog2.dismiss();
                    }
                });

                btnCancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                break;
            case R.id.btnDayTime:
                final Dialog dialog3 = new Dialog(this, R.style.ThemeDialog);
                dialog3.setContentView(R.layout.dialog_day_time);
                dialog3.show();

                final NumberPicker npDayTime = dialog3.findViewById(R.id.npDayTime);
                final Button btnOk3 = dialog3.findViewById(R.id.btnOk);
                final Button btnCancel3 = dialog3.findViewById(R.id.btnCancel);

                npDayTime.setMaxValue(48);
                npDayTime.setMinValue(1);
                npDayTime.setValue(numbercount);
                btnOk3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numbercount = npDayTime.getValue();
                        btnDayTime.setText(String.format("%d %s", numbercount, getResources().getString(R.string.time_a_day)));
                        dialog3.dismiss();
                    }
                });

                btnCancel3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();
                    }
                });
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
                npMinuteBefore.setValue(minutesBefore);

                btnOk4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        minutesBefore = npMinuteBefore.getValue();
                        btnMinutesBefore.setText(String.format("%d %s", minutesBefore, getResources().getString(R.string.minute_before)));
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


                if (color == getResources().getColor(R.color.colorBlackBerry))
                    rbBlackBerry.setChecked(true);
                else if (color == getResources().getColor(R.color.colorEggplantPurple))
                    rbEggplantPurple.setChecked(true);
                else if (color == getResources().getColor(R.color.colorPomegranateRedDark))
                    rbPomegranate.setChecked(true);
                else if (color == getResources().getColor(R.color.colorTomatoRed))
                    rbTomatoRed.setChecked(true);
                else if (color == getResources().getColor(R.color.colorBlueBerry))
                    rbBlueBerry.setChecked(true);
                else if (color == getResources().getColor(R.color.colorEucalyptusBlue))
                    rbEucalyptusBlue.setChecked(true);
                else if (color == getResources().getColor(R.color.colorOceanicGreen))
                    rbOceanicGreen.setChecked(true);
                else if (color == getResources().getColor(R.color.colorMangoColor))
                    rbMangoYellow.setChecked(true);
                else if (color == getResources().getColor(R.color.colorStrawberryPink))
                    rbStrawBerryPink.setChecked(true);
                else if (color == getResources().getColor(R.color.colorOrangeFruit))
                    rbOrange.setChecked(true);
                else if (color == getResources().getColor(R.color.colorTwitterBlue))
                    rbDefault.setChecked(true);

                btnOk5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                        if (rgColors.getCheckedRadioButtonId() == R.id.rbBlackBerry)
                            color = getResources().getColor(R.color.colorBlackBerry);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbEggplantPurple)
                            color = getResources().getColor(R.color.colorEggplantPurple);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbPomegranate)
                            color = getResources().getColor(R.color.colorPomegranateRedDark);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbTomatoRed)
                            color = getResources().getColor(R.color.colorTomatoRed);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbBlueBerry)
                            color = getResources().getColor(R.color.colorBlueBerry);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbEucalyptusBlue)
                            color = getResources().getColor(R.color.colorEucalyptusBlue);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbOceanicGreen)
                            color = getResources().getColor(R.color.colorOceanicGreen);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbMangoYellow)
                            color = getResources().getColor(R.color.colorMangoColor);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbStrawBerryPink)
                            color = getResources().getColor(R.color.colorStrawberryPink);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbOrange)
                            color = getResources().getColor(R.color.colorOrangeFruit);
                        else if (rgColors.getCheckedRadioButtonId() == R.id.rbDefault)
                            color = getResources().getColor(R.color.colorTwitterBlue);
                    }
                });

                btnCancel5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                    }
                });
                break;
            case R.id.btnSave:
                saveAndClose();
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

    @Override
    public void onBackPressed() {
        final Dialog dialog5 = new Dialog(this, R.style.ThemeDialog);
        dialog5.setContentView(R.layout.dialog_yes_no);
        dialog5.show();

        final Button btnOk5 = dialog5.findViewById(R.id.btnYes);
        final Button btnCancel5 = dialog5.findViewById(R.id.btnNo);
        final TextView tvContent = dialog5.findViewById(R.id.tvContent);
        tvContent.setText(getResources().getString(R.string.are_you_sure));
        btnOk5.setText(getResources().getString(R.string.keep_edditing));
        btnCancel5.setText(getResources().getString(R.string.discard));


        btnOk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndClose();
            }
        });

        btnCancel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveAndClose() {
        Intent intent = new Intent();
        intent.putExtra("numberCount", numbercount);
        intent.putExtra("startTime", startTime);
        intent.putExtra("totalCount", totalCount);
        intent.putExtra("takeTime", takeTime);
        intent.putExtra("color", color);
        intent.putExtra("smartNotification", swSmartNotification.isChecked());
        intent.putExtra("minutesBefore", minutesBefore);
        setResult(Constant.MORE_OPTIONS_CODE, intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setSelectedTheme(newConfig);
       // recreate();
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
