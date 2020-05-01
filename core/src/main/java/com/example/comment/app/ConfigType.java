package com.example.comment.app;

/**
 * 枚举是单利，并且只会初始化一次
 * @author NewBies
 * @date 2018/9/9
 */

public enum ConfigType {
    /**
     * 网络请求域名
     */
    API_HOST,
    /**
     * 全局上下文
     */
    APPLICATION_CONTEXT,
    /**
     * 控制初始化，配置是否完成
     */
    CONFIG_READY,
    /**
     * 存储一些我们自己的初始化项目
     */
    ICON,
    /**
     * 拦截器
     */
    INTERCEPTOR,
    /**
     * 微信登录相关（未实现）
     */
    WE_CHAT_APP_ID,
    WE_CHAT_APP_SECRET,
    HANDLER,
}
