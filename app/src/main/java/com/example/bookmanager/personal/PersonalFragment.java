package com.example.bookmanager.personal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bookmanager.R;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;

public class PersonalFragment extends BaseFragment {

    public PersonalFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public Object setLayout() {
        return R.layout.personal_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {

    }
}
