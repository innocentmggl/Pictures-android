package com.imagagula.gallery.app.ui.collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.imagagula.gallery.app.R;
import com.imagagula.gallery.app.data.network.model.PictureResponse;
import com.imagagula.gallery.app.ui.base.BaseActivity;
import com.imagagula.gallery.app.ui.image.ImageActivity;
import com.imagagula.gallery.app.utils.CommonUtils;
import com.imagagula.gallery.app.utils.GridSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesActivity extends BaseActivity implements PlacesView, PlacesAdapter.Callback {


    @Inject
    PlacesGalleryPresenter<PlacesView> mPresenter;

    @Inject
    PlacesAdapter placesAdapter;

    @Inject
    GridLayoutManager mLayoutManager;

    @BindView(R.id.grid_recycle_view)
    RecyclerView mRecyclerView;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PlacesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        placesAdapter.setCallback(this);

        mPresenter.onAttach(this);

        setup();

        getIntentExtras();

    }

    private void getIntentExtras(){

        Intent intent = getIntent();
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        mPresenter.getPlacesPhotos(latitude,longitude);
    }

    private void setup(){

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, CommonUtils.dpToPx(this,5), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(placesAdapter);
    }



    @Override
    public void renderPhotos(List<PictureResponse.Photo.Photos> photos) {
        placesAdapter.addItems(photos);
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void openFullScreenImageActivity(String photoUrl, String caption) {
        Intent intent = ImageActivity.getStartIntent(PlacesActivity.this);
        intent.putExtra(CommonUtils.FULL_SCREEN_ACTIVITY_URL, photoUrl);
        intent.putExtra(CommonUtils.FULL_SCREEN_ACTIVITY_CAPTION, caption);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(int position) {
        mPresenter.onItemClicked(position);
    }

}
