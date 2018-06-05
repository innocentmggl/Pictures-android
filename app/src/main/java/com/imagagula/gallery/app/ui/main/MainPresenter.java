package com.imagagula.gallery.app.ui.main;

import android.location.Location;

import com.imagagula.gallery.app.di.PerActivity;
import com.imagagula.gallery.app.ui.base.GalleryPresenter;

/**
 * Created by innoc on 2/26/2018.
 */
@PerActivity
public interface MainPresenter<V extends MainView> extends GalleryPresenter<V> {

    //pagination
    void apiGetPhotos(Location location);

    void onItemClicked(final int position);
}
