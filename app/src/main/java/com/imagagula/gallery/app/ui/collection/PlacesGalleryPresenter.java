package com.imagagula.gallery.app.ui.collection;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.imagagula.gallery.app.data.DataManager;
import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.rx.SchedulerProvider;
import com.imagagula.gallery.app.ui.base.BasePresenter;
import com.imagagula.gallery.app.utils.CommonUtils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class PlacesGalleryPresenter<V extends PlacesView> extends BasePresenter<V>
        implements PlacesPresenter<V> {

    private PictureResponse response;

    @Inject
    public PlacesGalleryPresenter(DataManager dataManager,
                                  SchedulerProvider schedulerProvider,
                                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    /**
     * Runtime setup
     *
     * @param mainView
     */
    @Override
    public void onAttach(V mainView) {

        super.onAttach(mainView);

    }


    @Override
    public void getPlacesPhotos(String latitude, String longitude) {

        String params = "accuracy=" +
                CommonUtils.PLACES_SEARCH_ACCURACY +
                "&radius=" +
                CommonUtils.PLACES_SEARCH_RADIUS +
                "&radius_units=" +
                CommonUtils.PHOTO_SEARCH_RADIUS_UNITS +
                "&lat=" +
                latitude +
                "&lot=" +
                longitude +
                "&per_page=" +
                CommonUtils.PHOTOS_PER_PAGE +
                "&page=1";
        //call flckr api
        getPhotos(params);
    }



    private void getPhotos(String params) {
        //check network and connect
        if (getBaseGalleryView().isNetworkConnected()){

            doGetPhotosApiCall(params);
        }
        //no connection
        else{
            getBaseGalleryView().showSnackbar("No network connection");
        }

    }


    private void doGetPhotosApiCall(String params){

        getBaseGalleryView().showLoading();
        getCompositeDisposable().add(getDataManager().dogetPicturesApiCall(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PictureResponse>() {

                    @Override
                    public void accept(@NonNull PictureResponse response)
                            throws Exception {
                        //hide loading animation
                        getBaseGalleryView().hideLoading();
                        //check for valid response
                        if (response != null) {
                            //successful
                            renderPhotos(response);
                        }
                        //empty response
                        else {
                            getBaseGalleryView().showSnackbar("Error occurred!");
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        //if view is attached
                        if (!isViewAttached()) {
                            return;
                        }
                        //hide loading animation
                        getBaseGalleryView().hideLoading();
                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));

    }

    //set pictures
    private void renderPhotos(PictureResponse response) {
        //set global variable
        this.response = response;

        //success response
        if(response.getStatusCode().equals("ok")){
            //set returned photo
            getBaseGalleryView().renderPhotos(response.getPhoto().getData());
        }
        else{
            if(response.getMessage() != null)
               getBaseGalleryView().onError(response.getMessage());
            else
                getBaseGalleryView().showSnackbar("Server error!");
        }
    }

    @Override
    public void onItemClicked(int position) {
        PictureResponse.Photo.Photos photo = response.getPhoto().getData().get(position);
        getBaseGalleryView().openFullScreenImageActivity(photo.getUrl(), photo.getTitle());
    }
}
