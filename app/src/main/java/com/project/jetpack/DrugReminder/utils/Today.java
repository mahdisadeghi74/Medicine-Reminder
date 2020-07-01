package com.project.jetpack.DrugReminder.utils;

import android.util.Log;

import com.project.jetpack.DrugReminder.models.DrugPlan;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Today {
    private List<Object> objectList;

    public Today(List<Object> objectList) {
        this.objectList = objectList;
    }

    public static Object getToday(List<Object> objectList) {
        Object object = null;
        if (objectList != null && objectList.size() > 1) {
            Calendar calendar = Calendar.getInstance();
            long now = Math.abs(calendar.getTime().getTime());
            long distance = now - ((DrugPlan) objectList.get(1)).getDate() ;
            object = objectList.get(1);
            Log.d(TAG, "getToday: " + String.valueOf(now - ((DrugPlan) objectList.get(1)).getDate()) );
            for (int position = 2; position < objectList.size(); position++) {
                if (objectList.get(position) instanceof DrugPlan) {
                    if (now - ((DrugPlan) objectList.get(position)).getDate() > 0) {
                        object = objectList.get(position);
                    }else break;
                }

            }

        }


        return object;
    }
}
