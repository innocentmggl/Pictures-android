package com.imagagula.gallery.app.ui.collection;

import com.imagagula.gallery.app.di.PerActivity;
import com.imagagula.gallery.app.ui.base.GalleryPresenter;

/**
 * Created by innoc on 2/26/2018.
 */
@PerActivity
public interface PlacesPresenter<V extends PlacesView> extends GalleryPresenter<V> {

    //pagination
    void getPlacesPhotos(String latitude, String longitude);

    void onItemClicked(final int position);
}
