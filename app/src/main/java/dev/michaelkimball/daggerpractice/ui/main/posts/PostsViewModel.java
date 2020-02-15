package dev.michaelkimball.daggerpractice.ui.main.posts;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import dev.michaelkimball.daggerpractice.SessionManager;
import dev.michaelkimball.daggerpractice.models.Post;
import dev.michaelkimball.daggerpractice.network.main.MainApi;
import dev.michaelkimball.daggerpractice.ui.main.Resource;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    private final SessionManager sessionManager;
    private final MainApi mainApi;

    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostsViewModel: viewmodel is working...");
    }

    public LiveData<Resource<List<Post>>> observePosts(){
        if(posts == null){
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>) null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                    .onErrorReturn((throwable) -> {
                        Log.e(TAG, "observePosts: error", throwable);
                        Post post = new Post();
                        post.setId(-1);
                        return Collections.singletonList(post);
                    })
                    .map((posts) -> {
                        if(posts.size() > 0){
                            if(posts.get(0).getId() == -1){
                                return Resource.error("Something went wrong", (List<Post>)null);
                            }
                        }
                        return Resource.success(posts);
                    })
                    .subscribeOn(Schedulers.io()));
            posts.addSource(source, listResource -> {
                posts.setValue(listResource);
                posts.removeSource(source);
            });
        }
        return posts;
    }
}
