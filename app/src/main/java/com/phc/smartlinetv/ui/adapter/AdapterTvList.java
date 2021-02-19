package com.phc.smartlinetv.ui.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.phc.smartlinetv.R;
import com.phc.smartlinetv.bean.SearchBean;
import com.phc.smartlinetv.ui.EpisodeActivity;

import java.util.List;

/**
 * 版权：没有版权 看得上就用
 *
 * @author peng
 * 创建日期：2021/2/17 20
 * 描述：
 */
public class AdapterTvList extends RecyclerView.Adapter<AdapterTvList.ViewHolder> {
    private static final String TAG = "AdapterTvList";

    List<SearchBean> searchBeans;
    private View mView;

    public AdapterTvList(List<SearchBean> searchBeans) {
        this.searchBeans = searchBeans;
    }

    @NonNull
    @Override
    public AdapterTvList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = View.inflate(parent.getContext(), R.layout.item_tv_list, null);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTvList.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        SearchBean searchBean = searchBeans.get(holder.getLayoutPosition());
        Glide.with(mView.getContext()).load(searchBean.getThumb())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.ivTvFace);
        holder.tvTitle.setText(searchBean.getTitle());
        holder.tvInfo.setText("连载至：" + searchBean.getLianzaijs() + "\n" +
                "年代：" + searchBean.getTime() + "\n" +
                "角色：" + searchBean.getStar()
        );
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), EpisodeActivity.class);
                intent.putExtra("url", searchBean.getUrl());
                intent.putExtra("img", searchBean.getThumb());
                Log.i(TAG, "onClick: " + position + searchBean.toString());
                mView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return searchBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardView;
        ImageView ivTvFace;
        MaterialTextView tvTitle;
        MaterialTextView tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView = (MaterialCardView) itemView.findViewById(R.id.materialCardView);
            ivTvFace = (ImageView) itemView.findViewById(R.id.iv_tv_face);
            tvTitle = (MaterialTextView) itemView.findViewById(R.id.tv_title);
            tvInfo = (MaterialTextView) itemView.findViewById(R.id.tv_info);
        }
    }
}
