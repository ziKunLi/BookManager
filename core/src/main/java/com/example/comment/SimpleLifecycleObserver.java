package com.example.comment;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.comment.util.LogUtil;

public class SimpleLifecycleObserver implements LifecycleObserver {

    private Context context;
    private String logInfoHead;
    private boolean enable;
    private String tag = "lifecycle";

    public SimpleLifecycleObserver(Context context, String logInfoHead) {
        this(context, "", logInfoHead);
    }

    public SimpleLifecycleObserver(Context context, String tag, String logInfoHead) {
        this.context = context;
        this.logInfoHead = logInfoHead;
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {
        LogUtil.d(tag, logInfoHead + " : onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onStart() {
        LogUtil.d(tag, logInfoHead + " : onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void logResume() {
        LogUtil.d(tag, logInfoHead + " : onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop() {
        LogUtil.d(tag, logInfoHead + " : onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        LogUtil.d(tag, logInfoHead + " : onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        LogUtil.d(tag, logInfoHead + " : onDestroy");
    }
}
