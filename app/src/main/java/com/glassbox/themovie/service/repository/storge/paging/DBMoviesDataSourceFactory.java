package com.glassbox.themovie.service.repository.storge.paging;




import androidx.paging.DataSource;

import com.glassbox.themovie.service.repository.storge.MovieDao;


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
