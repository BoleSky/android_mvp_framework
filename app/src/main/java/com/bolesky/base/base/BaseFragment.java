package com.bolesky.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bolesky.base.App;
import com.bolesky.base.R;
import com.progresslibrary.CustomDialog;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected String TAG = this.getClass().getSimpleName();
    protected App mApp;
    protected View mContentView;
    private CustomDialog dialog;//进度条
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
            ButterKnife.bind(this, mContentView);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

    /**
     * 获取CustomDialog实例
     *
     * @return CustomDialog
     */
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(mActivity);
            dialog.setCancelable(false);
        }
        return dialog;
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        getDialog().show();
    }

    /**
     * 去除加载对话框
     */
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 显示系统状态栏
     */
    protected void showStatusBar() {
        WindowManager.LayoutParams attr = mActivity.getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivity.getWindow().setAttributes(attr);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 隐藏系统状态栏
     */
    protected void hideStatusBar() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mActivity.getWindow().setAttributes(lp);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}