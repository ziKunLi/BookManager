package com.example.customui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.comment.util.DensityUtil;
import com.example.customui.R;

public class MoreTextView extends LinearLayout implements CompoundButton.OnCheckedChangeListener, ViewTreeObserver.OnPreDrawListener {


    private int moreSwitchTextSize = 12;
    private int moreTextSize = 12;
    private int moreTextColor = 0xff3c3c40;
    private int moreSwitchTextColor = 0xfffc9400;
    private int maxHeight;
    private int minHeight;
    private int maxLine;
    private int minLine = 3;
    private int lineHeight = -1;
    private TextView textView;
    private CheckBox checkBox;
    private CharSequence[] moreSwitchHints = {"展开", "收起"};
    private Drawable moreSwitchDrawable;
    private String text;
    private float lineSpace;

    public MoreTextView(Context context) {
        this(context, null);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MoreTextView, defStyleAttr, 0);
        moreSwitchTextColor = attributes.getColor(R.styleable.MoreTextView_moreSwitchTextColor, moreSwitchTextColor);
        moreTextColor = attributes.getColor(R.styleable.MoreTextView_moreTextColor, moreTextColor);
        minLine = attributes.getInt(R.styleable.MoreTextView_minLine, minLine);
        moreTextSize = attributes.getDimensionPixelSize(R.styleable.MoreTextView_moreTextSize, moreTextSize);
        moreSwitchTextSize = attributes.getDimensionPixelSize(R.styleable.MoreTextView_moreSwitchTextSize, moreSwitchTextSize);
        moreSwitchDrawable = attributes.getDrawable(R.styleable.MoreTextView_moreSwitchDrawable);
        text = attributes.getString(R.styleable.MoreTextView_moreText);
        lineSpace = attributes.getDimension(R.styleable.MoreTextView_moreTextLineSpacingExtra, 4f);
        attributes.recycle();
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_more, this, true);
        textView = inflate.findViewById(R.id.tv_more_content);
        checkBox = inflate.findViewById(R.id.cb_more_checked);
        textView.setMinLines(minLine);
        textView.setTextColor(moreTextColor);
        textView.setText(text);
        textView.setLineSpacing(DensityUtil.dip2px(lineSpace), 1);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, moreTextSize);
        checkBox.setTextColor(moreSwitchTextColor);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, moreSwitchTextSize);
        setSwitchDrawable(moreSwitchDrawable);
        textView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    /**
     * 设置文本
     *
     * @param sequence sequence
     */
    public void setText(CharSequence sequence) {
        textView.setText(sequence);
        textView.getViewTreeObserver().addOnPreDrawListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    /**
     * 设置按钮的样式
     *
     * @param drawable drawable
     */
    public void setSwitchStyle(Drawable drawable) {
        if (drawable == null) {
            throw new NullPointerException("drawable is null !!!!!!!!");
        }

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        checkBox.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置按钮的样式
     *
     * @param drawable drawable
     */
    private void setSwitchDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        setSwitchStyle(drawable);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean open) {
        textView.clearAnimation();
        final int deltaValue;
        final int startValue = textView.getHeight();
        if (open) {
            //展开
            deltaValue = maxHeight - startValue;
        } else {
            //缩进
            deltaValue = minHeight - startValue;
        }
        setMoreSwitchHints();
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                textView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                if (interpolatedTime == 0) {
                    t.clear();
                }
            }
        };
        animation.setDuration(350);
        textView.startAnimation(animation);
    }

    private void setMoreSwitchHints() {
        if (noDrawable()) {
            if (checkBox.isChecked()) {
                checkBox.setText(moreSwitchHints[1]);
            } else {
                checkBox.setText(moreSwitchHints[0]);
            }
        }
    }

    @Override
    public boolean onPreDraw() {
        textView.getViewTreeObserver().removeOnPreDrawListener(this);
        maxLine = textView.getLineCount(); //获取当前最大的line
        // 求出最大的高度
        maxHeight = textView.getHeight();
        //如果点击了展开  此时更新数据时 我们不想自动关闭它
        if (checkBox.isChecked()) {
            textView.setHeight(maxHeight);
            return false;
        }
        if (maxLine > minLine) {
            textView.setLines(minLine); //设置为最小的行数
            //获取最小行的高度
            textView.post(new Runnable() {
                @Override
                public void run() {
                    minHeight = textView.getHeight();
                }
            });
            if (noDrawable()) {
                checkBox.setText(moreSwitchHints[0]);
            }
            checkBox.setVisibility(VISIBLE);
            return false;
        } else {
            if (noDrawable()) {
                checkBox.setText(moreSwitchHints[1]);
            }
            checkBox.setVisibility(GONE);
        }
        return true;
    }

    private boolean noDrawable() {
        return moreSwitchDrawable == null;
    }
}
