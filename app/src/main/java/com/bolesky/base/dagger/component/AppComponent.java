package com.bolesky.base.dagger.component;

import android.content.Context;

import com.bolesky.base.api.ApiWrapper;
import com.bolesky.base.dagger.module.ApiModule;
import com.bolesky.base.dagger.module.AppModule;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    Context getContext();

    ApiWrapper getApiWrapper();

    DisplayImageOptions getDisplayImageOptions();

    ImageLoader getImageLoader();
}
