package com.example.bookmanager.main.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bookmanager.R;
import com.example.bookmanager.R2;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import butterknife.BindView;

public class ScanFragment extends BaseFragment {

    @BindView(R2.id.scanView)
    DecoratedBarcodeView scanView;

    public ScanFragment(BaseActivity rootActivity) {
        super(rootActivity);
    }

    @Override
    public Object setLayout() {
        return R.layout.scan_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstance, View rootView) {
        requestPermission();
    }

    private void startScan() {
        scanView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                String res = result.getText();
                if (null != res) {

                }
                scanView.getBarcodeView().stopDecoding();
                scanView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startScan();
                    }
                }, 1500L);

            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 22 && ContextCompat.checkSelfPermission(getRootActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //先判断有没有权限 ，没有就在这里进行权限的申请
            ActivityCompat.requestPermissions(getRootActivity(), new String[]{android.Manifest.permission.CAMERA}, 1);
        }else {
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
}
