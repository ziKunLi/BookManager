package com.example.comment.net;

import android.content.Context;

import com.example.comment.net.callback.IError;
import com.example.comment.net.callback.IFailure;
import com.example.comment.net.callback.IRequest;
import com.example.comment.net.callback.ISuccess;
import com.example.comment.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */

public class NetClientBuilder {
    private String url;
    private Map<String, Object> PARAMS = NetCreator.getParams();
    private String downloadDir;
    private String extension;
    private String name;
    private IRequest request;
    private ISuccess success;
    private IError error;
    private IFailure failure;
    private RequestBody body;
    private LoaderStyle LOADER_STYLE;
    private Context context;
    private File file;

    NetClientBuilder(){}

    public final NetClientBuilder url(String url){
        this.url = url;
        return this;
    }

    public final NetClientBuilder params(WeakHashMap<String, Object> params){
        PARAMS.putAll(params);
        return this;
    }


    public final NetClientBuilder params(String key, Object value){
        PARAMS.put(key, value);
        return this;
    }

    /**
     * 文件下载后存放在哪个目录
     * @param downloadDir
     * @return
     */
    public final NetClientBuilder dir(String downloadDir){
        this.downloadDir = downloadDir;
        return this;
    }

    /**
     * 文件的后缀名
     * @param extension
     * @return
     */
    public final NetClientBuilder extension(String extension){
        this.extension = extension;
        return this;
    }

    public final NetClientBuilder name(String name){
        this.name = name;
        return this;
    }

    public final NetClientBuilder raw(String raw){
        //默认传的参数是json格式
        this.body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public final NetClientBuilder success(ISuccess success){
        this.success = success;
        return this;
    }

    public final NetClientBuilder failure(IFailure failure){
        this.failure = failure;
        return this;
    }

    public final NetClientBuilder error(IError error){
        this.error = error;
        return this;
    }

    public final NetClientBuilder request(IRequest request){
        this.request = request;
        return this;
    }

    public final NetClientBuilder loader(Context context, LoaderStyle style){
        this.LOADER_STYLE = style;
        this.context = context;
        return this;
    }

    public final NetClientBuilder loader(Context context){
        this.LOADER_STYLE = LoaderStyle.BallSpinFadeLoaderIndicator;
        this.context = context;
        return this;
    }

    public final NetClientBuilder file(File file){
        this.file = file;
        return this;
    }

    public final NetClientBuilder file(String filePath){
        this.file = new File(filePath);
        return this;
    }

    public Map<String, Object> checkParams(){
        return PARAMS;
    }

    public final NetClient build(){
        return new NetClient(url, PARAMS, downloadDir, extension, name, request, success, error, failure, body, LOADER_STYLE, file, context);
    }
}
