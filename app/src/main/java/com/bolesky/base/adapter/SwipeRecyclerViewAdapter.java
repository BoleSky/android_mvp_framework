package com.bolesky.base.adapter;

import android.support.v7.widget.RecyclerView;

import com.bolesky.base.R;
import com.bolesky.base.adapter.recyclerviewadapter.RecyclerViewAdapter;
import com.bolesky.base.adapter.recyclerviewadapter.ViewHolderHelper;
import com.bolesky.base.bean.RefreshModel;
import com.bolesky.base.adapter.recyclerviewadapter.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

public class SwipeRecyclerViewAdapter extends RecyclerViewAdapter<RefreshModel> {
    /**
     * 当前处于打开状态的item
     */
    private List<SwipeItemLayout> mOpenedSil = new ArrayList<>();

    public SwipeRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_swipe);
    }

    @Override
    public void setItemChildListener(ViewHolderHelper viewHolderHelper) {
        SwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_swipe_root);
        swipeItemLayout.setDelegate(new SwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_swipe_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_swipe_delete);
    }

    @Override
    public void fillData(ViewHolderHelper viewHolderHelper, int position, RefreshModel model) {
        viewHolderHelper.setText(R.id.tv_item_swipe_title, model.title)
                .setText(R.id.tv_item_swipe_detail, model.detail);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

}