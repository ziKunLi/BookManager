package com.example.bookmanager;

import android.os.Bundle;

import com.example.bookmanager.discover.DiscoverFragment;
import com.example.bookmanager.main.MainFragment;
import com.example.bookmanager.personal.PersonalFragment;
import com.example.comment.base.BaseBottomActivity;
import com.example.comment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

// 单activity多fragment模式
public class MainActivity extends BaseBottomActivity {

    @Override
    public List<Class<? extends BaseFragment>> setFragmentClass() {
        List<Class<? extends BaseFragment>> fragmentClasses = new ArrayList<>();
        fragmentClasses.add(MainFragment.class);
        fragmentClasses.add(DiscoverFragment.class);
        fragmentClasses.add(PersonalFragment.class);
        return fragmentClasses;
    }

    @Override
    public int setPageFragmentLayout() {
        return R.id.mainFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public int setRootFragmentLayout() {
        return R.id.rootFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 这个单activity多fragment框架有一个bug，请注意，不要添加下面这句话，不然跳转fragment时bottomFragment不会隐藏
        // 推测是unbinder的问题
//        setContentView(R.layout.activity_main);
    }

    @Override
    public int setBottomBar() {
        return R.xml.bottombar_tabs;
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.main:
                setCurrentFragment(0);
                break;
            case R.id.discover:
                setCurrentFragment(1);
                break;
            case R.id.personal:
                setCurrentFragment(2);
                break;
            default:
                break;
        }
    }
}
