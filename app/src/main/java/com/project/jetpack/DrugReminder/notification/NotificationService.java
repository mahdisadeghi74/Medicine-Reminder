package com.project.jetpack.DrugReminder.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Category;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.models.Plan;
import com.project.jetpack.DrugReminder.ui.drug.CategoryViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugViewModel;
import com.project.jetpack.DrugReminder.ui.drug.PlanViewModel;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanDetailActivity;
import com.project.jetpack.DrugReminder.ui.drugplan.activity.DrugPlanListActivity;
import com.project.jetpack.DrugReminder.utils.Today;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class NotificationService extends Service {
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 20;




    private DrugPlanViewModel drugPlanViewModel;
    private DrugViewModel drugViewModel;
    private PlanViewModel planViewModel;
    private CategoryViewModel categoryViewModel;

    public static List<Object> objects;
    public static Drug myDrug;
    public static Category myCategory;

    private static DrugPlanListActivity activity;
    private static ViewModelProvidersFactory viewModelProvidersFactory;
    public NotificationService() {

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);


        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        //  stoptimertask();
        super.onDestroy();
        //  startTimer();

    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {

        timer = new Timer();


        initializeTimerTask();


        timer.schedule(timerTask, 5000, 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        final Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());

                        Object o = null;
                        if (objects != null && objects.size() > 0)
                            o = Today.getToday(objects);

//                        createNotification(new DrugPlan());
                        if (o != null) {
                            if (activity != null) {
                                if (drugPlanViewModel == null) {
                                    drugPlanViewModel = ViewModelProviders.of(activity, viewModelProvidersFactory).get(DrugPlanViewModel.class);
                                }
                                if (drugViewModel == null) {
                                    drugViewModel = ViewModelProviders.of(activity, viewModelProvidersFactory).get(DrugViewModel.class);
                                }
                                if (categoryViewModel == null) {
                                    categoryViewModel = ViewModelProviders.of(activity, viewModelProvidersFactory).get(CategoryViewModel.class);
                                }
                                drugPlanViewModel.getDrugPlans().observe(activity, new Observer<List<DrugPlan>>() {
                                    @Override
                                    public void onChanged(List<DrugPlan> drugPlans) {
                                        objects.clear();
                                        objects.addAll(drugPlans);
                                    }
                                });

                                drugViewModel.getDrugByName(((DrugPlan)o).getFk_drugId()).observe(activity, new Observer<Drug>() {
                                    @Override
                                    public void onChanged(Drug drug) {
                                        myDrug = drug;
                                    }
                                });

                                categoryViewModel.getCategoryById(((DrugPlan)o).getFk_categoryId()).observe(activity, new Observer<Category>() {
                                    @Override
                                    public void onChanged(Category category) {
                                        myCategory = category;
                                    }
                                });
                                if (planViewModel == null)
                                    planViewModel = ViewModelProviders.of(activity, viewModelProvidersFactory).get(PlanViewModel.class);

                                final Object finalO = o;
                                planViewModel.getPlanById(((DrugPlan) o).getFk_planId()).observe(activity, new Observer<Plan>() {
                                    @Override
                                    public void onChanged(Plan plan) {
                                        if (plan != null && plan.isSmartNotification() && !((DrugPlan) finalO).isTookIt()) {
                                            long data = ((DrugPlan) finalO).getDate() / 1000L;
                                            calendar.add(Calendar.MINUTE, - plan.getMinuteBefore());
                                            if ((calendar.getTimeInMillis() / 1000L) == data) {
                                                createNotification((DrugPlan) finalO);
                                            }
                                        }
                                    }
                                });

                            }
                        }
                    }
                });
            }
        };

    }

    private void createNotification(DrugPlan o) {
        Intent notificationIntent = new Intent(this, DrugPlanListActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 2, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent tookItIntent = new Intent(this, DrugPlanDetailActivity.class);
        tookItIntent.putExtra("drugPlanTookIt", (DrugPlan) o);
        tookItIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent tookItIntentIntent =
                PendingIntent.getActivity(this, 0, tookItIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "1");
        mBuilder.setContentTitle(o.getFk_drugId()/* + myCategory.getName() != null ? "(" + myCategory.getName() +")" : ""*/);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(o.getDate());
        mBuilder.setContentText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        mBuilder.setTicker("");
        mBuilder.addAction(R.drawable.ic_add, getString(R.string.took_it), tookItIntentIntent);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setAutoCancel(true);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1", "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId("1");
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }


        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public void setActivity(DrugPlanListActivity activity) {
        this.activity = activity;
    }

    public void setViewModelProvidersFactory(ViewModelProvidersFactory viewModelProvidersFactory) {
        NotificationService.viewModelProvidersFactory = viewModelProvidersFactory;
    }
}
