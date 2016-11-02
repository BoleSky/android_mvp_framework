package com.bolesky.base;

import android.app.Application;

import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.dagger.component.DaggerAppComponent;
import com.bolesky.base.dagger.module.AppModule;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

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
        //通过配置方案来初始化ImageLoader
        ImageLoader.getInstance().init(getSimpleConfig());
    }


    /**
     * ImageLoader比较常用的配置方案
     *
     * @return ImageLoaderConfiguration
     */
    private ImageLoaderConfiguration getSimpleConfig() {
        //设置缓存的路径
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) //即保存的每个缓存文件的最大长宽
                .threadPriority(Thread.NORM_PRIORITY - 2) //线程池中线程的个数
                .denyCacheImageMultipleSizesInMemory() //禁止缓存多张图片
                .memoryCache(new LRULimitedMemoryCache(25 * 1024 * 1024)) //缓存策略
                .memoryCacheSize(50 * 1024 * 1024) //设置内存缓存的大小
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //缓存文件名的保存方式
                .diskCacheSize(200 * 1024 * 1024) //磁盘缓存大小
                .tasksProcessingOrder(QueueProcessingType.LIFO) //工作队列
                .diskCacheFileCount(100) //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir)) //自定义缓存路径
                //.writeDebugLogs() // Remove for release app
                .build();
        return config;
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
