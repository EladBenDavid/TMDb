package com.tikal.themovie.service.repository.network.api;

import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tikal.themovie.Constants;
import com.tikal.themovie.service.repository.storge.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Elad on 6/25/2018.
 */

class MoviesJsonDeserializer implements JsonDeserializer {

    private static String TAG = MoviesJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<Movie> movies = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray moviesJsonArray = jsonObject.getAsJsonArray(Constants.MOVIES_ARRAY_DATA_TAG);
            movies = new ArrayList<>(moviesJsonArray.size());
            for (int i = 0; i < moviesJsonArray.size(); i++) {
                // adding the converted wrapper to our container
                Movie dematerialized = context.deserialize(moviesJsonArray.get(i), Movie.class);
                movies.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize Movie element: %s", json.toString()));
        }
        return movies;
    }
}
