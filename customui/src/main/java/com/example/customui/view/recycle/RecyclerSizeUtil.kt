package com.example.customui.view.recycle

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.comment.util.DensityUtil
import com.example.comment.util.UiUtil
import java.lang.Math.floor

/**
 * 根据左边的边距和item间的边距获取item的宽度
 * item个数
 * @param left
 * @param divider
 * @param itemCount 想要显示的数量 如 2.8
 * @param slide 是否需要滑动
 * @return
 */
fun getItemWidth(context: Context, left: Int, divider: Int, itemCount: Double, slide: Boolean = true): Double {
    val screenWidth = UiUtil.getScreenWidth(context)
    val contentWidth =  if (slide) screenWidth - left - divider * floor(itemCount) else
        screenWidth - left * 2 - divider * (floor(itemCount) - 1)
    return contentWidth / itemCount
}

/**
 * 可滑动的item漏出按照dp来设置，不按照比例显示的时候 获取item的宽度 比如说显示 1个 + 第二个显示出 16dp
 * @param fullShowItemCount 全部显示的itemCount 1
 * @param showPx 只漏出来的item的漏出宽度
 */
fun getItemWidthWithSlidePx(context: Context, left: Int, divider: Int, fullShowItemCount: Int, showPx: Int): Double {
    val screenWidth = UiUtil.getScreenWidth(context)
    val itemWidth = screenWidth - left  - divider * fullShowItemCount - showPx
    return (itemWidth / fullShowItemCount).toDouble()
}

/**
 * 可滑动的item漏出按照dp来设置，不按照比例显示的时候 获取item的宽度 比如说显示 1个 + 第二个显示出 16dp
 * @param showItemCount 全部显示的itemCount
 * @param showPx 只漏出来的item的漏出宽度
 */
fun getDividerWidthWithSlidePx(context: Context, left: Int, itemWidth: Int, showItemCount: Int, showPx: Int): Double {
    val screenWidth = UiUtil.getScreenWidth(context)
    val dividerWidth = screenWidth - left  - itemWidth * showItemCount - showPx
    return (dividerWidth / showItemCount).toDouble()
}

fun genItemDecoration(left: Int, divider: Int): RecyclerView.ItemDecoration {
    return ItemDecoration(left, divider)
}

fun genLastItemDecoration(right: Int): RecyclerView.ItemDecoration {
    return LastItemDecoration(right)
}

fun genGridItemDecoration(left: Int, divider: Int, spanCount: Int): RecyclerView.ItemDecoration {
    return GridItemDecoration(left, divider, spanCount)
}

/**
 * 只是间隙的itemDecoration
 */
private class ItemDecoration(private val left: Int, private val divider: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, recyclerView, state)
        val adapter = recyclerView.adapter ?: return
        when (recyclerView.getChildAdapterPosition(view)) {
            0 -> outRect.set(left, 0, 0, 0)
            adapter.itemCount - 1 -> outRect.set(divider, 0, left, 0)
            else -> outRect.set(divider, 0, 0, 0)
        }
    }
}

/**
 * 只是间隙的itemDecoration
 */
private class LastItemDecoration(private val right: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, recyclerView, state)
        val adapter = recyclerView.adapter ?: return
        when (recyclerView.getChildAdapterPosition(view)) {
            adapter.itemCount - 1 -> outRect.set(outRect.left, 0, right, 0)
        }
    }
}

private class GridItemDecoration(private val left: Int, private val divider: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, recyclerView, state)
        val adapter = recyclerView.adapter ?: return
        val position = recyclerView.getChildAdapterPosition(view)
        // 每个span分配的间隔大小
        val spanSpace = left * (spanCount + 1) / spanCount
        // 列索引
        val colIndex = position % spanCount
        // 列左、右间隙
        outRect.left = left * (colIndex + 1) - spanSpace * colIndex
        outRect.right = spanSpace * (colIndex + 1) - left * (colIndex + 1)
        // 行间距
        if (position >= spanCount) {
            outRect.top = left
        }
    }
}