package com.tikal.themovie.service.repository.network.api;

import com.tikal.themovie.service.repository.storge.model.Movie;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static com.tikal.themovie.service.repository.network.api.ThemoviedbAPI.API_KEY;
import static com.tikal.themovie.service.repository.network.api.ThemoviedbAPI.LANGUAGE;
import static com.tikal.themovie.service.repository.network.api.ThemoviedbAPI.PAGE;
public interface MoviesService {



    @GET(".")
    Call<ArrayList<Movie>> getMovies(@Query(API_KEY) String apiKey,
                                           @Query(LANGUAGE) String language, @Query(PAGE) int page);
}
