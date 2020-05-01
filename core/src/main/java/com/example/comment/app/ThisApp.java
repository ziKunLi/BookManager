package com.example.comment.app;

import android.content.Context;
import android.os.Handler;

import java.util.WeakHashMap;

/**
 * 主配置类
 * @author NewBies
 * @date 2018/9/9
 */

public final class ThisApp {

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static WeakHashMap<String, Object> getConfigurations(){
        return Configurator.getInstance().getAppConfigs();
    }

    public static Object getConfigurations(String key){
        return Configurator.getInstance().getAppConfigs().get(key);
    }

    public static Context getApplicationContext(){
        return (Context)getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigType.HANDLER.name());
    }

    public static <T> T getConfiguration(String key) {
        return getConfigurator().getConfiguration(key);
    }
}
