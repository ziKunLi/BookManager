package com.example.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.core.R;
import com.example.core.R2;
import com.roughike.bottombar.BottomBar;

import butterknife.BindView;

public class BottomBarFragment extends BaseFragment {

    @BindView(R2.id.bottomBar)
    BottomBar bottomBar;
    private int res;

    public BottomBarFragment(){
        super();
    }

    public BottomBarFragment(BaseActivity rootActivity, int res) {
        super(rootActivity);
        this.res = res;
    }

    @Override
    public Object setLayout() {
        return R.layout.bottom_bar_item;
    }

    @Override
    public void onSaveInstance(@NonNull Bundle outState) {
        super.onSaveInstance(outState);
        bottomBar.onSaveInstanceState();
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {
        if(res == 0){ // 横屏时会再次调用到该方法，此时res为0
            return;
        }
        bottomBar.setItems(res);
    }

    public BottomBar getBottomBar() {
        return this.bottomBar;
    }

    public void setBottomBarRes(int res) {
        bottomBar.setItems(res);
    }
}
