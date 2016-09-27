package com.bolesky.base.sportsinfo.api;

import com.bolesky.base.sportsinfo.bean.HttpExceptionBean;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

public interface CallBack<T> {
    void onCompleted();

    void onError(HttpExceptionBean httpExceptionBeanBean);

    void onNext(T t);
}
