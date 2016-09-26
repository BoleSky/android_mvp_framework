package com.bolesky.base.sportsinfo.dagger.module;


import com.bolesky.base.sportsinfo.api.ApiWrapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiaoyong.cuiY
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public ApiWrapper provideApiWrapper() {
        return new ApiWrapper();
    }
}
