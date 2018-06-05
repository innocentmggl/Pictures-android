package com.imagagula.gallery.app.di.component;

import com.imagagula.gallery.app.ui.image.ImageActivity;
import com.imagagula.gallery.app.ui.main.MainActivity;
import com.imagagula.gallery.app.di.PerActivity;
import com.imagagula.gallery.app.di.module.ActivityModule;
import com.imagagula.gallery.app.ui.collection.PlacesActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(ImageActivity activity);

    void inject(PlacesActivity activity);

}
