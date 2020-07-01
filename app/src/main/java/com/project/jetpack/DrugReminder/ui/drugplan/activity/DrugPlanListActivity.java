package com.project.jetpack.DrugReminder.ui.drugplan.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.databases.MainDataBase;
import com.project.jetpack.DrugReminder.models.Category;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.models.DrugPlan;
import com.project.jetpack.DrugReminder.notification.NotificationService;
import com.project.jetpack.DrugReminder.ui.category.activity.AddCategoryActivity;
import com.project.jetpack.DrugReminder.ui.category.adapter.CategoryAdapter;
import com.project.jetpack.DrugReminder.ui.drug.CategoryViewModel;
import com.project.jetpack.DrugReminder.ui.drug.DrugPlanViewModel;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.DrugPlansAdapter;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.StikyDecoration;
import com.project.jetpack.DrugReminder.ui.drugplan.adapter.SwipeDrugPlansRecycler;
import com.project.jetpack.DrugReminder.ui.drugplan.listeners.SetOnClickItemListener;
import com.project.jetpack.DrugReminder.ui.drugplan.listeners.TookItDrugListener;
import com.project.jetpack.DrugReminder.utils.Constant;
import com.project.jetpack.DrugReminder.utils.PersianCalander;
import com.project.jetpack.DrugReminder.utils.Today;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DrugPlanListActivity extends DaggerAppCompatActivity implements View.OnClickListener, CategoryAdapter.SelectCategoryListener, SetOnClickItemListener {

    private static final String TAG = "sssss";
    private static final int PERMISSION_REQUEST_FOREGROUND_SERVICE = 2;

    ////views
    private FloatingActionButton fabAddDrugPlan;
    private RecyclerView rvDrugPlans;
    private ImageButton imbMenu, imbMore, imbToday;
    private TextView tvDate, tvCategoryTitle;
    private ConstraintLayout crlBottomSheet;
    private ConstraintLayout clbSetting;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetBehavior bottomSheetBehaviorSetting;
    private BottomAppBar babMain;
    private RecyclerView rvCategories;
    private Button btnAddCategory, btnShowList, btnFeedback, btnRateUs;
    private ImageButton imbClose;
    private LinearLayoutManager layoutManager;
    private CoordinatorLayout crlBottomAppBar;
    ///settings views
    private RadioGroup rgLanguage;
    private RadioGroup rgTheme;
    private AppCompatImageButton imbCloseSetting;

    private int firstItemPosition = 0;
    private MediatorLiveData<Integer> selectedCategory = new MediatorLiveData<>();
    private int selectedPosition = -1;
    private int listStatus = Constant.THIS_YEAR;
    DrugPlanViewModel drugPlanViewModel;
    CategoryViewModel categoryViewModel;

    boolean swipe = true;
    private SetOnClickItemListener setOnClickItemListener;
    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;

    @Inject
    Drug drug;

    @Inject
    MainDataBase mainDataBase;

    @Inject
    DrugPlansAdapter drugPlansAdapter;

    @Inject
    CategoryAdapter categoryAdapter;

    @Inject
    SharedPreferences sharedPreferences;

    NotificationService mYourService;

    private Intent mServiceIntent;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        changeLanguage(sharedPreferences.getString(Constant.LANGUAGE_KEY, Constant.LANGUAGE_FA));
        setContentView(R.layout.activity_main);
        configuration();
        initial();
        observe();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            showPermission();
        }
        startNotification();

        // set radiobutton default
        String them = sharedPreferences.getString(Constant.THEME_KEY, Constant.THEME_DEFAULT);
        switch (them) {
            case Constant.THEME_DEFAULT:
                rgTheme.check(R.id.rbDefaultTheme);
                break;
            case Constant.THEME_DARK:
                rgTheme.check(R.id.rbDarkTheme);
                break;
            case Constant.THEME_LIGHT:
                rgTheme.check(R.id.rbLightTheme);
                break;
        }

        // set radiobutton default
        String language = sharedPreferences.getString(Constant.LANGUAGE_KEY, Constant.LANGUAGE_FA);
        switch (language) {
            case Constant.LANGUAGE_FA:
                rgLanguage.check(R.id.rbPersian);
                break;
            case Constant.LANGUAGE_EN:
                rgLanguage.check(R.id.rbEnglish);
                break;
        }
        rvDrugPlans.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setDate();
            }
        });

        rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbEnglish:
                        changeLanguage(Constant.LANGUAGE_EN);
                        break;
                    case R.id.rbPersian:
                        changeLanguage(Constant.LANGUAGE_FA);
                        break;
                }
                finish();
                startActivity(new Intent(DrugPlanListActivity.this, DrugPlanListActivity.class));
            }
        });

        btnFeedback.setOnClickListener(this);

        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String theme = Constant.THEME_DEFAULT;
                switch (checkedId) {
                    case R.id.rbDefaultTheme:
                        theme = Constant.THEME_DEFAULT;
                        break;
                    case R.id.rbDarkTheme:
                        theme = Constant.THEME_DARK;
                        break;
                    case R.id.rbLightTheme:
                        theme = Constant.THEME_LIGHT;
                        break;
                }
                sharedPreferences.edit().putString(Constant.THEME_KEY, theme).apply();
                setSelectedTheme(getResources().getConfiguration());

                finish();
                startActivity(new Intent(DrugPlanListActivity.this, DrugPlanListActivity.class));
                // recreate();

            }
        });
        drugPlansAdapter.setSetOnClickItemListener(setOnClickItemListener);


        SwipeDrugPlansRecycler swipeDrugPlansRecycler = new SwipeDrugPlansRecycler(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT, new TookItDrugListener() {
            @Override
            public void tookItdrug(int position) {
                swipe = false;
                drugPlanViewModel.changeTookItStatus(
                        !(((DrugPlan) drugPlansAdapter.getDrugPlans().get(position)).isTookIt()),
                        (DrugPlan) drugPlansAdapter.getDrugPlans().get(position));

                ((DrugPlan) drugPlansAdapter.getDrugPlans().get(position))
                        .setTookIt(!(((DrugPlan) drugPlansAdapter.getDrugPlans().get(position)).isTookIt()));

                drugPlansAdapter.addDrugPlan(((DrugPlan) drugPlansAdapter.getDrugPlans().get(position)), position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeDrugPlansRecycler);
        itemTouchHelper.attachToRecyclerView(rvDrugPlans);
    }


    private void startNotification() {

        mServiceIntent = new Intent(this, mYourService.getClass());
       if (!isMyServiceRunning(mYourService.getClass())) {
            startService(mServiceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }
    private void observe() {
        if (selectedCategory != null && selectedCategory.getValue() != null && selectedCategory.getValue() == -1) {
            long x = categoryViewModel.addCategory(new Category("لیست من"));
            selectedCategory.setValue(Integer.parseInt(String.valueOf(x)));
            selectedPosition = 0;
            sharedPreferences.edit().putInt(Constant.SELECTED_CATEGORY, selectedCategory.getValue()).apply();
            sharedPreferences.edit().putInt(Constant.SELECTED_CATEGORY_POSITION, selectedPosition).apply();
        }


        categoryViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setCategories(categories);
                categoryAdapter.setSelected(-1, selectedPosition);
            }
        });

        categoryViewModel.getCategoryById(selectedCategory.getValue()).observe(this, new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                tvCategoryTitle.setText(category.getName());
            }
        });

        selectedCategory.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                layoutManager.scrollToPosition(0);
                drugPlanViewModel.getDrugPlans(selectedCategory.getValue()).observe(DrugPlanListActivity.this, new Observer<List<DrugPlan>>() {
                    @Override
                    public void onChanged(List<DrugPlan> drugPlans) {
                        if (swipe) {
                            drugPlansAdapter.setDrugPlans(getFullDrugList(drugPlans));
                            Object nowObject = Today.getToday(getFullDrugList(drugPlans));

                            //go to today
                            layoutManager.scrollToPosition(drugPlansAdapter.getDrugPlans().indexOf(nowObject));

                            setDate();
                        }
                        swipe = true;
                    }
                });
            }
        });

    }

    private void configuration() {
        fabAddDrugPlan = findViewById(R.id.fabAddDrugPlan);
        rvDrugPlans = findViewById(R.id.rvDrugPlans);
        babMain = findViewById(R.id.babMain);
        tvDate = findViewById(R.id.tvDate);
        imbMenu = findViewById(R.id.imbMenu);
        rvCategories = findViewById(R.id.rvCategories);
        crlBottomSheet = findViewById(R.id.crlBottomSheet);
        clbSetting = findViewById(R.id.clbSetting);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        rgLanguage = findViewById(R.id.rgLanguage);
        rgTheme = findViewById(R.id.rgTheme);
        btnShowList = findViewById(R.id.btnShowList);
        btnFeedback = findViewById(R.id.btnFeedback);
        btnRateUs = findViewById(R.id.btnRateUs);
        imbCloseSetting = findViewById(R.id.imbCloseSetting);
        imbClose = findViewById(R.id.imbClose);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        imbMore = findViewById(R.id.imbMore);
        imbToday = findViewById(R.id.imbToday);
        crlBottomAppBar = findViewById(R.id.crlBottomAppBar);
        bottomSheetBehavior = BottomSheetBehavior.from(crlBottomSheet);
        bottomSheetBehaviorSetting = BottomSheetBehavior.from(clbSetting);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehaviorSetting.setPeekHeight(0);
    }

    private void initial() {
        drugPlanViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugPlanViewModel.class);
        categoryViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(CategoryViewModel.class);
        mYourService = new NotificationService();
        mYourService.setActivity(this);
        mYourService.setViewModelProvidersFactory(viewModelProvidersFactory);
        fabAddDrugPlan.setOnClickListener(this);
        imbMenu.setOnClickListener(this);
        btnAddCategory.setOnClickListener(this);
        imbClose.setOnClickListener(this);
        imbMore.setOnClickListener(this);
        imbCloseSetting.setOnClickListener(this);
        imbToday.setOnClickListener(this);
        btnShowList.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnRateUs.setOnClickListener(this);
        setOnClickItemListener = this;
        /// recyclerview
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDrugPlans.setLayoutManager(layoutManager);
        rvDrugPlans.addItemDecoration(new StikyDecoration(drugPlansAdapter));
        rvDrugPlans.setAdapter(drugPlansAdapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        categoryAdapter.setSelectCategoryListener(this);
        rvCategories.setAdapter(categoryAdapter);

        selectedCategory.setValue(sharedPreferences.getInt(Constant.SELECTED_CATEGORY, -1));
        selectedPosition = sharedPreferences.getInt(Constant.SELECTED_CATEGORY_POSITION, -1);
        listStatus = sharedPreferences.getInt(Constant.DRUG_LISTS_STATUS, Constant.THIS_YEAR);
        setListStatus(false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddDrugPlan:
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DrugPlanListActivity.this);
                startActivity(new Intent(this, AddDrugPlanActivity.class), options.toBundle());
                break;
            case R.id.imbMenu:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.btnAddCategory:
                ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation(DrugPlanListActivity.this);
                startActivityForResult(new Intent(this, AddCategoryActivity.class), Constant.ADD_NEW_CATEGORY, options1.toBundle());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.imbClose:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.imbMore:
                bottomSheetBehaviorSetting.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.imbCloseSetting:
                bottomSheetBehaviorSetting.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.imbToday:
                if (drugPlansAdapter.getDrugPlans().size() == 0 || Today.getToday(drugPlansAdapter.getDrugPlans()) == null){

                    Snackbar snackbar = Snackbar.make(crlBottomAppBar, getResources().getString(R.string.today_empty_error),
                            BaseTransientBottomBar.LENGTH_SHORT);

                    CoordinatorLayout.LayoutParams params =
                            (CoordinatorLayout.LayoutParams)
                                    snackbar.getView().getLayoutParams();
                    params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                    snackbar.getView().requestLayout();

                    params.setMargins(0,0,0,250);

                    snackbar.getView().setLayoutParams(params);

                    TextView tvAlert = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    tvAlert.setTextColor(getResources().getColor(R.color.colorSoftWhite));
                    snackbar.show();
                    break;
                }
                RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };
                smoothScroller.setTargetPosition(drugPlansAdapter.getDrugPlans().indexOf(Today.getToday(drugPlansAdapter.getDrugPlans())));
                layoutManager.startSmoothScroll(smoothScroller);
                break;
            case R.id.btnShowList:
                setListStatus(true);
                sharedPreferences.edit().putInt(Constant.DRUG_LISTS_STATUS, listStatus).apply();
                break;
            case R.id.btnFeedback:
                try {
                    //cafe bazaar

                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=" + getPackageName()));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                    // miket
//                    String url = "myket://comment?id=com.project.jetpack.DrugReminder";
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
                }catch (Exception e){

                }
                break;
            case R.id.btnRateUs:

                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private List<Object> getFullDrugList(List<DrugPlan> drugPlans) {
        List<Object> objects = new ArrayList<>();
        if (drugPlans != null && drugPlans.size() > 0) {

            String day = "-1";
            for (int position = 0; position < drugPlans.size(); position++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(drugPlans.get(position).getDate());
                if (!day.equals(String.format("%d%d%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)))) {
                    day = String.format("%d%d%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    objects.add(String.format("%s %d\n%s", PersianCalander.getWeekName(calendar.get(Calendar.DAY_OF_WEEK), DrugPlanListActivity.this),
                            PersianCalander.getCurrentShamsidate(calendar).get(2), PersianCalander.getMonth(calendar, DrugPlanListActivity.this, false)));
                }
                objects.add(drugPlans.get(position));
            }
        }
        mYourService.setObjects(objects);
        return objects;
    }

    private void setDate() {
        if (drugPlansAdapter.getDrugPlans() != null
                && drugPlansAdapter.getDrugPlans().size() > 1
                && ((drugPlansAdapter.getDrugPlans().get(layoutManager.findFirstVisibleItemPosition() >= 0 ?
                layoutManager.findFirstVisibleItemPosition() : 1)) instanceof DrugPlan)) {
            DrugPlan date = (DrugPlan) (drugPlansAdapter.getDrugPlans().get(layoutManager.findFirstVisibleItemPosition() >= 0 ?
                    layoutManager.findFirstVisibleItemPosition() : 1));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getDate());

            tvDate.setText(PersianCalander.getMonth(calendar, DrugPlanListActivity.this, true));


        }
    }

    @Override
    public void selectCategoryPosition(int position) {
        categoryAdapter.setSelected(selectedPosition, position);
        selectedPosition = position;
        selectedCategory.setValue(categoryAdapter.getCategories().get(position).getId());

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        tvCategoryTitle.setText(categoryAdapter.getCategories().get(position).getName());

        sharedPreferences.edit().putInt(Constant.SELECTED_CATEGORY, selectedCategory.getValue()).apply();
        sharedPreferences.edit().putInt(Constant.SELECTED_CATEGORY_POSITION, selectedPosition).apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constant.ADD_NEW_CATEGORY:
                if (data != null) {
                    int id = data.getIntExtra(Constant.CATEGOR_ID, selectedCategory != null
                            && selectedCategory.getValue() != null ? selectedCategory.getValue() : -1);
                    selectedCategory.setValue(id);
                }
                break;
        }
    }

    @Override
    public void setOnClickItem(int position, View view1) {
       // ActivityOptions activityOptions = ActivityOptions.makeScaleUpAnimation(view, 200, 400, 400, 800);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(view1, "android"));
        Intent intent = new Intent(this, DrugPlanDetailActivity.class);
        intent.putExtra("DrugPlan", (DrugPlan) drugPlansAdapter.getDrugPlans().get(position));
        startActivity(intent, activityOptions.toBundle());
    }

    private void changeLanguage(String language) {
        sharedPreferences.edit().putString(Constant.LANGUAGE_KEY, language).apply();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration(getResources().getConfiguration());
        configuration.setLayoutDirection(locale);

        configuration.locale = locale;
        configuration.setLocale(locale);

        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        getResources().getConfiguration().setLocale(locale);
    }

    public void setListStatus(boolean clicked) {
        if (clicked) {
            Calendar calendar = Calendar.getInstance();
            long startDate = PersianCalander.atStartOfDay(calendar.getTimeInMillis());
            long endDate = PersianCalander.atEndOfDay(calendar.getTimeInMillis());
            switch (listStatus) {
                case Constant.THIS_YEAR:
                    listStatus = Constant.TODAY;
                    btnShowList.setText(getResources().getString(R.string.today));
                    Calendar calendar1 = Calendar.getInstance();
                    startDate = PersianCalander.atStartOfDay(calendar1.getTimeInMillis());
                    endDate = PersianCalander.atEndOfDay(calendar1.getTimeInMillis());
                    break;
                case Constant.THIS_MONTH:
                    listStatus = Constant.THIS_YEAR;
                    btnShowList.setText(getResources().getString(R.string.this_year));

                    startDate = PersianCalander.startYear(calendar);
                    endDate = PersianCalander.endYear(calendar);
                    break;
                case Constant.THIS_WEEK:
                    listStatus = Constant.THIS_MONTH;
                    btnShowList.setText(getResources().getString(R.string.this_Month));

                    startDate = PersianCalander.startMonth(calendar);
                    endDate = PersianCalander.endMonth(calendar);
                    break;
                case Constant.TOMORROW:
                    listStatus = Constant.THIS_WEEK;
                    btnShowList.setText(getResources().getString(R.string.this_Week));

                    startDate = PersianCalander.getWeekStartDate();
                    endDate = PersianCalander.getWeekEndDate();
                    break;
                case Constant.TODAY:
                    listStatus = Constant.TOMORROW;
                    btnShowList.setText(getResources().getString(R.string.tomorrow));

                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
                    startDate = PersianCalander.atStartOfDay(calendar.getTimeInMillis());
                    endDate = PersianCalander.atEndOfDay(calendar.getTimeInMillis());
                    break;

            }
            drugPlanViewModel.getDrugPlans(selectedCategory.getValue(), startDate, endDate).observe(this, new Observer<List<DrugPlan>>() {
                @Override
                public void onChanged(List<DrugPlan> drugPlans) {
                    if (swipe) {
                        drugPlansAdapter.setDrugPlans(getFullDrugList(drugPlans));
                    }

                }
            });
        } else {
            switch (listStatus) {
                case Constant.THIS_YEAR:
                    btnShowList.setText(getResources().getString(R.string.this_year));
                    break;
                case Constant.THIS_MONTH:
                    btnShowList.setText(getResources().getString(R.string.this_Month));
                    break;
                case Constant.THIS_WEEK:
                    btnShowList.setText(getResources().getString(R.string.this_Week));
                    break;
                case Constant.TOMORROW:
                    btnShowList.setText(getResources().getString(R.string.tomorrow));
                    break;
                case Constant.TODAY:
                    btnShowList.setText(getResources().getString(R.string.today));
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void showPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND},
                PERMISSION_REQUEST_FOREGROUND_SERVICE);
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND},
                        PERMISSION_REQUEST_FOREGROUND_SERVICE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FOREGROUND_SERVICE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
