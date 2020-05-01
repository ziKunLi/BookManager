package com.example.comment.ui.recycler;

import androidx.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;


/**
 * recycleView的分割线
 *
 * @author NewBies
 * @date 2018/9/18
 */

public class BaseDecoration extends DividerItemDecoration {

    /**
     * @param color 分割线颜色
     * @param size  分割线宽度
     */
    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }

}
