package dev.michaelkimball.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerFragment;
import dev.michaelkimball.daggerpractice.R;
import dev.michaelkimball.daggerpractice.models.User;
import dev.michaelkimball.daggerpractice.viewmodels.ViewModelProviderFactory;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;
    private TextView email, username, website;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created...");
        this.email = view.findViewById(R.id.email);
        this.username = view.findViewById(R.id.username);
        this.website = view.findViewById(R.id.website);
        this.viewModel = new ViewModelProvider(this, this.providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    public void subscribeObservers(){
        this.viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        this.viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if(userAuthResource == null){
                return;
            }
            switch (userAuthResource.status){
                case AUTHENTICATED:{
                    setUserDetails(userAuthResource.data);
                    break;
                }
                case ERROR: {
                    setErrorDetails(userAuthResource.message);
                    break;
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        this.email.setText(getResources().getString(R.string.error));
        this.username.setText(message);
        this.website.setText("");
    }

    private void setUserDetails(User data) {
        this.email.setText(data.getEmail());
        this.username.setText(data.getUsername());
        this.website.setText(data.getWebsite());
    }
}
