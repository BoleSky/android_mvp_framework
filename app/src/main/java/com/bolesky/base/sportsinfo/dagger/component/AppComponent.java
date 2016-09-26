package com.bolesky.base.sportsinfo.dagger.component;

import android.content.Context;

import com.bolesky.base.sportsinfo.api.ApiWrapper;
import com.bolesky.base.sportsinfo.dagger.module.ApiModule;
import com.bolesky.base.sportsinfo.dagger.module.AppModule;

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
}
