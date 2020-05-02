package com.example.comment.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.comment.router.RouterConfig;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;


public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    protected int rootLayoutId;

    public abstract int setLayout();

    public abstract int setRootFragmentLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        unbinder = ButterKnife.bind(this);
        RouterConfig.getInstance().init(this);
        rootLayoutId = setRootFragmentLayout();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void startFragment(BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            //添加新的碎片并显示
            if(fragments.size() > 0){
                fragmentTransaction.hide(fragments.get(fragments.size() - 1));
            }
            fragmentTransaction.add(rootLayoutId, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.addToBackStack(null).setTransition(TRANSIT_FRAGMENT_OPEN).commit();
    }

    /**
     * 带数据的fragment跳转
     *
     * @param fragment
     * @param data
     */
    public void startFragment(BaseFragment fragment, HashMap<String, Object> data) {
        fragment.setData(data);
        startFragment(fragment);
    }

    /**
     * 释放一些资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        // 告诉垃圾收集器打算进行垃圾收集，而垃圾收集器是否进行收集是不确定的
        System.gc();
        // 强制调用已经失去引用对象的finalize方法，也就是释放了失去引用对象所占用的资源
        System.runFinalization();
    }
}
