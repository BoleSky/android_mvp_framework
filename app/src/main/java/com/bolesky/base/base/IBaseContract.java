package com.bolesky.base.base;

/**
 * Created by xiaoyong.cui
 * on 2016/9/26
 * E-Mail:hellocui@aliyun.com
 */

public interface IBaseContract {
    interface BaseView {

        void showError();

        void complete();

        void hideLoading();

        void showLoading();
    }

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }
}
