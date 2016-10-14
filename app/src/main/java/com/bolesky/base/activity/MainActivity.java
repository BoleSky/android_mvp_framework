package com.bolesky.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolesky.base.R;
import com.bolesky.base.adapter.FragmentTabPagerAdapter;
import com.bolesky.base.api.ApiWrapper;
import com.bolesky.base.base.BaseActivity;
import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.dagger.component.DaggerMainActivityComponent;
import com.bolesky.base.fragment.GroupFragment;
import com.bolesky.base.fragment.HomeFragment;
import com.bolesky.base.fragment.StatusFragment;
import com.bolesky.base.fragment.SubjectFragment;
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
    @Bind(R.id.tl_bottom)
    TabLayout mTabLayout;
    @Bind(R.id.vp_content)
    ViewPager mViewPager;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{
            R.string.home,
            R.string.subject,
            R.string.status,
            R.string.group};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{
            R.drawable.tab_home_selector,
            R.drawable.tab_subject_selector,
            R.drawable.tab_status_selector,
            R.drawable.tab_group_selector};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{
            new HomeFragment(),
            new SubjectFragment(),
            new StatusFragment(),
            new GroupFragment()};
    private FragmentTabPagerAdapter mFragmentTabPagerAdapter;

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

        mFragmentTabPagerAdapter = new FragmentTabPagerAdapter(getSupportFragmentManager(), TAB_FRAGMENTS, TAB_TITLES, this);
        mViewPager.setAdapter(mFragmentTabPagerAdapter);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);
        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] titles, int[] images) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable d = null;
            switch (i) {
                case 0:
                    d = getResources().getDrawable(images[0]);
                    break;
                case 1:
                    d = getResources().getDrawable(images[1]);
                    break;
                case 2:
                    d = getResources().getDrawable(images[2]);
                    break;
                case 3:
                    d = getResources().getDrawable(images[3]);
                    break;
            }
            tab.setIcon(d);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
