package com.zzb.myalllibrary;

import android.content.Intent;
import android.os.Bundle;

import com.zzb.myalllibrary.fragment.SecondsFragment;
import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.swipback.SwipeBackHelper;
import com.zzb.zzblibrary.utils.FragmentUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

public class HomeActivity extends BaseViewActivity {
    private SecondsFragment secondsFragment;


    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
//        HightBottom.assistActivity(findViewById(android.R.id.content));
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(false);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void initView() {
        secondsFragment = new SecondsFragment();
        FragmentUtils.addFragment(getSupportFragmentManager(), secondsFragment, R.id.framLayout);
    }


    @Override
    protected boolean enableRightSliding() {
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
