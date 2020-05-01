package com.example.comment.base;

import android.app.Activity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseViewPage {

    private Unbinder unbinder;

    private View rootView;

    protected Activity activity;

    public abstract Object setPageLayout();

    public abstract void bindView();

    public BaseViewPage(Activity activity) {
        this.activity = activity;
        if (setPageLayout() instanceof Integer) {
            rootView = activity.getLayoutInflater().inflate((Integer) setPageLayout(), null);
        } else if (setPageLayout() instanceof View) {
            rootView = (View) setPageLayout();
        } else {
            throw new ClassCastException("setLayout()方法返回值类型错误：" + setPageLayout());
        }
        unbinder = ButterKnife.bind(this, rootView);
        bindView();
    }

    public View getRootView() {
        return rootView;
    }

    public void onDestory() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
