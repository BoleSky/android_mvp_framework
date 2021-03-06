package com.bolesky.base.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bolesky.base.R;
import com.bolesky.base.adapter.recyclerviewadapter.OnItemChildClickListener;
import com.bolesky.base.adapter.recyclerviewadapter.OnItemChildLongClickListener;
import com.bolesky.base.adapter.recyclerviewadapter.OnRVItemClickListener;
import com.bolesky.base.adapter.recyclerviewadapter.OnRVItemLongClickListener;
import com.bolesky.base.adapter.SwipeRecyclerViewAdapter;
import com.bolesky.base.base.BaseActivity;
import com.bolesky.base.bean.RefreshModel;
import com.bolesky.base.dagger.component.AppComponent;
import com.bolesky.base.adapter.recyclerviewadapter.Divider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bole.refreshlayout.NormalRefreshViewHolder;
import cn.bole.refreshlayout.RefreshLayout;

public class SwipeRecyclerViewActivity extends BaseActivity implements OnRVItemClickListener, OnRVItemLongClickListener, OnItemChildClickListener, OnItemChildLongClickListener, RefreshLayout.BGARefreshLayoutDelegate {
    //    private BGABanner mBanner;
    @Bind(R.id.data)
    RecyclerView mDataRv;
    private SwipeRecyclerViewAdapter mAdapter;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;
    @Bind(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

        mDataRv.setAdapter(mAdapter);
        RefreshModel refreshModel;
        List<RefreshModel> list=new ArrayList<>();
        for (int i= 0;i <=10;i++){
            refreshModel=new RefreshModel("Title"+i,"detail"+i);
            list.add(refreshModel);
        }
        mAdapter.setData(list);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }
        });
    }

    @Override
    public void configViews() {
        mRefreshLayout.setDelegate(this);
        mAdapter = new SwipeRecyclerViewAdapter(mDataRv);

        mRefreshLayout.setRefreshViewHolder(new NormalRefreshViewHolder(this, true));
        mDataRv.addItemDecoration(new Divider(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {


    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
//            showToast("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View itemView, int position) {
//        showToast("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup viewGroup, View itemView, int position) {
//        showToast("长按了条目 " + mAdapter.getItem(position).title);
        return true;
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(RefreshLayout refreshLayout) {
//        mNewPageNumber++;
//        if (mNewPageNumber > 4) {
//            mRefreshLayout.endRefreshing();
//            showToast("没有最新数据了");
//            return;
//        }
//        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
//            @Override
//            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
//                mRefreshLayout.endRefreshing();
//                mAdapter.addNewData(response.body());
//                mDataRv.smoothScrollToPosition(0);
//            }
//
//            @Override
//            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
//                mRefreshLayout.endRefreshing();
//            }
//        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(RefreshLayout refreshLayout) {
        return true;
    }

}