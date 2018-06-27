package com.tikal.themovie.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tikal.themovie.R;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.ui.listeners.ItemClickListener;


public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w500";
    private Movie movie;
    private TextView titleTextView;
    private TextView userratingTextView;
    private ImageView thumbnailImageView;
    private ItemClickListener itemClickListener;

    public MovieViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.userratingTextView = view.findViewById(R.id.userrating);
        this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(Movie movie) {
        this.movie = movie;
        titleTextView.setText(movie.getTitle());
        userratingTextView.setText(Double.toString(movie.getVoteAverage()));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        String poster = IMAGE_URL_PREFIX + movie.getPosterPath();
        Glide.with(itemView.getContext())
                .load(poster)
                .apply(requestOptions)
                .into(thumbnailImageView);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(movie); // call the onClick in the OnItemClickListener
        }
    }
}
