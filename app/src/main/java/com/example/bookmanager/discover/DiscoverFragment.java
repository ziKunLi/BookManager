package com.example.bookmanager.discover;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bookmanager.R;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;

public class DiscoverFragment extends BaseFragment {

    public DiscoverFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public Object setLayout() {
        return R.layout.discover_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {

    }
}
