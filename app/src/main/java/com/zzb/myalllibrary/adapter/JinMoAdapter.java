package com.zzb.myalllibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.myalllibrary.R;
import com.zzb.zzblibrary.lateralspreads.AutoSwipeMenuLayout;
import com.zzb.zzblibrary.xrefreshview.recyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JinMoAdapter extends BaseRecyclerAdapter<JinMoAdapter.ViewHolder> {
    private List<String> list;
    private int largeCardHeight, smallCardHeight;


    public JinMoAdapter(Context context) {
        this.list = new ArrayList<>();
        largeCardHeight = dip2px(context, 150);
        smallCardHeight = dip2px(context, 100);
    }

    public void addData(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(String str) {
        this.list.add(str);
    }

    public void addData2(List<String> list2) {
        for (int i = 0; i < list2.size(); i++) {
            this.list.add(list2.get(i));
        }
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cehua, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        AutoUtils.autoSize(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        holder.content.setText(position + "");
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            holder.roll.getLayoutParams().height = position % 2 != 0 ? largeCardHeight : smallCardHeight;
        }
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
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
        @BindView(R.id.roll)
        AutoSwipeMenuLayout roll;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
