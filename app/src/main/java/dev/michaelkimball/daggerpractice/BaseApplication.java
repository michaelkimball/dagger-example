package dev.michaelkimball.daggerpractice;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import dev.michaelkimball.daggerpractice.di.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
