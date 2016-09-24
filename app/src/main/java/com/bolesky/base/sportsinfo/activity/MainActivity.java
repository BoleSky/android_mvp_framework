package com.bolesky.base.sportsinfo.activity;

import android.os.Bundle;

import com.bolesky.base.sportsinfo.R;
import com.bolesky.base.sportsinfo.base.BaseActivity;
import com.bolesky.base.sportsinfo.dagger.component.AppComponent;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
