package com.imagagula.gallery.app.ui.collection;

import android.app.Activity;

import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.ui.base.GalleryView;

import java.util.List;


public interface PlacesView extends GalleryView {

    //set adapter
    void renderPhotos(List<PictureResponse.Photo.Photos> photos);

    //context
    Activity getViewActivity();

    //open location pictures
    void openFullScreenImageActivity(String photoUrl, String caption);


}
