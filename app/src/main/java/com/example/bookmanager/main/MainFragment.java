package com.example.bookmanager.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.bookmanager.StaticDataPool;
import com.example.bookmanager.book.BookDetailsFragment;
import com.example.bookmanager.main.recycle.MainFragmentAdapter;
import com.example.bookmanager.main.recycle.MainFragmentDataConverter;
import com.example.comment.app.ThisApp;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.example.comment.net.NetClient;
import com.example.comment.router.RouterConfig;
import com.example.comment.router.RouterUtil;
import com.example.comment.ui.recycler.BaseMultipleAdapter;
import com.example.comment.ui.recycler.DataConverter;
import com.example.comment.util.LogUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import butterknife.BindView;

public class MainFragment extends BaseFragment {

    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
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
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        //设置为true的话，下拉的时候球会由小变大，回去的时候会由大变小
        //第二个参数是起始高度，第三个参数是最终高度
        refreshLayout.setProgressViewOffset(true, 120, 300);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            //设置两秒的延迟来模拟网络请求
            ThisApp.getHandler().postDelayed(() -> refreshLayout.setRefreshing(false), 2000);
        });
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

    private void startScan() {
        // 创建IntentIntegrator对象
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);
        //设置扫码的类型为二维码
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("请对准二维码/条形码进行扫描");
        // 开始扫描
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showToast("取消扫描");
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("isbn", result.getContents());
                RouterUtil.startFragment(BookDetailsFragment.class, hashMap);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(getRootActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //先判断有没有权限 ，没有就在这里进行权限的申请
            ActivityCompat.requestPermissions(getRootActivity(), new String[]{android.Manifest.permission.CAMERA}, 1);
        } else {
            startScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScan();
                } else {
                    showToast("请手动打开相机权限");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            dataGet();
        }
    }

    private void dataGet() {
        // okHttp3 + retrofit2 的网络框架
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
                requestPermission();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
