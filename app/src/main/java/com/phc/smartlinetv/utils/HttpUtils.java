package com.phc.smartlinetv.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 版权：没有版权 看得上就用
 *
 * @author peng
 * 创建日期：2021/2/17 13
 * 描述：
 */
public class HttpUtils {
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=utf-8");


    public static Call netWork(String u, String method, Map<String, String> headers, RequestBody requestBody) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(u).method(method, requestBody);
        for (String s : headers.keySet()) {
            requestBuilder.addHeader(s, Objects.requireNonNull(headers.get(s)));
        }
        return client.newCall(requestBuilder.build());
    }
}
