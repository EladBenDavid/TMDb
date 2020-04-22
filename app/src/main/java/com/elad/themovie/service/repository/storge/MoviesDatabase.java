package com.elad.themovie.service.repository.storge;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.elad.themovie.service.repository.storge.model.Movie;
import com.elad.themovie.service.repository.storge.paging.DBMoviesDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.elad.themovie.Constants.DATA_BASE_NAME;
import static com.elad.themovie.Constants.NUMBERS_OF_THREADS;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;
    public abstract MovieDao movieDao();
    private static final Object sLock = new Object();
    private LiveData<PagedList<Movie>> moviesPaged;

    public static MoviesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBMoviesDataSourceFactory dataSourceFactory = new DBMoviesDataSourceFactory(movieDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return moviesPaged;
    }
}
