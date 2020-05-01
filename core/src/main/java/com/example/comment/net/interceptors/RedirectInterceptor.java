package com.example.comment.net.interceptors;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RedirectInterceptor extends BaseInterceptor {

    private final String REDIRECT_URL;

    public RedirectInterceptor(String redirectUrl) {
        REDIRECT_URL = redirectUrl;
    }

    private Response getResponse(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        HttpUrl newBaseUrl = HttpUrl.parse(REDIRECT_URL);
        if (newBaseUrl == null) {
            return null;
        }
        //重建新的HttpUrl，修改需要修改的url部分
        HttpUrl newFullUrl = oldHttpUrl.newBuilder()
                .host(newBaseUrl.host())//更换主机名
                .port(newBaseUrl.port())//更换端口
                .build();
        return chain.proceed(request.newBuilder().url(newFullUrl).build());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if (url.startsWith(REDIRECT_URL)) {
            return getResponse(chain);
        }
        return chain.proceed(chain.request());
    }
}
