package com.tikal.themovie.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.content.Context;

import com.tikal.themovie.service.repository.MoviesRepository;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;

import java.io.IOException;

public class MoviesListViewModel extends ViewModel {
    private MoviesRepository repository;

    public void initRepository(Context context) throws IOException {
        repository = MoviesRepository.getInstance(context.getApplicationContext());
    }
    public LiveData<PagedList<Movie>> getMovies() {
        return repository.getMovies();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}
