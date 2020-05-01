package com.example.customui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.comment.util.DensityUtil;
import com.example.customui.R;
import com.facebook.drawee.view.SimpleDraweeView;

// 首页常用的功能珠子
public class FuncBeadItem extends SimpleDraweeView {

    private Paint bgPaint;
    private Paint textPaint;
    private int bgColor;
    private int textSize;
    private int textColor;
    private String text;
    private float textWidth;
    private float textHeight;

    public FuncBeadItem(Context context) {
        super(context);
        init(context, null);
    }

    public FuncBeadItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FuncBeadItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FuncBeadItem);
        bgColor = typedArray.getColor(R.styleable.FuncBeadItem_bgColor, 0xff3090DB);
        textColor = typedArray.getColor(R.styleable.FuncBeadItem_textColor, 0xff333333);
        textSize = (int) typedArray.getDimension(R.styleable.FuncBeadItem_textSize, 14);
        text = typedArray.getString(R.styleable.FuncBeadItem_text);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(bgColor);
        if (!TextUtils.isEmpty(text)) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
            textPaint.setTypeface(font);
            textPaint.setFakeBoldText(true);
            textPaint.setTextSize(textSize);
            textPaint.setColor(textColor);
            textWidth = textPaint.measureText(text);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            textHeight = fontMetrics.descent + fontMetrics.ascent + fontMetrics.leading;
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(DensityUtil.dip2px(50f), widthMeasureSpec);
        int height = measureSize(DensityUtil.dip2px(50f), heightMeasureSpec);
        int side = Math.min(width, height);
        setMeasuredDimension(side, side);
    }

    /**
     * 虽然写着方法名是myMeasure，但是其实这就是View类getDefaultSize()方法的源码
     * 注意看文章开头，setMeasuredDimension()需要的两个参数：宽、高信息都是通过这个方法生成的
     */
    private int measureSize(int defaultValue, int measureSpec) {
        int result = defaultValue;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = defaultValue;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, bgPaint);
        if (textPaint != null) {
            //noinspection IntegerDivisionInFloatingPointContext
            canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 - textHeight / 2, textPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        if (textPaint != null) {
            textPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (!pressed || !isSelected()) {
            if (pressed) {
                setAlpha((int) (255 * 0.7));
            } else {
                setAlpha(255);
            }
        }
    }
}
