package com.zzb.zzblibrary.widget.spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.base.MyBaseAdapter;

/**
 * 作者： 张梓彬
 * 日期： 2018/4/9 0009
 * 时间： 下午 3:39
 * 描述： Spinner默认适配器
 */
public class DefaultSpinnerAdapter extends MyBaseAdapter<String> implements SpinnerAdapter {

    public DefaultSpinnerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_spinner_default, null);
        ViewHolder viewHolder = new ViewHolder(view);
        String levelName = list.get(i);
        viewHolder.tvSpinnerItem.setText(levelName);
        AutoUtils.autoSize(view);
        return view;
    }

    static class ViewHolder {
        TextView tvSpinnerItem;

        ViewHolder(View view) {
            tvSpinnerItem = view.findViewById(R.id.tv_spinner_item);
        }
    }
}
