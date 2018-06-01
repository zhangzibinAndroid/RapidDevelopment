package com.zzb.myalllibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.myalllibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 张梓彬 on 2018/1/9 0009.
 */

public class CeHuaAdapter extends RecycleViewDemoAdapter<String,CeHuaAdapter.ViewHolder> {
    public CeHuaAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cehua, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        AutoUtils.autoSize(view);
        view.setTag(viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.btnTop)
        Button btnTop;
        @BindView(R.id.btnUnRead)
        Button btnUnRead;
        @BindView(R.id.btnDelete)
        Button btnDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
