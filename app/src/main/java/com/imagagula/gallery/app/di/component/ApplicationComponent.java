package com.imagagula.gallery.app.di.component;

import android.app.Application;
import android.content.Context;

import com.imagagula.gallery.app.Gallery;
import com.imagagula.gallery.app.data.DataManager;
import com.imagagula.gallery.app.di.ApplicationContext;
import com.imagagula.gallery.app.di.module.ApplicationModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(Gallery app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}