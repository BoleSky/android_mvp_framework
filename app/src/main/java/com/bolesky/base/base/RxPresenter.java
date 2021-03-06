package com.bolesky.base.base;

import com.bolesky.base.api.Api;
import com.bolesky.base.bean.HttpExceptionBean;
import com.bolesky.base.utils.BaseUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import com.bolesky.base.api.ICallBackImp;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 *
 * @param <T>
 */
public class RxPresenter<T extends IBaseContract.BaseView> implements IBaseContract.BasePresenter<T> {
    T mView;
    CompositeSubscription compositeSubscription;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    /**
     * 创建观察者  这里对观察着 过滤一次，过滤出我们想要的信息，错误的信息toast
     *
     * @param callBack
     * @param <E>
     * @return
     */
    protected <E> Subscriber getSubscriber(final ICallBackImp callBack) {
        return new Subscriber<E>() {

            @Override
            public void onStart() {
                super.onStart();
                if (mView != null) {
                    mView.showLoading();
                }
            }

            @Override
            public void onCompleted() {
                if (mView != null) {
                    mView.hideLoading();
                }
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof Api.APIException) {
                    Api.APIException exception = (Api.APIException) e;
                    if (mView != null) {
                        BaseUtils.showMessage(exception.message);
                    }
                } else if (e instanceof HttpException) {
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try {
                        String json = body.string();
                        Gson gson = new Gson();
                        HttpExceptionBean mHttpExceptionBean = gson.fromJson(json, HttpExceptionBean.class);
                        if (mHttpExceptionBean != null && mHttpExceptionBean.getMessage() != null) {
                            BaseUtils.showMessage(mHttpExceptionBean.getMessage());
                            callBack.onError(mHttpExceptionBean);
                        }
                    } catch (IOException IOe) {
                        IOe.printStackTrace();
                    }
                }
                if (mView != null) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onNext(E t) {
                if (!compositeSubscription.isUnsubscribed()) {
                    callBack.onNext(t);
                }
            }

        };
    }

    protected void addSubscribe(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    /**
     * 释放Observable 持有的Subscriber引用，
     * 防止由于不能及时被释放，造成内存泄露
     */
    protected void unSubscribe() {

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

}
