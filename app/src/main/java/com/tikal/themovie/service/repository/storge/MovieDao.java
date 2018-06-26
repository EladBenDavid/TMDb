package com.tikal.themovie.service.repository.storge;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tikal.themovie.service.repository.storge.model.Movie;

import java.util.List;

/**
 * Data Access Object for the movies table.
 */
@Dao
public interface MovieDao {

    /**
     * Get the Movies from the table.
     *
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
