package com.example.customui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.comment.util.DensityUtil;
import com.example.customui.R;

public class MoreTextView extends LinearLayout implements View.OnClickListener, ViewTreeObserver.OnPreDrawListener {


    private int moreSwitchTextSize = 12;
    private int moreTextSize = 12;
    private int moreTextColor = 0xff3c3c40;
    private int moreSwitchTextColor = 0xfffc9400;
    private int maxHeight;
    private int minHeight;
    private int maxLine;
    private int minLine = 3;
    private TextView textView;
    private TextView openStateView;
    private int[] moreSwitchHints = {R.drawable.ic_open, R.drawable.ic_close};
    private String text;
    private float lineSpace;
    private LinearLayout container;
    private boolean openState = false;

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
        text = attributes.getString(R.styleable.MoreTextView_moreText);
        lineSpace = attributes.getDimension(R.styleable.MoreTextView_moreTextLineSpacingExtra, 4f);
        attributes.recycle();
        init();
    }

    @SuppressLint("CutPasteId")
    private void init() {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_more, this, true);
        textView = view.findViewById(R.id.tv_more_content);
        openStateView = view.findViewById(R.id.cb_more_checked);
        container = view.findViewById(R.id.container);
        textView.setMinLines(minLine);
        textView.setTextColor(moreTextColor);
        textView.setText(text);
        textView.setLineSpacing(DensityUtil.dip2px(lineSpace), 1);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, moreTextSize);
        textView.getViewTreeObserver().addOnPreDrawListener(this);
        openStateView.setBackground(getResources().getDrawable(moreSwitchHints[0]));
    }

    /**
     * 设置文本
     *
     * @param sequence sequence
     */
    public void setText(CharSequence sequence) {
        textView.setText(sequence);
        textView.getViewTreeObserver().addOnPreDrawListener(this);
        container.setOnClickListener(this);
    }

    @Override
    public boolean onPreDraw() {
        textView.getViewTreeObserver().removeOnPreDrawListener(this);
        maxLine = textView.getLineCount(); //获取当前最大的line
        // 求出最大的高度
        maxHeight = textView.getHeight();
        if (maxLine > minLine) {
            textView.setLines(minLine); //设置为最小的行数
            //获取最小行的高度
            textView.post(new Runnable() {
                @Override
                public void run() {
                    minHeight = textView.getHeight();
                }
            });
            openStateView.setVisibility(VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.container) {
            textView.clearAnimation();
            final int deltaValue;
            final int startValue = textView.getHeight();
            openState = !openState;
            if (openState) {
                //展开
                deltaValue = maxHeight - startValue;
                openStateView.setBackground(getResources().getDrawable(moreSwitchHints[1]));
            } else {
                //缩进
                deltaValue = minHeight - startValue;
                openStateView.setBackground(getResources().getDrawable(moreSwitchHints[0]));
            }
            Animation animation = new Animation() {
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    textView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    if (interpolatedTime == 0) {
                        t.clear();
                    }
                }
            };
            animation.setDuration(maxLine > 10 ? 400 : 200);
            textView.startAnimation(animation);
        }
    }
}
