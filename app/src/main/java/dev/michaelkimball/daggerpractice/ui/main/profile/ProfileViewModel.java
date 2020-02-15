package dev.michaelkimball.daggerpractice.ui.main.profile;

import android.util.Log;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dev.michaelkimball.daggerpractice.SessionManager;
import dev.michaelkimball.daggerpractice.models.User;
import dev.michaelkimball.daggerpractice.ui.auth.AuthResource;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: viewmodel is ready...");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return this.sessionManager.getAuthUser();
    }
}
