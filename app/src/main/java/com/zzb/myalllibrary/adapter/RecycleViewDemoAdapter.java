package com.zzb.myalllibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/9 0009
 * 时间： 下午 1:10
 * 描述： RecycleViewAdapetr基类适配器
 */

public abstract class RecycleViewDemoAdapter<T ,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private List<T> list;
    protected Context context;

    public RecycleViewDemoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }


    public void addData(T t){
        list.add(t);
    }

    public void addData(List<T> dataList){
        list.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
