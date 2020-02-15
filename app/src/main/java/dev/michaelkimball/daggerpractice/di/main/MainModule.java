package dev.michaelkimball.daggerpractice.di.main;

import dagger.Module;
import dagger.Provides;
import dev.michaelkimball.daggerpractice.network.main.MainApi;
import dev.michaelkimball.daggerpractice.ui.main.posts.PostsRecyclerAdapter;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @MainScope
    @Provides
    PostsRecyclerAdapter provideAdapter(){
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
