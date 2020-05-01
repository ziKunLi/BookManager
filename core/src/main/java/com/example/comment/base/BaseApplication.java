package com.example.comment.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.comment.util.LogUtil;
import com.facebook.drawee.backends.pipeline.Fresco;


@SuppressLint("Registered")
public class BaseApplication extends Application {
    private final String lifecycle = "lifecycle";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is created");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is started");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is resumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is paused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is stopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.v(lifecycle, activity.getLocalClassName() + " is destroyed");
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
