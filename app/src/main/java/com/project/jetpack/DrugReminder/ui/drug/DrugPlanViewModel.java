package com.project.jetpack.DrugReminder.ui.drug;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.models.Plan;
import com.project.jetpack.DrugReminder.utils.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class DrugPlanViewModel extends ViewModel {

    MediatorLiveData<List<DrugPlan>> drugPlans;

    @Inject
    MainDataBase mainDataBase;
    private int categoryId;

    @Inject
    public DrugPlanViewModel() {
    }


    public LiveData<List<DrugPlan>> getDrugPlans() {
        if (drugPlans == null)
            return mainDataBase.drugPlanDao().getDrugPlan(categoryId);
        else return drugPlans;
    }

    public LiveData<List<DrugPlan>> getDrugPlans(int categoryId) {
        this.categoryId = categoryId;
        if (drugPlans == null)
            return mainDataBase.drugPlanDao().getDrugPlan(categoryId);
        else return drugPlans;
    }

    public LiveData<List<DrugPlan>> getDrugPlans(int categoryId, long startDate, long endDate) {
        this.categoryId = categoryId;
        if (drugPlans == null)
            return mainDataBase.drugPlanDao().getDrugPlanWithDate(categoryId, startDate, endDate);
        else return drugPlans;
    }
    public long changeTookItStatus(boolean tookIt, DrugPlan drugPlan) {
        return mainDataBase.drugPlanDao().setTookitStatus(tookIt, drugPlan.getFk_drugId(), drugPlan.getFk_categoryId(), drugPlan.getFk_planId(), drugPlan.getDate());

    }

    public long[] addDrugPlan(Plan plan, String drugId) {
        return mainDataBase.drugPlanDao().addDrugPlanList(calculateDrugPlan(plan, drugId));
    }

    private List<DrugPlan> calculateDrugPlan(Plan plan, String drugId) {
        List<DrugPlan> drugPlans = new ArrayList<>();
        int numberCount = plan.getNumberOfDayCount();
        int totalCount = plan.getTotal();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(plan.getStartTime());


        if (plan.getTakeTime() == Constant.EVERYDAY) {
            int dayMinutes = 24 * 60;
            int totalMinutes = dayMinutes / numberCount;
            // add now
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            drugPlans.add(new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor()));

            // add next times
            for (int position = 0; position < totalCount - 1; position++) {
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + totalMinutes);
                DrugPlan drugPlan = new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor());
                drugPlans.add(drugPlan);
            }
        } else if (plan.getTakeTime() == Constant.EVERY_OTHER_DAY) {
            int dayMinutes = 24 * 60;
            int totalMinutes = dayMinutes / numberCount;
            // add now
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            drugPlans.add(new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor()));

            int day = calendar.get(Calendar.DATE);
            for (int position = 0; position < totalCount - 1; ) {
                Calendar calendar1 = (Calendar) calendar.clone();
                calendar1.set(Calendar.MINUTE, calendar1.get(Calendar.MINUTE) + totalMinutes);
                if (day != calendar1.get(Calendar.DATE)) {
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
                    day = calendar.get(Calendar.DATE) + 1;
                    continue;
                }
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + totalMinutes);
                DrugPlan drugPlan = new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor());
                drugPlans.add(drugPlan);
                position++;
            }
        } else if (plan.getTakeTime() == Constant.ONE_O_MONTH) {
            int dayMinutes = 24 * 60;
            int totalMinutes = dayMinutes / numberCount;
            // add now
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            drugPlans.add(new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor()));

            int day = calendar.get(Calendar.DATE);
            for (int position = 0; position < totalCount - 1; ) {
                Calendar calendar1 = (Calendar) calendar.clone();
                calendar1.set(Calendar.MINUTE, calendar1.get(Calendar.MINUTE) + totalMinutes);
                if (day != calendar1.get(Calendar.DATE)) {
                    calendar.set(Calendar.DATE, day - 1);
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
                    continue;
                }
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + totalMinutes);
                DrugPlan drugPlan = new DrugPlan(plan.getId(), drugId, plan.getFk_category(), calendar.getTimeInMillis(), plan.getColor());
                drugPlans.add(drugPlan);
                position++;
            }
        }
        return drugPlans;
    }

    public int delete(DrugPlan drugPlan){
        return mainDataBase.drugPlanDao().delete(drugPlan);
    }

    public long update(DrugPlan drugPlan){
        mainDataBase.drugPlanDao().updateColor(drugPlan.getColor(), drugPlan.getFk_drugId(), drugPlan.getFk_categoryId(), drugPlan.getFk_planId());
        return mainDataBase.drugPlanDao().update(drugPlan);
    }
}
