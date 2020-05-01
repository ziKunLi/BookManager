package com.example.customui.view.recycle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.comment.ui.recycler.BaseMultipleHolder;
import com.example.comment.util.DensityUtil;

import static com.example.customui.view.recycle.RecyclerSizeUtilKt.genItemDecoration;
import static com.example.customui.view.recycle.RecyclerSizeUtilKt.genLastItemDecoration;

public abstract class HorizontalSnapHolder<T> extends BaseMultipleHolder<T> {

    protected HorizonSlideAdapter horizonSlideAdapter = null;
    private boolean slideStatus; // 管理本次和上次的滑动状态
    private SnapHelper snapHelper;
    private CanScrollHorizonRecyclerView recyclerView;
    private RecyclerView.ItemDecoration itemDecoration;

    public HorizontalSnapHolder(@NonNull View itemView) {
        super(itemView);
        itemDecoration = genItemDecoration(getLeftPadding(), getDivider());
        recyclerView = getRecyclerView();
    }

    protected abstract void onBindSnapViewHolder(T t);

    @Override
    public void onBindBaseViewHolder(T t) {
        initRecyclerView(t);
        onBindSnapViewHolder(t);
    }

    protected abstract double getShowItemCount();

    protected abstract int getShowPx();

    private void initRecyclerView(T item) {
        boolean curSlideStatus = needSlide(item);
        int width;
        if (curSlideStatus) {
            width = (int) ((DensityUtil.getScreenWidth() - getLeftPadding() - getDivider() * getShowItemCount() - getShowPx()) / getShowItemCount());
        } else {
            width = (int) ((DensityUtil.getScreenWidth() - getLeftPadding() * 2 - getDivider() * (Math.floor(getShowItemCount()) - 1)) / getShowItemCount());
        }
        if (horizonSlideAdapter == null) {
            slideStatus = curSlideStatus;
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            horizonSlideAdapter = getHorizonAdapter(item, width);
            recyclerView.setAdapter(horizonSlideAdapter);
            setSnapHelperByStatus();
        } else {
            boolean statusChange = curSlideStatus != slideStatus;
            if (statusChange) {
                slideStatus = curSlideStatus;
                horizonSlideAdapter = getHorizonAdapter(item, width);
                recyclerView.setAdapter(horizonSlideAdapter);
                setSnapHelperByStatus();
            }
        }
        if (slideStatus) {
            int itemPosition = getItemPosition(item);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (itemPosition > horizonSlideAdapter.getItemCount()) {
                itemPosition = horizonSlideAdapter.getItemCount() - 1;
            }
            if (layoutManager != null) {
                if (itemPosition == 0) {
                    layoutManager.scrollToPosition(itemPosition);
                } else if (itemPosition > 0) {
                    ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(itemPosition, getItemOffset(item));
                }
            }
        }
    }

    /**
     * 获取item滑动后的位置，防止item复用时，被复用的item位置不对
     */
    protected abstract int getItemPosition(T item);

    /**
     * 获取item滑动后左边的距离
     */
    protected abstract int getItemOffset(T item);

    /**
     * 是否需要fling 不需要的话就每次只滑动一个
     */
    public boolean needFling() {
        return true;
    }

    /**
     * 获取recyclerView，使用这个viewHolder的view必须有recyclerView布局空间
     */
    protected abstract CanScrollHorizonRecyclerView getRecyclerView();

    /**
     * 左右一样的间隙
     */
    protected abstract int getLeftPadding();

    /**
     * 中间的间隙
     */
    protected abstract int getDivider();

    /**
     * 获取可滑动也可不滑动的adapter
     */
    protected abstract HorizonSlideAdapter getHorizonAdapter(T item, int width);

    /**
     * 通过数据判断是否需要滑动
     */
    protected abstract Boolean needSlide(T item);

    private void setSnapHelperByStatus() {
        if (slideStatus) {
            handleSnap();
            recyclerView.setState(CanScrollHorizonRecyclerView.ScrollHorizontallyState.SUPER_SCROLL);
        } else {
            recyclerView.setState(CanScrollHorizonRecyclerView.ScrollHorizontallyState.SUPER_SCROLL);
            recyclerView.clearOnScrollListeners();
            recyclerView.setOnFlingListener(null);
        }
    }

    /**
     * 根据业务去设置横滑效果
     */
    public void handleSnap() {
        if (snapHelper == null) {
            snapHelper = new HorizontalSnapHelper(getRecyclerView(), getLeftPadding(), needFling());
            snapHelper.attachToRecyclerView(getRecyclerView());
        }
        handleLastItemDecoration();
    }

    private void handleLastItemDecoration() {
        int itemWidth = horizonSlideAdapter.getItemWidth();
        if (itemWidth * 2 > DensityUtil.getScreenWidth()) {
            // item 只够显示一项时，将最后一项的decor增加，保证最后一项也能靠左
            int needWidth = DensityUtil.getScreenWidth() - itemWidth - getLeftPadding() * 2;
            getRecyclerView().addItemDecoration(genLastItemDecoration(needWidth));
        }
    }
}
