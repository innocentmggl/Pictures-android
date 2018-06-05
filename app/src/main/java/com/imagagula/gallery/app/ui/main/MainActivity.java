package com.imagagula.gallery.app.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.imagagula.gallery.app.BuildConfig;
import com.imagagula.gallery.app.R;
import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.ui.base.BaseActivity;
import com.imagagula.gallery.app.ui.collection.PlacesActivity;
import com.imagagula.gallery.app.utils.CommonUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, MainAdapter.Callback {

    @Inject
    MainGalleryPresenter<MainView> mPresenter;

    @Inject
    MainAdapter mainAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.grid_recycle_view)
    RecyclerView mRecyclerView;

    private FusedLocationProviderClient mFusedLocationClient;

    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mainAdapter.setCallback(this);

        mPresenter.onAttach(this);

        setup();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!hasLocationPermission()) {
            requestLocationPermissions();
        } else {
            getLastLocation();
        }
    }

    private void setup() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mainAdapter);
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            mLastLocation = task.getResult();
                            mLastLocation.getLatitude();
                            mLastLocation.getLongitude();

                            //call api
                            mPresenter.apiGetPhotos(mLastLocation);

                        } else {
                             showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }


    @Override
    public void renderPhotos(List<PictureResponse.Photo.Photos> photos) {
        mainAdapter.addItems(photos);
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void openPlacesActivity(String latitude, String longitude) {
        Intent intent = PlacesActivity.getStartIntent(MainActivity.this);
        intent.putExtra("latitude", latitude);
        intent.putExtra("latitude", longitude);

        startActivity(intent);
    }

    @Override
    public void onItemClicked(int position) {
        mPresenter.onItemClicked(position);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CommonUtils.PERMISSION_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted get location
                    getLastLocation();

                } else {
                    // permission denied show message
                    showSnackbar(R.string.permission_rationale, R.string.settings,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
                return;
            }
        }
    }
}
