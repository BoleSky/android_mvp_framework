package com.bolesky.base.sportsinfo.api;

import com.bolesky.base.sportsinfo.SportsApplication;
import com.bolesky.base.sportsinfo.utils.NetUtils;

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

    public static Interceptor getInterceptorCache() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isNetworkReachable(SportsApplication.getInstance())) {
                    request = request.newBuilder()
                            .addHeader(Api.HEADER_Client_Type, Api.FROM_ANDROID)
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtils.isNetworkReachable(SportsApplication.getInstance())) {
                    int maxAge = 60 * 60 * 10;
                    // 有网络时 设置缓存超时时间10分钟
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        return interceptor;
    }
}