package com.tikal.themovie.service.repository.network;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.tikal.themovie.service.repository.network.paging.NetMoviesDataSourceFactory;
import com.tikal.themovie.service.repository.network.paging.NetMoviesPageKeyedDataSource;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.tikal.themovie.Constants.LOADING_PAGE_SIZE;
import static com.tikal.themovie.Constants.NUMBERS_OF_THREADS;

/**
 * Created by Elad on 6/25/2018.
 */

public class MoviesNetwork {

    final private static String TAG = MoviesNetwork.class.getSimpleName();
    final private LiveData<PagedList<Movie>> moviesPaged;
    final private LiveData<NetworkState> networkState;

    public MoviesNetwork(NetMoviesDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetMoviesPageKeyedDataSource, LiveData<NetworkState>>)
                        NetMoviesPageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Movie>> getPagedMovies(){
        return moviesPaged;
    }



    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
