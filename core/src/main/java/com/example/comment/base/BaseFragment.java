package com.example.comment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.comment.SimpleLifecycleObserver;
import com.example.comment.util.LogUtil;
import com.example.comment.util.StringUtil;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    private BaseActivity rootActivity;

    public BaseFragment(BaseActivity rootActivity) {
        this.rootActivity = rootActivity;
    }

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstance, View rootView);

    public void onSaveInstance(HashMap<String, Object> data) {
    }

    public void onNewIntent(HashMap<String, Object> data) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout()方法返回值类型错误：" + setLayout());
        }
        String name = getClass().getSimpleName();
        String tag = "fragmentLifecycle";
        LogUtil.d(tag, name + " : onCreateView");
        unbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        getLifecycle().addObserver(new SimpleLifecycleObserver(getContext(), tag, name));
        return rootView;
    }

    public BaseActivity getRootActivity() {
        return this.rootActivity;
    }

    public void showToast(String message) {
        if (!StringUtil.isEmpty(message)) {
            Toast.makeText(rootActivity, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
