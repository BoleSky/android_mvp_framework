package com.bolesky.base.sportsinfo.api;

import com.bolesky.base.sportsinfo.bean.HttpExceptionBean;

/**
 * Created by xiaoyong.cui
 * on 2016/9/27
 * E-Mail:hellocui@aliyun.com
 */

public abstract class CallBackImp<T> implements CallBack<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(HttpExceptionBean httpExceptionBeanBean) {
    }

    @Override
    public void onNext(T t) {

    }
}
