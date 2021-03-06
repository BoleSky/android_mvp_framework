package com.bolesky.base.api;

import android.util.Log;

import com.bolesky.base.App;
import com.bolesky.base.utils.BaseUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiaoyong.cui
 * on 2016/9/27
 * E-Mail:hellocui@aliyun.com
 */

public class CacheInterceptor {
    protected static String TAG = "CacheInterceptor";

    public static Interceptor getInterceptorCache() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!BaseUtils.isNetworkConnected(App.getInstance())) {
                    request = request.newBuilder()
                            .addHeader(Api.HEADER_Client_Type, Api.FROM_ANDROID)
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    Log.d(TAG, "------>无网络 设置CacheControl.FORCE_CACHE");
                }
                Response response = chain.proceed(request);
                if (BaseUtils.isNetworkConnected(App.getInstance())) {
                    int maxAge = 60 * 60 * 10;
                    // 有网络时 设置缓存超时时间10分钟
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                    Log.d(TAG, "------>有网络时 设置缓存超时时间10分钟");
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                    Log.d(TAG, "------>无网络时，设置超时为1周");
                }
                return response;
            }
        };
        return interceptor;
    }
}