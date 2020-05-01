package com.example.comment.router;

import com.alibaba.fastjson.JSONObject;
import com.example.comment.base.BaseActivity;
import com.example.comment.base.BaseFragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static com.example.comment.router.RouterConfig.getInstance;


public class RouterUtil {

    private HashMap<Class<? extends BaseFragment>, BaseFragment> fragmentHashMap = new HashMap<>();

    // 根据action跳转界面，action中包括需要跳转界面对应的路由名称，跳转所携带的数据，跳转完后是否需要返回数据，返回数据列表
    public static void startFragment(String actionJson) {
        // 解析action
        Action actionObject = JSONObject.parseObject(actionJson, Action.class);
        startFragment(actionObject);
    }

    public static void startFragment(Action action) {
        if (getInstance().isInitComplete() || action == null) {
            return;
        }
        try {
            Action.DataBean dataBean = action.getData();
            HashMap<String, Object> data = new HashMap<>();
            data.put("url", dataBean.getUrl());
            data.put("extInfoApi", dataBean.getExtInfoApi());
            data.put("extInfo", dataBean.getExtInfo());
            Constructor constructor = getInstance().getFragment(action.getAction()).getDeclaredConstructor(BaseActivity.class);
            constructor.setAccessible(true);
            getInstance().getRootActivity().startFragment((BaseFragment) constructor.newInstance(getInstance().getRootActivity()), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startFragment(Class<? extends BaseFragment> fragmentClass) {
        startFragment(fragmentClass, null);
    }

    public static void startFragment(Class<? extends BaseFragment> fragmentClass, HashMap<String, Object> data) {
        if (getInstance().isInitComplete()) {
            return;
        }
        try {
            Constructor constructor = fragmentClass.getDeclaredConstructor(BaseActivity.class);
            constructor.setAccessible(true);
            getInstance().getRootActivity().startFragment((BaseFragment) constructor.newInstance(getInstance().getRootActivity()), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
