package com.bolesky.base.sportsinfo.widget.sidesliplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


public class SideSlipLayout extends RelativeLayout {
    private DragLayout mDragLayout;

    public SideSlipLayout(Context context) {
        super(context);
    }

    public SideSlipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideSlipLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDragLayout(DragLayout dragLayout) {
        this.mDragLayout = dragLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mDragLayout.getStatus() != DragLayout.Status.CLOSE) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragLayout.getStatus() != DragLayout.Status.CLOSE) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mDragLayout.close();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
