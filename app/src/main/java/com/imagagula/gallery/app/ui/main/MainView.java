package com.imagagula.gallery.app.ui.main;

import android.app.Activity;

import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.ui.base.GalleryView;

import java.util.List;


public interface MainView extends GalleryView {

    //set adapter
    void renderPhotos(List<PictureResponse.Photo.Photos> photos);

    //context
    Activity getViewActivity();

    //open location pictures
    void openPlacesActivity(String latitude,String longitude);


}
