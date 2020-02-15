package dev.michaelkimball.daggerpractice.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerAppCompatActivity;
import dev.michaelkimball.daggerpractice.R;
import dev.michaelkimball.daggerpractice.ui.main.MainActivity;
import dev.michaelkimball.daggerpractice.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText userId;

    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener((v) -> attemptLogin());

        this.viewModel = new ViewModelProvider(this, this.providerFactory).get(AuthViewModel.class);

        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, (userAuthResource) -> {
            if(userAuthResource != null) {
                switch (userAuthResource.status){
                    case LOADING: {
                        showProgressBar(true);
                        break;
                    }
                    case NOT_AUTHENTICATED: {
                        showProgressBar(false);
                        break;
                    }
                    case AUTHENTICATED: {
                        showProgressBar(false);
                        Log.d(TAG, "subscribeObservers: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                        onLoginSuccess();
                        break;
                    }
                    case ERROR: {
                        showProgressBar(false);
                        Toast.makeText(AuthActivity.this, userAuthResource.message +
                                "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo(){
        this.requestManager
                .load(this.logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }
}
