package com.zzb.zzblibrary.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.zzblibrary.R;

/**
 * 作者： 张梓彬
 * 日期： 2018/3/28 0028
 * 时间： 上午 9:51
 * 描述： 自定义toast样式
 */

public class XToast7 {

    private Toast mToast;
    private TextView textView;

    private XToast7(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        textView = (TextView) v.findViewById(R.id.tv_toast);
        AutoUtils.autoSize(textView);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static XToast7 makeText(Context context, CharSequence text, int duration) {
        return new XToast7(context, text, duration);
    }

    public void setText(String msg){
        textView.setText(msg);
    }


    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void cancel(){
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
