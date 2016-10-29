package com.bolesky.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import butterknife.ButterKnife;
import cn.bole.tabnavigator.MagicIndicator;
import cn.bole.tabnavigator.TabNavigator;
import cn.bole.tabnavigator.ViewPagerHelper;
import cn.bole.tabnavigator.abs.IPagerIndicator;
import cn.bole.tabnavigator.abs.IPagerTitleView;
import cn.bole.tabnavigator.abs.NavigatorAdapter;
import cn.bole.tabnavigator.titles.PagerTitleView;

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
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{
            R.string.home,
            R.string.subject,
            R.string.status,
            R.string.group};
    //Tab 选中图片
    private final int[] TAB_SELECTED_IMAGES = new int[]{
            R.drawable.ic_tab_home_active,
            R.drawable.ic_tab_subject_active,
            R.drawable.ic_tab_status_active,
            R.drawable.ic_tab_group_active};

    //Tab 未选中图片
    private final int[] TAB_UNSELECTED_IMAGES = new int[]{
            R.drawable.ic_tab_home_normal,
            R.drawable.ic_tab_subject_normal,
            R.drawable.ic_tab_status_normal,
            R.drawable.ic_tab_group_normal};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{
            new HomeFragment(),
            new SubjectFragment(),
            new StatusFragment(),
            new GroupFragment()};

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
        FragmentTabPagerAdapter fragmentTabPagerAdapter = new FragmentTabPagerAdapter(this.getSupportFragmentManager(),
                TAB_FRAGMENTS, this);
        mViewPager.setAdapter(fragmentTabPagerAdapter);
        initTabNavigator();
    }

    @Override
    protected void setListener() {
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

    /**
     * 初始化底部导航TabNavigator
     */
    private void initTabNavigator() {

        TabNavigator tabNavigator = new TabNavigator(this);
        tabNavigator.setAdjustMode(true);
        tabNavigator.setAdapter(new NavigatorAdapter() {
            @Override
            public int getCount() {
                return TAB_TITLES.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                PagerTitleView pagerTitleView = new PagerTitleView(context);
                // load layout
                View view = LayoutInflater.from(context).inflate(R.layout.layout_tab, null);
                ButterKnife.bind(this, view);
                final ImageView image = (ImageView) view.findViewById(R.id.iv_image);
                final TextView title = (TextView) view.findViewById(R.id.tv_title);
                image.setImageResource(TAB_UNSELECTED_IMAGES[index]);
                title.setText(TAB_TITLES[index]);
                pagerTitleView.setContentView(view);

                pagerTitleView.setOnPagerTitleChangeListener(new PagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        title.setTextColor(getResources().getColor(R.color.themeColor));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        title.setTextColor(getResources().getColor(R.color.light_gray));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        image.setScaleX(1.0f + (1.2f - 1.0f) * leavePercent);
                        image.setScaleY(1.0f + (1.2f - 1.0f) * leavePercent);
                        image.setImageResource(TAB_UNSELECTED_IMAGES[index]);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        image.setScaleX(1.0f + (1.5f - 1.0f) * enterPercent);
                        image.setScaleY(1.0f + (1.5f - 1.0f) * enterPercent);
                        image.setImageResource(TAB_SELECTED_IMAGES[index]);
                    }
                });

                pagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return pagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(tabNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
