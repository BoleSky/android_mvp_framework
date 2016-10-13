package com.bolesky.base.dagger.module;

import android.content.Context;

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
}
