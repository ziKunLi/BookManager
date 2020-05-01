package com.example.comment.util;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.comment.app.ThisApp;

/**
 * 设备屏幕工具类
 * <p/>
 */
public final class DensityUtil {

    private static int sWidth = 720;
    private static int sHeight = 1080;

    private static int[] deviceWidthHeight = new int[2];

    public static int[] getDeviceInfo(Activity activity) {
        if ((deviceWidthHeight[0] == 0) && (deviceWidthHeight[1] == 0)) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay()
                    .getMetrics(metrics);

            deviceWidthHeight[0] = metrics.widthPixels;
            deviceWidthHeight[1] = metrics.heightPixels;
        }
        return deviceWidthHeight;
    }

    /**
     * 获取屏幕宽度的方法
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) ThisApp.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度的方法
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) ThisApp.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = ThisApp.getApplicationContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusBarHeight;
    }


    /**
     * dp -> px
     *
     * @param dpValue dp数值
     * @return dp to  px
     */
    public static int dip2px(float dpValue) {
        final float scale = ThisApp.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px -> dp
     *
     * @param pxValue px的数值
     * @return px to dp
     */
    public static int px2dip(float pxValue) {
        final float scale = ThisApp.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);

    }

}