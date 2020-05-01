package com.example.bookmanager.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.bookmanager.StaticDataPool;
import com.example.bookmanager.main.fragment.ScanFragment;
import com.example.bookmanager.main.recycle.MainFragmentAdapter;
import com.example.bookmanager.main.recycle.MainFragmentDataConverter;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.comment.net.NetClient;
import com.example.comment.router.RouterUtil;
import com.example.comment.ui.recycler.BaseMultipleAdapter;
import com.example.comment.ui.recycler.DataConverter;
import com.example.comment.util.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    private BaseMultipleAdapter adapter;
    private DataConverter dataConverter;

    public MainFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public Object setLayout() {
        return R.layout.main_fragment;
    }

    /**
     * Fragment中初始化Toolbar
     */
    public void initToolbar(Toolbar toolbar, String title, boolean isDisplayHomeAsUp) {
        AppCompatActivity appCompatActivity = getRootActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {
        initToolbar(toolbar, "", false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataGet();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        //通过MenuItem得到SearchView
        SearchView searchView = (SearchView) searchItem.getActionView();
        //设置是否显示搜索框展开时的提交按钮
        searchView.setSubmitButtonEnabled(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            dataGet();
        }
    }

    private void dataGet() {
        NetClient.builder()
                .url(StaticDataPool.BASE_URL + "debugMain")
                .success(response -> {
                    //设置Adapter
                    if (adapter == null) {
                        dataConverter = new MainFragmentDataConverter(response);
                        adapter = new MainFragmentAdapter(dataConverter.convert());
                        recyclerView.setAdapter(adapter);
                    } else {
                        dataConverter.setJsonData(response);
                        adapter.setData(dataConverter.convert());
                        adapter.notifyDataSetChanged();
                    }
                    LogUtil.d(StaticDataPool.HTTP_TAG, "请求成功：" + response);
                })
                .failure(throwable -> LogUtil.d(StaticDataPool.HTTP_TAG, "请求失败：" + throwable))
                .build()
                .get();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                RouterUtil.startFragment(ScanFragment.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
