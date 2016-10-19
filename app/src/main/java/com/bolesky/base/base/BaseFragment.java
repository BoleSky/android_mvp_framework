package com.bolesky.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bolesky.base.App;
import com.bolesky.base.R;

public abstract class BaseFragment extends Fragment {
    protected String TAG = this.getClass().getSimpleName();
    protected App mApp;
    protected View mContentView;
    protected BaseActivity mActivity;
    protected boolean mIsVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mApp = App.getInstance();
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onLazyLoad();
        } else {
            mIsVisible = false;
            onUserInvisible();
        }
    }

    /**
     * 第一次对用户可见时调用该方法，用于加载数据等
     */
    protected void onLazyLoad() {
        initDatas();
    }

    /**
     * 对用户不可见时会调用该方法，除了第一次
     */
    public void onUserInvisible() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), container, false);
            configViews(savedInstanceState);
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    protected abstract int getLayoutId();

    /**
     * 对数据进行初始化
     */
    public abstract void initDatas();

    /**
     * 初始化View控件,对各种控件进行设置、适配、填充数据
     *
     * @param savedInstanceState
     */
    protected abstract void configViews(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mContentView.findViewById(id);
    }
}