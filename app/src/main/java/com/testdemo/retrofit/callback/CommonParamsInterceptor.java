package com.testdemo.retrofit.callback;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 16:06
 * @DESC：请求公共参数处理
 */

public class CommonParamsInterceptor implements Interceptor {
    private Map<String,String> params;
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String method = request.method();
        if (null != params && params.size() > 0) {
            if (method.equals("GET")){
                HttpUrl.Builder httpBuilder = request.url().newBuilder();
                for (String key : params.keySet()) {
                    httpBuilder.addQueryParameter(key,params.get(key));
                }
                builder.method(request.method(),request.body()).url(httpBuilder.build()).build();
            }
        }
        request = builder.build();
        return chain.proceed(request);
    }

    public void setParams(Map<String, String> params) {
        if (this.params != null) this.params.clear();
        this.params = params;
    }
}
