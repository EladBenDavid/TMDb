package com.tikal.themovie.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tikal.themovie.service.repository.network.api.MoviesService;
import com.tikal.themovie.service.repository.network.api.ThemoviedbAPI;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.subjects.ReplaySubject;

/**
 * Created by Elad on 6/25/2018.
 */

public class NetMoviesPageKeyedDataSource extends PageKeyedDataSource<String, Movie> {

    public static final String TAG = NetMoviesPageKeyedDataSource.class.getSimpleName();
    private static final String API_KEY = "04f2f288263683f12131ae2ae1d348c6";
    private static final String LANGUAGE = "en";
    private final MoviesService moviesService;
    private final MutableLiveData networkState;
    private final ReplaySubject<Movie> moviesObservable;
    LoadInitialParams<String> initialParams;
    LoadParams<String> afterParams;

    private int pageCounter = 1;

    public NetMoviesPageKeyedDataSource() {
        moviesService = ThemoviedbAPI.createThemoviedbService();
        networkState = new MutableLiveData();
        moviesObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Movie> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        initialParams = params;

        networkState.postValue(NetworkState.LOADING);
        Call<ArrayList<Movie>> callBack = moviesService.getMovies(API_KEY, LANGUAGE,1);
        callBack.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(),Integer.toString(pageCounter++),Integer.toString(pageCounter++));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(moviesObservable::onNext);
                    initialParams = null;

                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();

                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(pageCounter),Integer.toString(pageCounter));
            }
        });
    }



    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Movie> callback) {
        Log.i(TAG, "Loading page " + params.key );
        afterParams = params;

        networkState.postValue(NetworkState.LOADING);
        int page = 0;
        try {
            page = Integer.parseInt(params.key);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        Call<ArrayList<Movie>> callBack = moviesService.getMovies(API_KEY, LANGUAGE,page);
        callBack.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(),Integer.toString(pageCounter++));
                    networkState.postValue(NetworkState.LOADED);
                    response.body().forEach(moviesObservable::onNext);
                    afterParams = null;
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                String errorMessage = t.getMessage();
                if (errorMessage == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(pageCounter));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Movie> callback) {

    }
}
