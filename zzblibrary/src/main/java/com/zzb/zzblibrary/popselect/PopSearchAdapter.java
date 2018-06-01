package com.zzb.zzblibrary.popselect;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.base.MyBaseAdapter;

/**
 * 作者： 张梓彬
 * 日期： 2017/10/11 0011
 * 时间： 上午 10:17
 * 描述： popwindow选择列表适配器
 */
public class PopSearchAdapter extends MyBaseAdapter<String> {

    public PopSearchAdapter(Context context) {
        super(context);
    }

    public void addStringData(String[] text){
        list.clear();
        for (String str:text) {
            list.add(str);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_serach_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        String searchName = list.get(i);
        viewHolder.tvSearch.setText(searchName);
        return view;
    }

    static class ViewHolder {
        TextView tvSearch;

        ViewHolder(View view) {
            tvSearch = view.findViewById(R.id.tv_search);
        }
    }
}
