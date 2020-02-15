package dev.michaelkimball.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.michaelkimball.daggerpractice.di.ViewModelKey;
import dev.michaelkimball.daggerpractice.ui.main.posts.PostsViewModel;
import dev.michaelkimball.daggerpractice.ui.main.profile.ProfileViewModel;

@Module
public abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);
}
