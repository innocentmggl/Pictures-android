package com.imagagula.gallery.app.ui.base;

import android.support.annotation.StringRes;

public interface GalleryView {

    void showLoading();

    void hideLoading();

    void onError(String message);

    void onError(@StringRes int message);

    boolean isNetworkConnected();

    void showSnackbar(final String text);
}
