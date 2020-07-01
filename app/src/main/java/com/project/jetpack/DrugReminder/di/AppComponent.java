package com.project.jetpack.DrugReminder.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                ViewModelFactoryModule.class,
                AppModule.class
        }
)
    public interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder Application(Application application);

        AppComponent build();
    }
}
