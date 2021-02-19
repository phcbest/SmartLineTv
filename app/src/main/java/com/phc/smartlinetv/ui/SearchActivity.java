package com.phc.smartlinetv.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.phc.smartlinetv.bean.SearchBean;
import com.phc.smartlinetv.databinding.ActivitySearchBinding;
import com.phc.smartlinetv.ui.adapter.AdapterTvList;
import com.phc.smartlinetv.utils.HttpUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author PengHaiChen
 */
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private String mUrl = "159.75.7.49:7211";
    private ActivitySearchBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        onInit();
    }



    private void onInit() {
        String input = getIntent().getStringExtra("search");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Referer", "http://www.feijisu5.com/");
        headers.put("Origin", "http://www.feijisu5.com/");
        headers.put("Accept", "*/*");

        try {
            input = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtils.netWork("http://" + mUrl + "/sssv.php?q=" + input,
                "GET", headers, null).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(SearchActivity.this,
                            e.toString(), Toast.LENGTH_SHORT).show();
                    call.clone();
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                SearchBean[] searchBeans = new Gson().fromJson(
                        Objects.requireNonNull(response.body()).string(), SearchBean[].class);

                runOnUiThread(() -> {
                    mBinding.mRecyclerView.setLayoutManager(
                            new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL));
                    Log.i(TAG, "onResponse: " + Arrays.toString(searchBeans));
                    mBinding.mRecyclerView.setAdapter(new AdapterTvList(Arrays.asList(searchBeans)));
                    call.clone();
                });
            }
        });

    }
}