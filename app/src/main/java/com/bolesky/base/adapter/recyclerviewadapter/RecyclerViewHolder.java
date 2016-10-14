/**
 * Copyright 2015 bingoogolapple
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bolesky.base.adapter.recyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 描述:适用于RecyclerView的item的ViewHolder
 */
class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnRVItemClickListener mOnRVItemClickListener;
    private OnRVItemLongClickListener mOnRVItemLongClickListener;
    private ViewHolderHelper mViewHolderHelper;
    private RecyclerView mRecyclerView;

    RecyclerViewHolder(RecyclerView recyclerView, View itemView, OnRVItemClickListener onRVItemClickListener, OnRVItemLongClickListener onRVItemLongClickListener) {
        super(itemView);
        mRecyclerView = recyclerView;
        mOnRVItemClickListener = onRVItemClickListener;
        mOnRVItemLongClickListener = onRVItemLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mViewHolderHelper = new ViewHolderHelper(mRecyclerView, this.itemView);
        mViewHolderHelper.setRecyclerViewHolder(this);
    }

    ViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.itemView.getId() && null != mOnRVItemClickListener) {
            mOnRVItemClickListener.onRVItemClick(mRecyclerView, v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return v.getId() == this.itemView.getId() && null != mOnRVItemLongClickListener &&
                mOnRVItemLongClickListener.onRVItemLongClick(mRecyclerView, v, getAdapterPosition());
    }

}