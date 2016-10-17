package com.bolesky.base;

import android.app.Application;

import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.dagger.component.DaggerAppComponent;
import com.bolesky.base.dagger.module.AppModule;

/**
 * Created by xiaoyong.cui
 * on 2016/9/24
 * E-Mail:hellocui@aliyun.com
 */

public class App extends Application {
    private static App mInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
        initComponent();
    }

    public static App getInstance() {
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
