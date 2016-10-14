package com.bolesky.base.fragment;

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
import com.bolesky.base.base.BaseFragment;
import com.bolesky.base.adapter.recyclerviewadapter.Divider;

import cn.bole.refreshlayout.RefreshLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RefreshSwipeRecyclerViewFragment extends BaseFragment implements OnRVItemClickListener, OnRVItemLongClickListener, OnItemChildClickListener, OnItemChildLongClickListener {
    private SwipeRecyclerViewAdapter mAdapter;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mDataRv;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview_refresh);
        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
//        mRefreshLayout.setDelegate(this);

        mAdapter = new SwipeRecyclerViewAdapter(mDataRv);
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
    protected void processLogic(Bundle savedInstanceState) {
//        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(mApp), false);


        mDataRv.addItemDecoration(new Divider(mApp));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mApp);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(linearLayoutManager);

        mDataRv.setAdapter(mAdapter);
    }

    @Override
    protected void onFirstUserVisible() {
        mNewPageNumber = 0;
        mMorePageNumber = 0;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
            mAdapter.closeOpenedSwipeItemLayoutWithAnim();
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_swipe_delete) {
//            showToast("长按了删除 " + mAdapter.getItem(position).title);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//        showToast("点击了条目 " + mAdapter.getItem(position).title);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
//        showToast("长按了条目 " + mAdapter.getItem(position).title);
        return true;
    }
}