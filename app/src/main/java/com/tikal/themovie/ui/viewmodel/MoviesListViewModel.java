package com.tikal.themovie.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tikal.themovie.service.repository.MoviesRepository;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;

import java.io.IOException;

public class MoviesListViewModel extends AndroidViewModel {
    private MoviesRepository repository;

    public MoviesListViewModel(@NonNull Application application) {
        super(application);
        repository = MoviesRepository.getInstance(application);
    }
    public LiveData<PagedList<Movie>> getMovies() {
        return repository.getMovies();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}
