package com.project.jetpack.DrugReminder.ui.drug.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.jetpack.DrugReminder.R;
import com.project.jetpack.DrugReminder.models.Drug;
import com.project.jetpack.DrugReminder.ui.drug.DrugViewModel;
import com.project.jetpack.DrugReminder.ui.drug.adapter.DrugRecyclerAdapter;
import com.project.jetpack.DrugReminder.ui.drug.callback.ChooseDrugCallback;
import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DrugsFragment extends DaggerFragment implements View.OnClickListener, ChooseDrugCallback {

    private DrugViewModel drugViewModel;
    private RecyclerView rvDrugName;
    EditText etDrugName;
    View view;

    private FloatingActionButton fabSubmit;
    private ChooseDrugCallback chooseDrugCallback;
    @Inject
    ViewModelProvidersFactory viewModelProvidersFactory;

    @Inject
    DrugRecyclerAdapter adapter;

    public DrugsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drugs, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drugViewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(DrugViewModel.class);

        fabSubmit = view.findViewById(R.id.fabSubmit);
        rvDrugName = view.findViewById(R.id.rvDrugName);
        rvDrugName.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        rvDrugName.setAdapter(adapter);
        fabSubmit.hide();
        observeDrugs();

        fabSubmit.setOnClickListener(this);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void observeDrugs() {
        drugViewModel.observe().observe(this, new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                adapter.setDrugs(drugs);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabSubmit:
                drugViewModel.getDrugByName(etDrugName.getText().toString()).observe(this, new Observer<Drug>() {
                    @Override
                    public void onChanged(Drug drug) {
                        if (drug == null){
                            long id = drugViewModel.addDrug(new Drug(etDrugName.getText().toString()));
                            drug = new Drug(etDrugName.getText().toString());
                            if (id != -1)
                                drug.setName(etDrugName.getText().toString());
                            chooseDrug(drug);
                        }else chooseDrug(drug);
                    }
                });
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        etDrugName = view.findViewById(R.id.etDrugName);
        etDrugName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty())
                    fabSubmit.show();
                else fabSubmit.hide();
            }
        });
        adapter.setChooseDrugCallback(this);
    }

    public void setChooseDrugCallback(ChooseDrugCallback chooseDrugCallback){
        this.chooseDrugCallback = chooseDrugCallback;
    }

    @Override
    public void chooseDrug(Drug drug) {
        if (drug != null) {
            etDrugName.setText(drug.getName());
            if (chooseDrugCallback != null) {
                chooseDrugCallback.chooseDrug(drug);
            }
        }
    }
}
