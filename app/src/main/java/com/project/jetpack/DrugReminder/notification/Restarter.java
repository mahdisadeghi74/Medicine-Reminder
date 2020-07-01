package com.project.jetpack.DrugReminder.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanListActivity;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startActivity(new Intent(context, DrugPlanListActivity.class));
            context.startForegroundService(new Intent(context, NotificationService.class));
        } else {
            context.startService(new Intent(context, NotificationService.class));
        }
    }
}
