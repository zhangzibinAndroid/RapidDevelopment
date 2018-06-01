package com.zzb.myalllibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.callutils.OnClickCallBack;
import com.zzb.zzblibrary.utils.ToastUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

public class TitleActivity extends BaseViewActivity {
    private TextView text1;
    private int imageHeight;

    private RelativeLayout relayout;



    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {
        titleBar.setTitleBar("滑动",R.drawable.ic_back,AutoTitleBar.DISMISS_IMAGE, new OnClickCallBack() {
            @Override
            public void OnClickLeft() {
                ToastUtils.show(mContext,"返回");
            }

            @Override
            public void OnClickRight() {

            }
        });

        titleBar.setTitleColor(Color.GREEN);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_title;
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        text1 = (TextView) findViewById(R.id.text1);
//        initNetsedScrollTitle(text1, Color.RED);
        initScrollTitle(text1, Color.RED);
    }


    @Override
    protected boolean showScrollTitle() {
        return true;
    }




}
