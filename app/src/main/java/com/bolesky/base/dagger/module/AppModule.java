package com.bolesky.base.dagger.module;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public DisplayImageOptions provideDisplayImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return displayImageOptions;
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        return imageLoader;
    }
}
