package com.project.jetpack.DrugReminder.di;

import androidx.lifecycle.ViewModelProvider;

import com.project.jetpack.DrugReminder.viewmodel.ViewModelProvidersFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule  {
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProvidersFactory viewModelProvidersFactory);
}
