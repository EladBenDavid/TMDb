package com.tikal.themovie.service.repository.storge.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


/**
 * Immutable model class for a Movie and entity in the Room database.
 */


@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey()
    @ColumnInfo(name = "id") @SerializedName(value="id") private Integer mId;
    @ColumnInfo(name = "vote_count") @SerializedName(value="vote_count") private Integer mVoteCount;
    @ColumnInfo(name = "video") @SerializedName(value="video") private Boolean mVideo;
    @ColumnInfo(name = "vote_average") @SerializedName(value="vote_average") private Float mVoteAverage;
    @ColumnInfo(name = "title") @SerializedName(value="title") private String mTitle;
    @ColumnInfo(name = "popularity") @SerializedName(value="popularity") private Float mPopularity;
    @ColumnInfo(name = "poster_path") @SerializedName(value="poster_path") private String mPosterPath;
    @ColumnInfo(name = "original_language") @SerializedName(value="original_language") private String mOriginalLanguage;
    @ColumnInfo(name = "original_title") @SerializedName(value="original_title") private String mOriginalTitle;
    @ColumnInfo(name = "backdrop_path") @SerializedName(value="backdrop_path") private String mBackdropPath;
    @ColumnInfo(name = "adult") @SerializedName(value="adult") private Boolean mAdult;
    @ColumnInfo(name = "overview") @SerializedName(value="overview") private String mOverview;
    @ColumnInfo(name = "release_date") @SerializedName(value="release_date") private String mReleaseDate;


    // use for ordering the items in view
    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }



    public Boolean getVideo() {
        return mVideo;
    }
    public void setVideo(Boolean mVideo) {
        this.mVideo = mVideo;
    }
    public Float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Float mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setAdult(Boolean mAdult) {
        this.mAdult = mAdult;
    }

    public Boolean getAdult() {
        return mAdult;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
