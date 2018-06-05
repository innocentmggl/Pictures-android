package com.imagagula.gallery.app.ui.base;

import com.androidnetworking.error.ANError;

public interface GalleryPresenter <V extends GalleryView>{

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);
}
