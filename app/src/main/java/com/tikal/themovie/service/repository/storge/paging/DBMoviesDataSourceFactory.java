package com.tikal.themovie.service.repository.storge.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.tikal.themovie.service.repository.storge.MovieDao;
import com.tikal.themovie.service.repository.storge.model.Movie;

import rx.subjects.ReplaySubject;


public class DBMoviesDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBMoviesDataSourceFactory.class.getSimpleName();
    private DBMoviesPageKeyedDataSource moviesPageKeyedDataSource;
    public DBMoviesDataSourceFactory(MovieDao dao) {
        moviesPageKeyedDataSource = new DBMoviesPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return moviesPageKeyedDataSource;
    }

}
