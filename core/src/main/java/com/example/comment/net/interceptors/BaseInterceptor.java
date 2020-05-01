package com.example.comment.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 *
 * @author NewBies
 * @date 2018/9/12
 */

public abstract class BaseInterceptor implements Interceptor {

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain){
        final HttpUrl url = chain.request().url();
        int size = url.querySize();
        final LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        for(int i = 0; i < size; i++){
            parameters.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return parameters;
    }

    protected String getUrlParmeters(Chain chain, String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain){
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        int size = formBody.size();
        for(int i = 0; i < size; i++){
            parameters.put(formBody.name(i), formBody.value(i));
        }
        return parameters;
    }

    protected String getBodyParameters(Chain chain, String key){
        return getBodyParameters(chain).get(key);
    }
}
