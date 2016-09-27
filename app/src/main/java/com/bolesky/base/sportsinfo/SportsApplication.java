package com.bolesky.base.sportsinfo;

import android.app.Application;

import com.bolesky.base.sportsinfo.dagger.component.AppComponent;
import com.bolesky.base.sportsinfo.dagger.component.DaggerAppComponent;
import com.bolesky.base.sportsinfo.dagger.module.AppModule;

/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */

public class SportsApplication extends Application {
    private static SportsApplication mInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
        initComponent();
    }

    public static SportsApplication getInstance() {
        return mInstance;
    }

    /**
     * 初始化全局appComponent
     */
    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 对外提供调用方法获取AppComponent
     *
     * @return AppComponent
     */
    public AppComponent getAppComponent() {
        return appComponent;
    }
}