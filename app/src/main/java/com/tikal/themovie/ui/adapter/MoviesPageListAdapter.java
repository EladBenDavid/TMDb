package com.tikal.themovie.ui.adapter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tikal.themovie.R;
import com.tikal.themovie.service.repository.storge.model.Movie;
import com.tikal.themovie.service.repository.storge.model.NetworkState;
import com.tikal.themovie.ui.listeners.ItemClickListener;
import com.tikal.themovie.ui.view.MovieViewHolder;
import com.tikal.themovie.ui.view.NetworkStateItemViewHolder;

/**
 * Created by Elad on 6/25/2018.
 */

public class MoviesPageListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    private static final String TAG = MoviesPageListAdapter.class.getSimpleName();
    private NetworkState networkState;
    private ItemClickListener itemClickListener;

    public MoviesPageListAdapter(ItemClickListener itemClickListener) {
        super(Movie.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.movie_item) {
            View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
            MovieViewHolder viewHolder = new MovieViewHolder(view, itemClickListener);;
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            View view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.movie_item:
                ((MovieViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.movie_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<Movie> currentList) {
        super.onCurrentListChanged(currentList);
        if(currentList != null && currentList.size() > 0) {
            //itemClickListener.OnItemClick(currentList.get(0));
        }
    }


}
