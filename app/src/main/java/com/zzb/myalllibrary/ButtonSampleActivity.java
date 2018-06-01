package com.zzb.myalllibrary;

import android.os.Bundle;
import android.view.View;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.widget.AutoTitleBar;
import com.zzb.zzblibrary.widget.StateImageView;

import butterknife.BindView;

public class ButtonSampleActivity extends BaseViewActivity {

    @BindView(R.id.image)
    StateImageView image;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_button_sample;
    }

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {

    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
