package com.example.comment.net.callback;


import com.example.comment.ui.loader.AppLoader;
import com.example.comment.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author NewBies
 * @date 2018/9/10
 */

public class RequestCallbacks implements Callback<String>{

    private final IRequest REQUEST;

    private final ISuccess SUCCESS;

    private final IError ERROR;

    private final IFailure FAILURE;

    private final LoaderStyle LOADER_STYLE;

    public RequestCallbacks(IRequest request, ISuccess success, IError error, IFailure failure, LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        //如果请求成功
        if(response.isSuccessful()){
            //如果call已经执行了
            if(call.isExecuted()){
                if(SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }
        else {
            if(ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }

        if(LOADER_STYLE != null){
            AppLoader.stopLoading();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFailure(t);
        }

        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }

        if(LOADER_STYLE != null){
            AppLoader.stopLoading();
        }
    }
}
