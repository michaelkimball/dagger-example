package dev.michaelkimball.daggerpractice.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.michaelkimball.daggerpractice.di.auth.AuthModule;
import dev.michaelkimball.daggerpractice.di.auth.AuthScope;
import dev.michaelkimball.daggerpractice.di.auth.AuthViewModelsModule;
import dev.michaelkimball.daggerpractice.di.main.MainFragmentBuildersModule;
import dev.michaelkimball.daggerpractice.di.main.MainModule;
import dev.michaelkimball.daggerpractice.di.main.MainScope;
import dev.michaelkimball.daggerpractice.di.main.MainViewModelsModule;
import dev.michaelkimball.daggerpractice.ui.auth.AuthActivity;
import dev.michaelkimball.daggerpractice.ui.main.MainActivity;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class,
            }
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainViewModelsModule.class,
                    MainModule.class,
                    MainFragmentBuildersModule.class
            }
    )
    abstract MainActivity contributeMainActivity();


}
