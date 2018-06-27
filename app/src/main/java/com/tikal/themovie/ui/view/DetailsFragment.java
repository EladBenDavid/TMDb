package com.tikal.themovie.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tikal.themovie.R;
import com.tikal.themovie.databinding.FragmentDetailsBinding;
import com.tikal.themovie.ui.viewmodel.MovieDetailsViewModel;

public class DetailsFragment extends Fragment {
    public static final String BIG_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w500";
    private MovieDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);
        View view = binding.getRoot();
        viewModel.getMovie().observe(this, movie -> binding.setMovie(movie) );
        return view;
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        if(url != null) {
            Glide.with(view.getContext()).load(BIG_IMAGE_URL_PREFIX + url).into(view);
        }
    }
}
