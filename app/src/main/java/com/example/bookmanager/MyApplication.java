package com.example.bookmanager;

import com.example.comment.app.ThisApp;
import com.example.comment.base.BaseApplication;
import com.example.comment.net.interceptors.DebugInterceptor;
import com.example.comment.net.interceptors.RedirectInterceptor;
import com.example.comment.util.GlobalCatchException;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 全局异常捕获
//        GlobalCatchException.getInstance().init(); // 这个用着其实并不舒服
        // 这个初始化是必须的
        ThisApp.init(getApplicationContext())
                .withApiHost(StaticDataPool.BASE_URL) //一般来说一个app就只有一个baseUrl，但也有特殊情况，这时候使用拦截器来解决该问题
                .withInterceptor(new DebugInterceptor(StaticDataPool.BASE_URL + "debugMain", R.raw.main_data)) // 这样可以添加debug拦截器
//                .withInterceptor(new RedirectInterceptor(StaticDataPool.DOU_BAN_BOOK_API)) // 这是一个更改baseUrl的拦截器，这里暂时用不上，之前连接超时原因是忘了断代理
                .configure();
    }
}
