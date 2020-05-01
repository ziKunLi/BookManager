package com.example.comment.app;

import android.os.Handler;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * 配置文件的存储和获取
 * 线程安全的懒汉模式
 * 主配置类的辅助创建者模式配置类
 *
 * @author NewBies
 * @date 2018/9/9
 */

public class Configurator {

    /**
     * 传说weakHashMap中不常用的键值对会被回收，这样可以避免内存饱满
     */
    private static final WeakHashMap<String, Object> APP_CONFIGS = new WeakHashMap<>();
    /**
     * 拦截器
     */
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    /**
     * 全局handler
     */
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        //表示我们的配置开始了，但是没有配置完成
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    /**
     * 静态内部类单利模式的初始化
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
        APP_CONFIGS.put(ConfigType.HANDLER.name(), HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public final WeakHashMap<String, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    public final Configurator withApiHost(String host) {
        APP_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    /**
     * 获取配置时调用检查
     */
    private void checkConfiguration() {
        //final变量修饰可以防止更改一个本不应该更改的变量，java虚拟机也会对此或多或少的进行优化，会有性能的提升
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("配置未完成");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) APP_CONFIGS.get(key);
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR.name(), INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR.name(), INTERCEPTORS);
        return this;
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(String key) {
        checkConfiguration();
        final Object value = APP_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) APP_CONFIGS.get(key);
    }
}
