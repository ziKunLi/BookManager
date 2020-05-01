package com.example.customui.view.recycle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CanScrollHorizonRecyclerView extends RecyclerView {

    private ScrollHorizontallyState state = ScrollHorizontallyState.CAN_SCROLL;
    private float mLastX;
    private float mLastY;
    private boolean needInvokeEventDispatch = true;
    private float mAngle = 60; // 滑动冲突时的角度控制， 当角度大于60度的时候交给父滑动控件处理，否则子控件处理

    public CanScrollHorizonRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CanScrollHorizonRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanScrollHorizonRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setNeedInvokeEventDispatch(boolean needInvokeEventDispatch) {
        this.needInvokeEventDispatch = needInvokeEventDispatch;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    public ScrollHorizontallyState getState() {
        return state;
    }

    public void setState(ScrollHorizontallyState state) {
        this.state = state;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        switch (state) {
            case CAN_SCROLL:
                return true;
            case CAN_NOT_SCROLL:
                return false;
            case SUPER_SCROLL:
                return super.canScrollHorizontally(direction);
        }
        return super.canScrollHorizontally(direction);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!needInvokeEventDispatch) return super.dispatchTouchEvent(ev);
        float x = ev.getX();
        float y = ev.getY();
        RecyclerView parentView = findParentRecyclerView(this);
        if (parentView == null) return super.dispatchTouchEvent(ev);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 不让父控件拦截
                parentView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!dispatchCurrentView(x, y)) {
                    // 让父滑动控件可以响应事件
                    parentView.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                parentView.requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否需要当前view处理move事件
     * @param x
     * @param y
     * @return 当夹角小于一定角度时为true
     */
    private boolean dispatchCurrentView(float x, float y) {
        if (ScrollHorizontallyState.SUPER_SCROLL == state
                && getLayoutManager() instanceof LinearLayoutManager) {
            float offset = x - mLastX;
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && offset > 0) {
                return false;
            }
            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1 && offset < 0) {
                return false;
            }
        }
        float deltaX = Math.abs(x - mLastX);
        float deltaY = Math.abs(y - mLastY);
        if (deltaX == 0) return false;
        double arcTan = deltaY / deltaX;
        return Math.atan(arcTan) <= (mAngle / 180f) * Math.PI;
    }

    @Nullable
    private RecyclerView findParentRecyclerView(ViewParent view) {
        if (view == null) {
            return null;
        }
        ViewParent parentView = view.getParent();
        if (parentView instanceof RecyclerView) {
            return (RecyclerView) parentView;
        } else {
            return findParentRecyclerView(parentView);
        }
    }

    public enum ScrollHorizontallyState {
        CAN_SCROLL,
        CAN_NOT_SCROLL,
        SUPER_SCROLL
    }
}
