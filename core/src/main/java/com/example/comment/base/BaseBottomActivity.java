package com.example.comment.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.comment.BottomBarFragment;
import com.example.comment.router.RouterConfig;
import com.example.comment.router.RouterUtil;
import com.example.comment.util.GlobalCatchException;
import com.example.core.R;
import com.example.core.R2;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;

/**
 * 适用于底部导航栏 + view模式，也可适用于底部导航栏 + viewpager模式
 */
public abstract class BaseBottomActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R2.id.bottomFragment)
    FrameLayout bottomFragment;

    private int lastIndex = -1;

    private List<Class<? extends BaseFragment>> fragmentClasses;

    private BaseFragment[] fragments;

    private int layoutId;

    private BottomBarFragment bottomBarFragment;

    private HashMap<BaseFragment, List<BaseFragment>> fragmentStackHashMap;

    private long lastTime;

    public abstract List<Class<? extends BaseFragment>> setFragmentClass();

    public abstract int setPageFragmentLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public abstract int setBottomBar();

    private void init() {
        fragmentStackHashMap = new HashMap<>();
        bottomBarFragment = new BottomBarFragment(this, setBottomBar());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.bottomFragment, bottomBarFragment);
        fragmentTransaction.commit();
        fragmentClasses = setFragmentClass();
        layoutId = setPageFragmentLayout();
        fragments = new BaseFragment[fragmentClasses.size()];
        setCurrentFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomBarFragment.getBottomBar().setOnTabSelectListener(this);
    }

    /**
     * 设置当前显示的碎片
     */
    public void setCurrentFragment(int currentIndex) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentIndex == lastIndex) {
            return;
        }
        //切换碎片时切换上一个碎片
        if (lastIndex != -1) {
            fragmentTransaction.hide(fragments[lastIndex]);
        }
        if (fragments[currentIndex] == null) {
            try {
                Constructor constructor = fragmentClasses.get(currentIndex).getConstructor(BaseActivity.class);
                fragments[currentIndex] = (BaseFragment) constructor.newInstance(this);
            } catch (Exception e) {
                GlobalCatchException.getInstance().uncaughtException(Thread.currentThread(), e);
                return;
            }
            //添加栈
            List<BaseFragment> list = new ArrayList<>();
            list.add(fragments[currentIndex]);
            fragmentStackHashMap.put(fragments[currentIndex], list);
            //第一次初始化碎片
            fragmentTransaction.add(layoutId, fragments[currentIndex]);
        } else {
            //显示碎片
            fragmentTransaction.show(fragments[currentIndex]);
        }
        fragmentTransaction.commit();
        lastIndex = currentIndex;
    }

    @Override
    public void startFragment(BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        List<BaseFragment> fragmentList = fragmentStackHashMap.get(fragments[lastIndex]);
        if (fragmentList != null && !fragmentList.contains(fragment) && !fragment.isAdded()) {
            //添加新的碎片并显示
            fragmentTransaction.hide(fragmentList.get(fragmentList.size() - 1)).add(rootLayoutId, fragment);
            fragmentList.add(fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.addToBackStack(null).setTransition(TRANSIT_FRAGMENT_OPEN).commit();
        bottomFragment.setVisibility(View.GONE);
        fragmentStackHashMap.put(fragments[lastIndex], fragmentList);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBarFragment.setBottomBarRes(setBottomBar());
        bottomBarFragment.onSaveInstance(outState);
        List<BaseFragment> fragmentList = fragmentStackHashMap.get(fragments[lastIndex]);
        if (fragmentList == null) {
            return;
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentList.get(i).onSaveInstance(outState);
        }
    }

    public void backToMain() {
        backToMain(null);
    }

    public void backToMain(HashMap<String, Object> data) {
        BaseFragment baseFragment = back();
        if(baseFragment != null){
            baseFragment.setData(data);
        }
    }

    private BaseFragment back() {
        List<BaseFragment> fragmentList = fragmentStackHashMap.get(fragments[lastIndex]);
        while (fragmentList != null && fragmentList.size() > 1) {
            onBackPressed();
        }
        if (fragmentList != null && fragmentList.size() > 0) {
            return fragmentList.get(0);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        List<BaseFragment> fragmentList = fragmentStackHashMap.get(fragments[lastIndex]);
        if (fragmentList == null || fragmentList.size() <= 0) {
            return;
        }
        if (fragmentList.size() == 1) {
            if (System.currentTimeMillis() - lastTime < 2000) {
                super.onBackPressed();
            } else {
                lastTime = System.currentTimeMillis();
                Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
            }
        } else {
            fragmentList.remove(fragmentList.size() - 1);
            if (fragmentList.size() == 1) {
                bottomFragment.setVisibility(View.VISIBLE);
            }
            fragmentStackHashMap.put(fragments[lastIndex], fragmentList);
            super.onBackPressed();
        }
    }
}
