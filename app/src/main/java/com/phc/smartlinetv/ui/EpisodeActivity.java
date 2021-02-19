package com.phc.smartlinetv.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.base.Strings;
import com.phc.smartlinetv.R;
import com.phc.smartlinetv.databinding.ActivityEpisodeBinding;
import com.phc.smartlinetv.ui.adapter.EpisodeList;
import com.phc.smartlinetv.utils.HttpUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

public class EpisodeActivity extends AppCompatActivity {
    private static final String TAG = "EpisodeActivity";

    private ActivityEpisodeBinding mBinding;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEpisodeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        webUrl = getIntent().getStringExtra("url");
        onSwitch();
    }

//    private void onInto() {
//        HttpUtils.netWork(webUrl, "GET", null, null).enqueue(
//                new Callback() {
//                    @Override
//                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                        runOnUiThread(() -> {
//                            Toast.makeText(EpisodeActivity.this,
//                                    "网络错误", Toast.LENGTH_SHORT).show();
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(@NotNull Call call, @NotNull Response response)
//                            throws IOException {
//                        Document doc = Jsoup.parse(response.body().string());
//
//                        String info = doc.getElementsByClass(
//                                "item-desc js-open-wrap").text().isEmpty()
//                                ? doc.getElementsByClass(
//                                "des1").text()
//                                : doc.getElementsByClass(
//                                "item-desc js-open-wrap").text();
//
//                        String title = doc.getElementsByClass(
//                                "title-left g-clear").text().isEmpty()
//                                ? doc.getElementsByClass(
//                                "name").first().text()
//                                : doc.getElementsByClass(
//                                "title-left g-clear").text();
//
//                        String img = getIntent().getStringExtra("img");
//
//                        ArrayList<Map<String, String>> maps = new ArrayList<>();
//
//
//                        Elements elementsByClass;
//                        if (doc.getElementsByClass("details-con2-list").size() == 0) {
//                            if (doc.getElementById("stab_1_71") == null) {
//                                elementsByClass = doc.getElementsByClass(
//                                        "details-con2-list");
//                            } else {
//                                elementsByClass = doc.getElementById("stab_1_71")
//                                        .getElementsByTag("div");
//                            }
//                        } else {
//                            elementsByClass = doc.getElementsByClass(
//                                    "details-con2-list");
//                        }
//
//                        if (elementsByClass.size() == 0) {
//                            runOnUiThread(() -> {
//                                Toast.makeText(EpisodeActivity.this,
//                                        "没有结果", Toast.LENGTH_SHORT).show();
//                            });
//                            return;
//                        }
//                        for (Element li : elementsByClass.get(0).getElementsByTag(
//                                "li")) {
//                            HashMap<String, String> map = new HashMap<>();
//                            map.put("number", li.text());
//                            String url = li.getElementsByTag("a")
//                                    .get(0).attr("href");
//                            if (!url.contains("http")) {
//                                url = webUrl + url;
//                            }
//                            map.put("url", url);
//                            maps.add(map);
//                        }
//                        Log.i(TAG, "onResponse: " + maps.toString());
//
//
//                    }
//
//
//                }
//        );
//    }

    private void onSwitch() {
        String tvn = webUrl.split("/")[webUrl.split("/").length - 1];
        String tvUrl = "http://t.mtyee.com/ne2/s" + tvn + ".js";
        HttpUtils.netWork(tvUrl,
                "GET", null, null)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call,
                                          @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call,
                                           @NotNull Response response)
                            throws IOException {
                        String js = response.body().string();
                        Log.i(TAG, "onResponse: " + js);
                        String[] split = js.split(";");
                        Map<String, String> tvLis = new HashMap<>();
                        List<Map<String, String>> tvAll = new ArrayList<>();

                        for (String s : split) {
                            if (s.contains("playarr") && s.contains("http")) {
                                String[] sp1 = s.split("=\"");
                                String number = sp1[0];
                                String url = sp1[1].split(",")[0];
                                tvLis.put(number, url);

                                Map<String, String> m = new HashMap<>();
                                m.put("number", number.replace("playarr", "路线"));
                                m.put("url", url);
                                tvAll.add(m);
                            }
                        }
                        Log.i(TAG, "onResponse: " + tvLis.toString());


                        runOnUiThread(() -> {
                            mBinding.rvList.setLayoutManager(new StaggeredGridLayoutManager(
                                    4, StaggeredGridLayoutManager.VERTICAL));
                            mBinding.rvList.setAdapter(
                                    new EpisodeList(mMap -> {
                                        //判断是否为m3u8
                                        String s = mMap.get("url");
                                        if (s.contains("m3u8")) {
                                            PlayerFactory.setPlayManager(Exo2PlayerManager.class);
                                        } else {
                                            PlayerFactory.setPlayManager(IjkPlayerManager.class);
                                        }
                                        mBinding.ioTv.setUp(
                                                s, false, mMap.get("number"));
                                        mBinding.ioTv.setShowFullAnimation(true);
                                        mBinding.ioTv.setAutoFullWithSize(true);
                                        mBinding.ioTv.getFullscreenButton().setOnClickListener(v ->
                                                mBinding.ioTv.startWindowFullscreen(
                                                        v.getContext(), false, true));
                                        mBinding.ioTv.startPlayLogic();
                                    }, tvAll));
                        });
//
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

}