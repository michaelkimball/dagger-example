package dev.michaelkimball.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;
import dev.michaelkimball.daggerpractice.ui.auth.AuthActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, (userAuthResource) -> {
            if(userAuthResource != null) {
                switch (userAuthResource.status){
                    case LOADING: {
                        break;
                    }
                    case NOT_AUTHENTICATED: {
                        navLoginScreen();
                        break;
                    }
                    case AUTHENTICATED: {
                        Log.d(TAG, "subscribeObservers: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(BaseActivity.this, userAuthResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
