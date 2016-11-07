package com.bolesky.base.api;

import com.bolesky.base.bean.HttpExceptionBean;

/**
 * Created by xiaoyong.cui
 * on 2016/9/27
 * E-Mail:hellocui@aliyun.com
 */

public abstract class ICallBackImp<T> implements ICallBack<T> {
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
