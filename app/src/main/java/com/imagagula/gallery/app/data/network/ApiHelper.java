package com.imagagula.gallery.app.data.network;

import com.imagagula.gallery.app.data.network.model.PictureResponse;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<PictureResponse> dogetPicturesApiCall(String params);

}