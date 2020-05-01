package com.example.comment.ui.loader;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import com.example.comment.util.UiUtil;
import com.example.customui.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class AppLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, String type) {
        final AppCompatDialog loadingDialog = new AppCompatDialog(context, R.style.loadingDialog);

        final AVLoadingIndicatorView loading = LoaderCreator.create(context, type);

        loadingDialog.setContentView(loading);

        int deviceWidth = UiUtil.getScreenWidth(context);
        int deviceHeight = UiUtil.getScreenHeight(context);

        final Window dialogWindow = loadingDialog.getWindow();

        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = deviceWidth / LOADER_SIZE_SCALE;
            layoutParams.height = deviceHeight / LOADER_SIZE_SCALE;
//          这句话可以不要，这是使用偏移量来让其居中的
//            layoutParams.height = layoutParams.height + deviceHeight/LOADER_SIZE_SCALE;
            layoutParams.gravity = Gravity.CENTER;
        }

        LOADERS.add(loadingDialog);
        loadingDialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void showLoading(Context context, LoaderStyle loaderStyle) {
        showLoading(context, loaderStyle.name());
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    //这两个的区别就是cancel回触发一些回调，而dismiss不会
                    dialog.cancel();
//                    dialog.dismiss();
                }
            }
        }
    }
}
