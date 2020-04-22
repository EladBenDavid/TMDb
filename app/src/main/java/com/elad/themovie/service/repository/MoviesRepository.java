package com.elad.themovie.service.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.elad.themovie.service.repository.network.MoviesNetwork;
import com.elad.themovie.service.repository.network.paging.NetMoviesDataSourceFactory;
import com.elad.themovie.service.repository.storge.MoviesDatabase;
import com.elad.themovie.service.repository.storge.model.Movie;
import com.elad.themovie.service.repository.storge.model.NetworkState;

import rx.schedulers.Schedulers;

public class MoviesRepository {
    private static final String TAG = MoviesRepository.class.getSimpleName();
    private static MoviesRepository instance;
    final private MoviesNetwork network;
    final private MoviesDatabase database;
    final private MediatorLiveData liveDataMerger;

    private MoviesRepository(Context context) {

        NetMoviesDataSourceFactory dataSourceFactory = new NetMoviesDataSourceFactory();

        network = new MoviesNetwork(dataSourceFactory, boundaryCallback);
        database = MoviesDatabase.getInstance(context.getApplicationContext());
        // when we get new movies from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedMovies(), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });

        // save the movies into db
        dataSourceFactory.getMovies().
                observeOn(Schedulers.io()).
                subscribe(movie -> {
                    database.movieDao().insertMovie(movie);
                });

    }

    private PagedList.BoundaryCallback<Movie> boundaryCallback = new PagedList.BoundaryCallback<Movie>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getMovies(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getMovies());
            });
        }
    };
    public static MoviesRepository getInstance(Context context){
        if(instance == null){
            instance = new MoviesRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Movie>> getMovies(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }
}
