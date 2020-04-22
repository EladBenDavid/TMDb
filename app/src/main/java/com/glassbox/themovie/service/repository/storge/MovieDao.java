package com.glassbox.themovie.service.repository.storge;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.glassbox.themovie.service.repository.storge.model.Movie;

import java.util.List;

/**
 * Data Access Object for the movies table.
 */
@Dao
public interface MovieDao {

    /**
     * Get the Movies from the table.
     * -------------------------------
     * Since the DB use as caching, we don't return LiveData.
     * We don't need to get update every time the database update.
     * We using the get query when application start. So, we able to display
     * data fast and in case we don't have connection to work offline.
     * @return the movies from the table
     */
    @Query("SELECT * FROM movies")
    List<Movie> getMovies();

    /**
     * Insert a movie in the database. If the movie already exists, replace it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movies")
    abstract void deleteAllMovies();
}
