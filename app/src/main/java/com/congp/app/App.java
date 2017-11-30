package com.congp.app;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by congp on 11/30/2017.
 */

public class App extends Application {
    private static App sInstance;
    private Gson mGSon;

    public static App self() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mGSon = new Gson();
    }

    public Gson getGSon() {
        return mGSon;
    }
}
