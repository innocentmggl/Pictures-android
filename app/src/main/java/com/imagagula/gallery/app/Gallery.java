package com.imagagula.gallery.app;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.imagagula.gallery.app.di.component.ApplicationComponent;
import com.imagagula.gallery.app.di.component.DaggerApplicationComponent;
import com.imagagula.gallery.app.di.module.ApplicationModule;

public class Gallery extends Application {

    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);


        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
