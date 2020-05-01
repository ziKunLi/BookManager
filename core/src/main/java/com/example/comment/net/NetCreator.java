package com.example.comment.net;


import com.example.comment.app.ConfigType;
import com.example.comment.app.ThisApp;

import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetCreator {

    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    public static NetService getNetService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) ThisApp.getConfigurations(ConfigType.INTERCEPTOR.name());

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        /**
         * 添加拦截器
         */
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) ThisApp.getConfigurations().get(ConfigType.API_HOST.name());
        /**
         * 步骤四，创建Retrofit实例
         */
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(BASE_URL))
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                //加入转换器（设置数据解析器）
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class RestServiceHolder {
        private static final NetService REST_SERVICE =
                //步骤五，创建网络请求接口实例
                RetrofitHolder.RETROFIT_CLIENT.create(NetService.class);
    }
}
