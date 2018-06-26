package com.tikal.themovie.service.repository.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tikal.themovie.service.repository.storge.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemoviedbAPI {

    public static final Type MOVIE_ARRAY_LIST_CLASS_TYPE = (new ArrayList<Movie>()).getClass();
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular/";
    public static final String API_KEY = "api_key";
    public static final String LANGUAGE = "language";
    public static final String PAGE = "page";

    public static MoviesService createThemoviedbService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                // we remove from the response some wrapper tags from our RedditPosts array
                .registerTypeAdapter(MOVIE_ARRAY_LIST_CLASS_TYPE, new MoviesJsonDeserializer())
                .create();
        // https://api.themoviedb.org/3/movie/popular?api_key=xxx&language=en&page=pageNum
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASE_URL);

        return builder.build().create(MoviesService.class);
    }
}
