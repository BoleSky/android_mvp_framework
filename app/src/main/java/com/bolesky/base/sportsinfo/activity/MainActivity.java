package com.bolesky.base.sportsinfo.activity;

import android.os.Bundle;
import android.util.Log;

import com.bolesky.base.sportsinfo.R;
import com.bolesky.base.sportsinfo.api.ApiWrapper;
import com.bolesky.base.sportsinfo.base.BaseActivity;
import com.bolesky.base.sportsinfo.dagger.component.AppComponent;
import com.bolesky.base.sportsinfo.dagger.component.DaggerMainActivityComponent;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    ApiWrapper apiWrapper;

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
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        showDialog();
        String s = apiWrapper.getSimple();
        Log.e("xiaoyong.cui", s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
