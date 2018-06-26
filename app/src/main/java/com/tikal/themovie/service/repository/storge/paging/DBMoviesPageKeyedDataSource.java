package com.tikal.themovie.service.repository.storge.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tikal.themovie.service.repository.network.api.ThemoviedbAPI;
import com.tikal.themovie.service.repository.storge.MovieDao;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

/**
 * Created by Elad on 6/25/2018.
 */

public class DBMoviesPageKeyedDataSource extends PageKeyedDataSource<String, Movie> {

    public static final String TAG = DBMoviesPageKeyedDataSource.class.getSimpleName();
    private final MovieDao movieDao;
    public DBMoviesPageKeyedDataSource(MovieDao dao) {
        movieDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Movie> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Movie> movies = movieDao.getMovies();
        if(movies.size() != 0) {
            callback.onResult(movies, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Movie> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Movie> callback) {
    }
}
