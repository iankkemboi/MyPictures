package com.ian.payback.image;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;


@HiltAndroidApp
public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}