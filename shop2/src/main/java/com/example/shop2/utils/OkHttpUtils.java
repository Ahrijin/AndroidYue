package com.example.shop2.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    private OkHttpClient mOkHttpClient;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
    }

    public static OkHttpUtils getInstance() {
        return OkHttpHolder.utils;
    }

    static class OkHttpHolder {
        private static final OkHttpUtils utils = new OkHttpUtils();
    }


    //封装同步的Get请求方式
    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response execute = mOkHttpClient.newCall(request).execute();
        return execute.body().string();
    }
}
