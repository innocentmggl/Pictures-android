package com.imagagula.gallery.app.ui.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.imagagula.gallery.app.R;
import com.imagagula.gallery.app.ui.base.BaseActivity;
import com.imagagula.gallery.app.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    @BindView(R.id.photo)
    ImageView photo;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ImageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        setup();

    }

    private void setup(){

        Intent intent = getIntent();
        String url = intent.getStringExtra(CommonUtils.FULL_SCREEN_ACTIVITY_URL);
        String title = intent.getStringExtra(CommonUtils.FULL_SCREEN_ACTIVITY_URL);
        if(title != null){
            setTitle(title);
        }
        renderImage(url);
    }

    private void renderImage(String url){

        try {
            if (url != null) {
                Glide.with(this)
                        .load(url)
                        .asBitmap()
                        .centerCrop()
                        .into(photo);
            }
            else{
                onError(getString(R.string.invalid_url));
                finish();
            }
        }

        catch (Exception e){
            onError(getString(R.string.api_default_error));
            finish();
        }
    }
}
