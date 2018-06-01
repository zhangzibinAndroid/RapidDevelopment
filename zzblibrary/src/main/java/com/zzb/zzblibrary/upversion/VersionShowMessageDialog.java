package com.zzb.zzblibrary.upversion;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zzb.zzblibrary.R;

/**
 * 作者： 张梓彬
 * 日期： 2017/12/8 0008
 * 时间： 下午 4:05
 * 描述： 更新弹窗
 */

public class VersionShowMessageDialog extends DialogFragment {
    private View view;
    private TextView tvVersionTitle;
    private TextView tvDescribe;
    private TextView tv_cancle;
    private TextView tv_confirm;
    public OnClickCallBack onClickCallBack;

    private String version;
    private String description;

    public VersionShowMessageDialog() {
    }

    @SuppressLint("ValidFragment")
    public VersionShowMessageDialog(String version, String description) {
        this.version = version;
        this.description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 设置背景透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.layout_version_dialog, container, false);
        initViewId(view);
        initView();
        return view;
    }

    private void initViewId(View view) {
        tvVersionTitle = view.findViewById(R.id.tvVersionTitle);
        tvDescribe = view.findViewById(R.id.tvDescribe);
        tv_cancle = view.findViewById(R.id.tv_cancle);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tvVersionTitle.setText(version);
        tvDescribe.setText(description);
    }

    private void initView() {
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    onClickCallBack.onClick(v);
                }catch (Exception e){}
            }
        });
    }

    public interface OnClickCallBack{
        void onClick(View view);
    }

    public void setOnClickCallBack(OnClickCallBack onClickCallBack) {
        this.onClickCallBack = onClickCallBack;
    }
}
