package com.tikal.themovie.service.repository.storge;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.paging.DBMoviesDataSourceFactory;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * The Room database that contains the Users table
 */
@Database(entities = {Movie.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final String TAG = MoviesDatabase.class.getSimpleName();
    private static MoviesDatabase instance;
    public abstract MovieDao movieDao();
    private static final Object sLock = new Object();
    LiveData<PagedList<Movie>> moviesPaged;
    private WeakReference<Context> context;
    public static MoviesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, "TMBb.db")
                        .build();
                instance.init(context);

            }
            return instance;
        }
    }

    private void init(Context context) {
        instance.context = new WeakReference<>(context);
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(25).setPageSize(10).build();
        Executor executor = Executors.newFixedThreadPool(5);
        DBMoviesDataSourceFactory dataSourceFactory = new DBMoviesDataSourceFactory(movieDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }


    public void insertMovies(LiveData<PagedList<Movie>> moviesList) {
        moviesList.observeForever(
                movies -> {
                    Log.i(TAG, "insert movies size=" + movies.size());
                    movies.forEach(movie -> {
                                movieDao().insertMovie(movie);
                                Log.i(TAG, "insert movie id=" + movie.getId());
                            }

                    );
                });
    }

    public LiveData<PagedList<Movie>> getMovies() {
        return moviesPaged;
    }
}
