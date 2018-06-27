package com.tikal.themovie.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.tikal.themovie.service.repository.storge.model.Movie;

import rx.subjects.ReplaySubject;

/*
    Responsible for creating the DataSource so we can give it to the PagedList.
 */
public class NetMoviesDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetMoviesDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetMoviesPageKeyedDataSource> networkStatus;
    private NetMoviesPageKeyedDataSource moviesPageKeyedDataSource;
    public NetMoviesDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        moviesPageKeyedDataSource = new NetMoviesPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;
    }

    public MutableLiveData<NetMoviesPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Movie> getMovies() {
        return moviesPageKeyedDataSource.getMovies();
    }

}
