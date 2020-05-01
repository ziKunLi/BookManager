package com.example.customui.view.recycle;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSnapHelper extends LinearSnapHelper {

    private int offset;
    private RecyclerView recyclerView;
    private int direction;
    private OrientationHelper orientationHelper;
    private float INVALID_DISTANCE = 1f;
    private boolean calFling = true;
    private int millisecondsPerInch = 50;

    public HorizontalSnapHelper(RecyclerView recyclerView, int offset, boolean calFling) {
        this.offset = offset;
        this.recyclerView = recyclerView;
        this.calFling = calFling;
    }

    @Override
    public int[] calculateScrollDistance(int velocityX, int velocityY) {
        return super.calculateScrollDistance(velocityX, velocityY);
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        // 每次滑动结束都和屏幕左边的距离一致
        return targetView.getLeft() - helper.getStartAfterPadding() - offset;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return findStartView((LinearLayoutManager) layoutManager, getHorizontalHelper(layoutManager));
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        direction = velocityX;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider = (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            return RecyclerView.NO_POSITION;
        }

        int hDeltaJump;
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager, getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }

        int deltaJump = hDeltaJump;
        if (deltaJump == 0 && calFling) {
            return RecyclerView.NO_POSITION;
        }

        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }
        return targetPos;
    }

    @Nullable
    @Override
    protected RecyclerView.SmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
           return null;
        } else {
            return new LinearSmoothScroller(recyclerView.getContext()){
                @Override
                protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                    int[] snapDistances = calculateDistanceToFinalSnap(layoutManager, targetView);
                    assert snapDistances != null;
                    int dx = snapDistances[0];
                    int dy = snapDistances[1];
                    int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                    if (time > 0) {
                        action.update(dx, dy, time, mDecelerateInterpolator);
                    }
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    if (displayMetrics == null) {
                        return super.calculateSpeedPerPixel(displayMetrics);
                    } else {
                        return (millisecondsPerInch / displayMetrics.densityDpi);
                    }
                }
            };
        }
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        }
        return out;
    }

    private View findStartView(LinearLayoutManager linearLayoutManager, OrientationHelper helper) {
        int childCount = linearLayoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }
        int firstChild = linearLayoutManager.findFirstVisibleItemPosition();
        int lastChild = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (firstChild == RecyclerView.NO_POSITION) {
            return null;
        }
        if (lastChild == linearLayoutManager.getItemCount() - 1) {
            return linearLayoutManager.findViewByPosition(lastChild);
        }
        View child = linearLayoutManager.findViewByPosition(firstChild);
        //获取偏左显示的Item
        if (direction > 0) {
            if (Math.abs(helper.getDecoratedStart(child)) >= offset) {
                return linearLayoutManager.findViewByPosition(firstChild + 1);
            } else {
                return child;
            }
        } else if (direction < 0) {
            if (helper.getDecoratedEnd(child) >= offset) {
                return child;
            } else {
                return linearLayoutManager.findViewByPosition(firstChild + 1);
            }
        } else {
            return super.findSnapView(linearLayoutManager);
        }
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (orientationHelper == null) {
            orientationHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return orientationHelper;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager, OrientationHelper helper, int velocityX, int velocityY) {
        // 不需要fling操作，直接返回0
        if (!calFling) {
            return 0;
        }

        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance;
        if (Math.abs(distances[0]) > Math.abs(distances[1])) {
            distance = distances[0];
        }
        else {
            distance = distances[1];
        }
        return (int)(distance / distancePerChild);
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }
}
