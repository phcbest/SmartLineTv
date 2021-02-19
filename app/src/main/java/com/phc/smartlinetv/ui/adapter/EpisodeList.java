package com.phc.smartlinetv.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phc.smartlinetv.R;

import java.util.List;
import java.util.Map;

/**
 * 版权：没有版权 看得上就用
 *
 * @author peng
 * 创建日期：2021/2/18 20
 * 描述：
 */
public class EpisodeList extends RecyclerView.Adapter<EpisodeList.ViewHolder> {

    public interface SwitchWebView {
        void onSwitch(Map<String, String> mMap);
    }

    SwitchWebView mSwitchWebView;

    List<Map<String, String>> mMaps;

    public EpisodeList(SwitchWebView switchWebView, List<Map<String, String>> maps) {
        mSwitchWebView = switchWebView;
        mMaps = maps;
    }

    private View mView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = View.inflate(parent.getContext(), R.layout.item_episode_list, null);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Map<String, String> map = mMaps.get(position);
        holder.tvItem.setText(map.get("number"));
        holder.tvItem.setOnClickListener(v -> {
            mSwitchWebView.onSwitch(map);
        });
    }

    @Override
    public int getItemCount() {
        return mMaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = (Button) itemView.findViewById(R.id.tv_item);
        }
    }
}
