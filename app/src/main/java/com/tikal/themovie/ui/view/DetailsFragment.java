package com.tikal.themovie.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tikal.themovie.R;
import com.tikal.themovie.databinding.FragmentDetailsBinding;
import com.tikal.themovie.ui.adapter.MoviesPageListAdapter;
import com.tikal.themovie.ui.listeners.ItemClickListener;
import com.tikal.themovie.ui.viewmodel.MovieDetailsViewModel;
import com.tikal.themovie.ui.viewmodel.MoviesListViewModel;

import static com.tikal.themovie.ui.view.MovieViewHolder.IMAGE_URL_PREFIX;

public class DetailsFragment extends Fragment {

    private MovieDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        FragmentDetailsBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_details);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
        viewModel.getMovie().observe(this, movie -> binding.setMovie(movie) );
        View view = binding.getRoot();
        return view;
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(IMAGE_URL_PREFIX + url).into(view);
    }
}
