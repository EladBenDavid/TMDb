package com.elad.themovie.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.elad.themovie.service.repository.MoviesRepository;
import com.elad.themovie.service.repository.storge.model.Movie;
import com.elad.themovie.service.repository.storge.model.NetworkState;

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
