package dev.michaelkimball.daggerpractice;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import dev.michaelkimball.daggerpractice.models.User;
import dev.michaelkimball.daggerpractice.ui.auth.AuthResource;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager(){
    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source){
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((User)null));
            cachedUser.addSource(source, (userAuthResource) -> {
                cachedUser.setValue(userAuthResource);
                cachedUser.removeSource(source);
            });
        } else {
            Log.d(TAG, "authenticateWithId: Previous session detected. Retrieving user from cache.");
        }
    }

    public void logout(){
        Log.d(TAG, "logout: logging out...");
        cachedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
