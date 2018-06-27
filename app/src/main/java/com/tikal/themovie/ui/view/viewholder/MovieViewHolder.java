package com.tikal.themovie.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tikal.themovie.R;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.ui.listeners.ItemClickListener;

import static com.tikal.themovie.Constants.SMALL_IMAGE_URL_PREFIX;


public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private Movie movie;
    private TextView titleTextView;
    private TextView userRatingTextView;
    private ImageView thumbnailImageView;
    private ItemClickListener itemClickListener;

    public MovieViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = view.findViewById(R.id.title);
        this.userRatingTextView = view.findViewById(R.id.userrating);
        this.thumbnailImageView = view.findViewById(R.id.thumbnail);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(Movie movie) {
        this.movie = movie;
        titleTextView.setText(movie.getTitle());
        userRatingTextView.setText(String.format("%1$,.2f", movie.getVoteAverage()));
        if(movie.getPosterPath() != null) {
            String poster = SMALL_IMAGE_URL_PREFIX + movie.getPosterPath();
            Picasso.get().load(poster).into(thumbnailImageView);
        }
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(movie); // call the onClick in the OnItemClickListener
        }
    }

}
