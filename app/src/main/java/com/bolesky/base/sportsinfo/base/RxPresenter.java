package com.bolesky.base.sportsinfo.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * @param <T>
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
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

    protected void addSubscrebe(Subscription subscription) {
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
