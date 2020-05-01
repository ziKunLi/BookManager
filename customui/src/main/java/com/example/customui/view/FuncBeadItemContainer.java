package com.example.customui.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.comment.util.DensityUtil;
import com.example.customui.R;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class FuncBeadItemContainer extends LinearLayout {

    private FuncBeadItem icon;
    private TextView title;

    public FuncBeadItemContainer(Context context) {
        super(context);
        init(context);
    }

    public FuncBeadItemContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FuncBeadItemContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        icon = new FuncBeadItem(context);
        title = new TextView(context);

        icon.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        title.setGravity(Gravity.CENTER);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        addView(icon);
        addView(title);

        setId(R.id.funcItem);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setText(String textString){
        title.setText(textString);
    }

    public void setTextSize(int textSize){
        title.setTextSize(COMPLEX_UNIT_SP, textSize);
    }

    public void setTextColor(int color){
        title.setTextColor(color);
    }

    public void setIconUrl(String url){
        icon.setImageURI(Uri.parse(url));
    }

    public void setIconUri(String uri){
        icon.setImageURI(uri);
    }
}
