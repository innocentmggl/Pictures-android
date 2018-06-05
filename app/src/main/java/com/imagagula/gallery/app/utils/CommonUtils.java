package com.imagagula.gallery.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import com.imagagula.gallery.app.R;


public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;


    public static final int PHOTO_SEARCH_ACCURACY = 11;
    public static final int PLACES_SEARCH_ACCURACY = 16;
    public static final int PHOTO_SEARCH_RADIUS = 5;
    public static final int PLACES_SEARCH_RADIUS = 2;
    public static final String PHOTO_SEARCH_RADIUS_UNITS = "mi";
    public static final int PHOTOS_PER_PAGE = 10;
    public static final int PERMISSION_REQUEST_LOCATION = 99;
    public static final String FULL_SCREEN_ACTIVITY_URL = "photo_url";
    public static final String FULL_SCREEN_ACTIVITY_CAPTION = "caption";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static ProgressDialog showLoadingDialog(Context context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.adapter_progress_bar);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    /**
     * Converting dp to pixel
     */
    public static int dpToPx(Context context,int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
