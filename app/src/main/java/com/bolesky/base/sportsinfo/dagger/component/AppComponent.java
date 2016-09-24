package com.bolesky.base.sportsinfo.dagger.component;

import android.content.Context;

import com.bolesky.base.sportsinfo.dagger.module.AppModule;

import dagger.Component;

/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */
@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
