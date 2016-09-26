package com.bolesky.base.sportsinfo.api;

import com.bolesky.base.sportsinfo.bean.HttpException;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

public interface CallBack<T> {
    void onCompleted();
    void onError(HttpException mHttpExceptionBean);
    void onNext(T t);
}
