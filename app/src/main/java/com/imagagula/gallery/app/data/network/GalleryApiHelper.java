package com.imagagula.gallery.app.data.network;

import android.util.Log;

import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class GalleryApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public GalleryApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Observable<PictureResponse> dogetPicturesApiCall(String params) {
        Log.e("Connection URL",EndPoint.ENDPOINT_GET_PICTURES+params);
        return Rx2AndroidNetworking.get(EndPoint.ENDPOINT_GET_PICTURES+params)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(PictureResponse.class);
    }
}
