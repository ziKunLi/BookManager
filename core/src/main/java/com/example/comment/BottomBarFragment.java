package com.example.comment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.customui.R;
import com.example.customui.R2;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;

public class BottomBarFragment extends BaseFragment {

    @BindView(R2.id.bottomBar)
    BottomBar bottomBar;
    private int res;

    public BottomBarFragment(BaseActivity rootActivity, int res) {
        super(rootActivity);
        this.res = res;
    }

    @Override
    public Object setLayout() {
        return R.layout.bottom_bar_item;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {
        bottomBar.setItems(res);
    }

    public BottomBar getBottomBar() {
        return this.bottomBar;
    }
}
