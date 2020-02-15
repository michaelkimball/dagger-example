package dev.michaelkimball.daggerpractice.di.auth;

import dagger.Module;
import dagger.Provides;
import dev.michaelkimball.daggerpractice.network.auth.AuthApi;
import retrofit2.Retrofit;

@Module
public class AuthModule {
    @AuthScope
    @Provides
    AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
