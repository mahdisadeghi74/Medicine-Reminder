package com.project.jetpack.DrugReminder.di;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import dagger.MapKey;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
