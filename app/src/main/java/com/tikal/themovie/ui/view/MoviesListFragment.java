package com.tikal.themovie.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tikal.themovie.R;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.ui.adapter.MoviesPageListAdapter;
import com.tikal.themovie.ui.listeners.ItemClickListener;
import com.tikal.themovie.ui.viewmodel.MovieDetailsViewModel;
import com.tikal.themovie.ui.viewmodel.MoviesListViewModel;

import java.io.IOException;

public class MoviesListFragment extends Fragment implements ItemClickListener {

    protected MoviesListViewModel viewModel;
    private MovieDetailsViewModel detailsViewModel;

    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        recyclerView = view.findViewById(R.id.moviesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(MoviesListViewModel.class);
        try {
            viewModel.initRepository(this.getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        observersRegisters();
        return view;
    }

    private void observersRegisters() {

        final MoviesPageListAdapter pageListAdapter = new MoviesPageListAdapter(this);
        viewModel.getMovies().observe(this, pageListAdapter::submitList);
        viewModel.getNetworkState().observe(this, networkState -> {
            pageListAdapter.setNetworkState(networkState);
        });
        recyclerView.setAdapter(pageListAdapter);
        detailsViewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
    }

    @Override
    public void OnItemClick(Movie movie) {
        detailsViewModel.getMovie().postValue(movie);
        if (!detailsViewModel.getMovie().hasActiveObservers()) {
            // Create fragment and give it an argument specifying the article it should show
            DetailsFragment detailsFragment = new DetailsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragmentsContainer, detailsFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }
}
