package com.bolesky.base.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bolesky.base.R;
import com.bolesky.base.App;
import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.utils.StatusBarCompat;
import com.progresslibrary.CustomDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context = null;
    protected Toolbar toolbar = null;
    protected int statusBarColor = 0;
    private CustomDialog dialog;//进度条

    /**
     * 加载布局文件
     *
     * @return activity布局文件id
     */
    public abstract int getLayoutId();

    /**
     * 各个Component dagger注入依赖
     *
     * @param appComponent 全局Component
     */

    protected abstract void setupActivityComponent(AppComponent appComponent);

    /**
     * 初始化配置
     */
    public abstract void initToolBar();

    /**
     * 对数据进行初始化
     */
    public abstract void initDatas();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        if (statusBarColor > 0) {
            StatusBarCompat.compat(this, statusBarColor);
        } else if (statusBarColor == 0) {
            StatusBarCompat.compat(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        context = this;
        ButterKnife.bind(this);
        setupActivityComponent(App.getInstance().getAppComponent());
        toolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (toolbar != null) {
            initToolBar();
            setSupportActionBar(toolbar);
        }
        initDatas();
        configViews();
        setListener();
        processLogic(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 获取CustomDialog实例
     *
     * @return CustomDialog
     */
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
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
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attr);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 隐藏系统状态栏
     */
    protected void hideStatusBar() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Activity跳转页面
     *
     * @param clazz 目标Activity
     */
    public void skipActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    /**
     * 带参数的Activity跳转页面
     *
     * @param clazz  目标Activity
     * @param bundle 传递数据
     */
    public void skipActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    /**
     * Android Home按钮事件
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
