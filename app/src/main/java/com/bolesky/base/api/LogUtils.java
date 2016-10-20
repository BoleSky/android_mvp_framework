package com.bolesky.base.api;

import android.util.Log;


/**
 * Created by xiaoyong.cui
 * on 2016/10/20
 * E-Mail:hellocui@aliyun.com
 */

public class LogUtils implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.d("OKHTTP:", message);
    }
}
