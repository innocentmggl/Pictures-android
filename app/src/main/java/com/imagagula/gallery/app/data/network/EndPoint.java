package com.imagagula.gallery.app.data.network;

import com.imagagula.gallery.app.BuildConfig;

public final class EndPoint {

    public static final String ENDPOINT_GET_PICTURES = BuildConfig.BASE_URL
            + "/?method=flickr.photos.search&api_key=" +
            BuildConfig.API_KEY +
            "&accuracy=11&media=photos&" +
            "&extras=owner_name,url_sq,geo&format=json&has_geo=1&nojsoncallback=1&";
}
