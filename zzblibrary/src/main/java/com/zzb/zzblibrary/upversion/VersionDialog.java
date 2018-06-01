package com.zzb.zzblibrary.upversion;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.progress.HorizontalProgressBarWithNumber;

import java.text.DecimalFormat;

/**
 * 作者： 张梓彬
 * 日期： 2017/12/8 0008
 * 时间： 下午 2:39
 * 描述： app更新弹窗
 */

public class VersionDialog extends DialogFragment {
    private View view;
    private HorizontalProgressBarWithNumber horizontalProgressBarWithNumber;
    private TextView tv_progress;
    private TextView tv_allprogress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 设置背景透明
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.layout_version_progress, container, false);
        initViewId(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViewId(View view) {
        horizontalProgressBarWithNumber = view.findViewById(R.id.horizontalProgressBarWithNumber);
        tv_progress = view.findViewById(R.id.tv_progress);
        tv_allprogress = view.findViewById(R.id.tv_allprogress);
    }



    public void addProgtess(int progress){
        tv_progress.setText(String.valueOf(getFileSize(progress)));
    }

    public void addAllProgress(int allProgress){
        horizontalProgressBarWithNumber.setMax(100);
        tv_allprogress.setText(String.valueOf(getFileSize(allProgress)));
    }

    public void setProgress(int progress){
        horizontalProgressBarWithNumber.setProgress(progress);
    }

    public void cancleProgress(){
        horizontalProgressBarWithNumber.cancelLongPress();
    }


    //字节转换为字符串，并取2位小数
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public  String getFileSize(long filesize){

        StringBuffer buffer = new StringBuffer();
        if(filesize<1024){
            buffer.append(filesize);
            buffer.append(" B");
        }else if(filesize<(1024*1024)){
            buffer.append(decimalFormat.format((double) filesize/1024));
            buffer.append(" KB");
        }else if(filesize<(1024*1024*1024)){
            buffer.append(decimalFormat.format((double) filesize/(1024*1024)));
            buffer.append(" MB");
        }else{
            buffer.append(decimalFormat.format((double) filesize/(1024*1024*1024)));
            buffer.append(" GB");
        }
        return buffer.toString();

    }
}
