package com.imagagula.gallery.app.di.module;

import android.app.Application;
import android.content.Context;


import com.imagagula.gallery.app.BuildConfig;
import com.imagagula.gallery.app.data.DataManager;
import com.imagagula.gallery.app.data.GalleryDataManager;
import com.imagagula.gallery.app.data.network.ApiHeader;
import com.imagagula.gallery.app.data.network.ApiHelper;
import com.imagagula.gallery.app.data.network.GalleryApiHelper;
import com.imagagula.gallery.app.di.ApiInfo;
import com.imagagula.gallery.app.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(GalleryDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(GalleryApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                BuildConfig.USER_ID,
                BuildConfig.SECRET);
    }


}
