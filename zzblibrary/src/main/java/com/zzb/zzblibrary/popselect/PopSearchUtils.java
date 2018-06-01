package com.zzb.zzblibrary.popselect;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zzb.zzblibrary.R;

import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/3 0003
 * 时间： 下午 5:35
 * 描述： 选择器初始化
 */

public class PopSearchUtils {
    private static PopSearchUtils instance;
    private Context context;
    private Drawable drawableDown, drawableUp;
    private PopSearchAdapter popSearchAdapter;
    private PopupWindow popMoreWindow;
    private boolean isShowPop;
    private TextView textView;


    private PopSearchUtils(Context context) {
        this.context = context;
        showPopMoreWindow();
        drawableUp = context.getResources().getDrawable(R.drawable.ic_search_up);
        drawableUp.setBounds(0, 0, drawableUp.getMinimumWidth(), drawableUp.getMinimumHeight());
        drawableDown = context.getResources().getDrawable(R.drawable.ic_search_down);
        drawableDown.setBounds(0, 0, drawableDown.getMinimumWidth(), drawableDown.getMinimumHeight());
    }

    public static PopSearchUtils getInstance(Context context) {
        if (instance == null) {
            instance = new PopSearchUtils(context.getApplicationContext());
        }
        if (context == null)
            instance.context = context.getApplicationContext();
        return instance;
    }

    private void showPopMoreWindow() {
        View popview = LayoutInflater.from(context).inflate(R.layout.pop_search_list, null);
        ViewHolder viewHolder = new ViewHolder(popview);
        popSearchAdapter = new PopSearchAdapter(context);
        viewHolder.lvSearch.setAdapter(popSearchAdapter);
        setOnPopStringListCallBack(new OnPopStringListCallBack() {
            @Override
            public void OnPopStringListCall(String[] str) {
                popSearchAdapter.addStringData(str);
            }
        });

        viewHolder.lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popMoreWindow.dismiss();
                String itemName = popSearchAdapter.getList().get(i);
                textView.setText(itemName);
                onClickCallBack.OnClickCall(itemName);

            }
        });


        //WRAP_CONTENT:是.xml中的布局宽、高，wrap_content包裹
        popMoreWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popMoreWindow.setBackgroundDrawable(new BitmapDrawable());
        popMoreWindow.getContentView().setFocusable(true);//为是否可以获得焦点
        //设置可触摸PopupWindow之外的地方关闭
        popMoreWindow.setOutsideTouchable(true);
        //点击其它地方对话框关闭的时候，将背景还原
        popMoreWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                isShowPop = false;
                textView.setCompoundDrawables(null, null, drawableDown, null);
            }
        });

        popMoreWindow.setAnimationStyle(R.style.dialogWindowAnim_style);//设置动画
    }

    private OnPopStringListCallBack onPopStringListCallBack;

    public static interface OnPopStringListCallBack {
        void OnPopStringListCall(String[] str);
    }

    private void setOnPopStringListCallBack(OnPopStringListCallBack onPopStringListCallBack) {
        this.onPopStringListCallBack = onPopStringListCallBack;
    }

    static class ViewHolder {
        ListView lvSearch;
        ViewHolder(View view) {
            lvSearch = view.findViewById(R.id.lv_search);
        }
    }


    public void showListPop(String[] data,TextView view,OnClickCallBack onClickCallBack){
        this.onClickCallBack = onClickCallBack;
        textView = view;
        onPopStringListCallBack.OnPopStringListCall(data);
        popMoreWindow.showAsDropDown(view, 0, 5);
        isShowPop = true;
        if (isShowPop) {
            view.setCompoundDrawables(null, null, drawableUp, null);
        }
    }


    public void showListPop(List<String> data, TextView view, OnClickCallBack onClickCallBack){
        this.onClickCallBack = onClickCallBack;
        String[] str = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            str[i] = data.get(i);
        }

        textView = view;
        onPopStringListCallBack.OnPopStringListCall(str);
        popMoreWindow.showAsDropDown(view, 0, 5);
        isShowPop = true;
        if (isShowPop) {
            view.setCompoundDrawables(null, null, drawableUp, null);
        }
    }


    //弹窗是否打开
    public boolean isShowPop() {
        return isShowPop;
    }

    //选择内容回调出去
    private OnClickCallBack onClickCallBack;

    public interface OnClickCallBack{
        void OnClickCall(String text);
    }


}
