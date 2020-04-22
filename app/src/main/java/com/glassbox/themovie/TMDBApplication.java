package com.glassbox.themovie;

import android.app.Application;
import android.util.Log;

import com.clarisite.mobile.Glassbox;
import com.clarisite.mobile.SessionCallback;
import com.clarisite.mobile.exceptions.GlassboxRecordingException;

import java.util.Map;

public class TMDBApplication extends Application {

    private static final String TAG = TMDBApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Glassbox.setSessionCallback(new SessionCallback() {
                @Override
                public void onSessionStarted(Map<String, Object> map) {
                    Log.d(TAG, String.format("onSessionStarted %s", map));
                }

                @Override
                public void onSessionFailed(Throwable throwable) {
                    Log.d(TAG, String.format("onSessionFailed %s", throwable));
                }

                @Override
                public void onSessionExcluded(String s, Map<String, Object> map) {
                    Log.d(TAG, String.format("onSessionExcluded %s, %s", s, map));
                }
            });
            Glassbox.start(this);
        } catch (GlassboxRecordingException e) {
            e.printStackTrace();
        }
    }
}
