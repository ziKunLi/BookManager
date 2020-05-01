package com.example.comment.router;


import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;

import java.util.HashMap;

public class RouterConfig {

    private static HashMap<String, Class<? extends BaseFragment>> ROUTER_CLASS_MAP = new HashMap<>();

    private BaseActivity ROOT_ACTIVITY;

    /**
     * 静态内部类单利模式的初始化
     */
    private static class Holder {
        private static final RouterConfig INSTANCE = new RouterConfig();
    }

    public static RouterConfig getInstance() {
        return Holder.INSTANCE;
    }

    public void init(BaseActivity rootActivity) {
        ROOT_ACTIVITY = rootActivity;
    }

    public boolean isInitComplete(){
        return ROOT_ACTIVITY == null;
    }

    public BaseActivity getRootActivity(){
        return ROOT_ACTIVITY;
    }

    public RouterConfig addRouter(String routerName, Class<? extends BaseFragment> fragmentClass){
        ROUTER_CLASS_MAP.put(routerName, fragmentClass);
        return this;
    }

    public Class<? extends BaseFragment> getFragment(String router){
        return ROUTER_CLASS_MAP.get(router);
    }
}
