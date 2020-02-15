package dev.michaelkimball.daggerpractice.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.michaelkimball.daggerpractice.ui.main.posts.PostsFragment;
import dev.michaelkimball.daggerpractice.ui.main.profile.ProfileFragment;

@Module
public abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();
}
