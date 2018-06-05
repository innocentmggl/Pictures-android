package com.imagagula.gallery.app.data;

import android.content.Context;

import com.imagagula.gallery.app.data.network.ApiHeader;
import com.imagagula.gallery.app.data.network.ApiHelper;
import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class GalleryDataManager implements DataManager {

    private static final String TAG = "GalleryDataManager";

    private final Context mContext;
    private final ApiHelper mApiHelper;

    @Inject
    public GalleryDataManager(@ApplicationContext Context context,
                           ApiHelper apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<PictureResponse> dogetPicturesApiCall(String param) {
        return mApiHelper.dogetPicturesApiCall(param);
    }
}
