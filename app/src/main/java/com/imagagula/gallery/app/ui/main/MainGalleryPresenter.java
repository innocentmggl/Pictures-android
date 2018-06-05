package com.imagagula.gallery.app.ui.main;

import android.location.Location;

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


public class MainGalleryPresenter<V extends MainView> extends BasePresenter<V>
        implements MainPresenter<V> {

    private PictureResponse response;

    @Inject
    public MainGalleryPresenter(DataManager dataManager,
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


    /**
     * Get user location and connect to server
     * @param location
     */
    @Override
    public void apiGetPhotos(Location location) {

        String params = "accuracy=" +
                CommonUtils.PHOTO_SEARCH_ACCURACY +
                "&radius=" +
                CommonUtils.PHOTO_SEARCH_RADIUS +
                "&radius_units=" +
                CommonUtils.PHOTO_SEARCH_RADIUS_UNITS +
                "&lat=" +
                location.getLatitude() +
                "&lot=" +
                location.getLongitude()+
                "&per_page=" +
                CommonUtils.PHOTOS_PER_PAGE +
                "&page=1";

        //call flckr api
        getPhotos(params);
    }



    private void getPhotos(String params) {

            //check network and connect
            if (getBaseGalleryView().isNetworkConnected()) {
                doGetPhotosApiCall(params);
            }
            //no connection
            else {
                getBaseGalleryView().onError("No internet");
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
                getBaseGalleryView().onError("Server error!");
        }
    }

    @Override
    public void onItemClicked(int position) {
        PictureResponse.Photo.Photos photo = response.getPhoto().getData().get(position);
        getBaseGalleryView().openPlacesActivity(photo.getLatitude(),photo.getLongitude());
    }
}
