package com.project.jetpack.DrugReminder.ui.category.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Category;
import com.project.jetpack.DrugReminder.ui.drug.CategoryViewModel;
import com.project.jetpack.DrugReminder.utils.Constant;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AddCategoryActivity extends DaggerAppCompatActivity {

    private Toolbar tlbCategory;
    private CategoryViewModel categoryViewModel;
    private EditText etCategoryTitle;

    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedTheme(getResources().getConfiguration());
        setContentView(R.layout.activity_add_category);
        configuration();
        initial();
        categoryViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(CategoryViewModel.class);
    }

    private void initial() {
        setSupportActionBar(tlbCategory);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    private void configuration() {
        tlbCategory = findViewById(R.id.tlbCategory);
        etCategoryTitle = findViewById(R.id.etCategoryTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_categoty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.mnDone:
                if (etCategoryTitle.getText().toString().trim().isEmpty())
                    finish();
                else{
                    long categoryId = categoryViewModel.addCategory(new Category(etCategoryTitle.getText().toString()));
                    if (categoryId == -1){
                        Toast.makeText(this, "دسته بندی با این عنوان وجود دارد", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(Constant.CATEGOR_ID, Integer.parseInt(String.valueOf(categoryId)));
                        setResult(Constant.ADD_NEW_CATEGORY, intent);
                        finish();
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
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
