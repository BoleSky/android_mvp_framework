package com.bolesky.base.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bolesky.base.R;
import com.bolesky.base.api.ApiWrapper;
import com.bolesky.base.base.BaseActivity;
import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.dagger.component.DaggerMainActivityComponent;
import com.bolesky.base.widget.sidesliplayout.DragLayout;
import com.bolesky.base.widget.sidesliplayout.SideSlipLayout;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Inject
    ApiWrapper apiWrapper;
    @Bind(R.id.dl)
    DragLayout dl;
    @Bind(R.id.sideSlipLayout)
    SideSlipLayout ssl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                //.mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_drawer_home);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.open();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
//        showDialog();
        String s = apiWrapper.getSimple();
        Log.e("xiaoyong.cui", s);
        dl.setDragListener(new DragLayout.DragListener() {
            //界面打开的时候
            @Override
            public void onOpen() {
            }

            //界面关闭的时候
            @Override
            public void onClose() {
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
//                ViewHelper.setAlpha(ssl, 1 - percent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
