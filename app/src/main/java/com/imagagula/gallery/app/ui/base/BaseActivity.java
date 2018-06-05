package com.imagagula.gallery.app.ui.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.imagagula.gallery.app.Gallery;
import com.imagagula.gallery.app.R;
import com.imagagula.gallery.app.di.component.ActivityComponent;
import com.imagagula.gallery.app.di.component.DaggerActivityComponent;
import com.imagagula.gallery.app.di.module.ActivityModule;
import com.imagagula.gallery.app.utils.CommonUtils;

import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity
        implements GalleryView {

    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((Gallery) getApplication()).getComponent())
                .build();

    }
    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showToast(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }
    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
    @Override
    public void onError(String message) {
        if (message != null) {
            showToast(message);
        } else {
            showToast(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(int message) {
        if (message != 0) {
            showToast(getString(message));
        } else {
            showToast(getString(R.string.some_error));
        }
    }

    protected void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }




    @Override
    public boolean isNetworkConnected() {
        return CommonUtils.isNetworkConnected(getApplicationContext());
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestLocationPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                 showSnackbar(R.string.permission_rationale, android.R.string.ok,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Request permission
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        CommonUtils.PERMISSION_REQUEST_LOCATION);

                            }
                        });
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, CommonUtils.PERMISSION_REQUEST_LOCATION);
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasLocationPermission() {
      return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
