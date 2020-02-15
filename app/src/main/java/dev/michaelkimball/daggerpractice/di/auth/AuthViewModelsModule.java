package dev.michaelkimball.daggerpractice.di.auth;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.michaelkimball.daggerpractice.di.ViewModelKey;
import dev.michaelkimball.daggerpractice.ui.auth.AuthViewModel;

@Module
public abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
