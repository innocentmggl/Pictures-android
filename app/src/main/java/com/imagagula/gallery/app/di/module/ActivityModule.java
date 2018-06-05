package com.imagagula.gallery.app.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;


import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.di.ActivityContext;
import com.imagagula.gallery.app.di.PerActivity;
import com.imagagula.gallery.app.rx.GallerySchedulerProvider;
import com.imagagula.gallery.app.rx.SchedulerProvider;
import com.imagagula.gallery.app.ui.main.MainAdapter;
import com.imagagula.gallery.app.ui.main.MainGalleryPresenter;
import com.imagagula.gallery.app.ui.main.MainPresenter;
import com.imagagula.gallery.app.ui.main.MainView;
import com.imagagula.gallery.app.ui.collection.PlacesAdapter;
import com.imagagula.gallery.app.ui.collection.PlacesGalleryPresenter;
import com.imagagula.gallery.app.ui.collection.PlacesPresenter;
import com.imagagula.gallery.app.ui.collection.PlacesView;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new GallerySchedulerProvider();
    }

    @Provides
    MainAdapter provideMainAdapter() {
        return new MainAdapter(new ArrayList<PictureResponse.Photo.Photos>());
    }

    @Provides
    PlacesAdapter providePlacesAdapter() {
        return new PlacesAdapter(new ArrayList<PictureResponse.Photo.Photos>());
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(AppCompatActivity activity) {
        return  new GridLayoutManager(activity, 2);
    }

    @Provides
    @PerActivity
    MainPresenter<MainView> provideMainPresenter(MainGalleryPresenter<MainView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PlacesPresenter<PlacesView> providePlacesPresenter(PlacesGalleryPresenter<PlacesView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return  new LinearLayoutManager(activity);
    }

}
