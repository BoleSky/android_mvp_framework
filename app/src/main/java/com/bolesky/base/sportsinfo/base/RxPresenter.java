package com.bolesky.base.sportsinfo.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
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

    protected void unSubscribe() {

        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

}
