package com.tikal.themovie.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.tikal.themovie.service.repository.storge.model.Movie;

import org.parceler.Repository;

public class MovieDetailsViewModel extends ViewModel {
    final private MutableLiveData movie;

    public MovieDetailsViewModel() {
        movie = new MutableLiveData<Movie>();
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }
}
