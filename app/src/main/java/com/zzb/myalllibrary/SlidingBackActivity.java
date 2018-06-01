package com.zzb.myalllibrary;

import android.os.Bundle;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.callutils.OnClickCallBack;
import com.zzb.zzblibrary.swipback.SwipeBackHelper;
import com.zzb.zzblibrary.utils.JumpActivityUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

public class SlidingBackActivity extends BaseViewActivity {

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {
        titleBar.setTitleLayoutColor(getResources().getColor(R.color.actionsheet_red));
        titleBar.setTextLeftVisibility(true);
        titleBar.setTextLeft("哈哈");
        titleBar.setTitleBar("侧滑返回", R.drawable.ic_back, R.drawable.ic_back, new OnClickCallBack() {
            @Override
            public void OnClickLeft() {
                new JumpActivityUtils(mContext).finishActivityLeft();
            }

            @Override
            public void OnClickRight() {

            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sliding_back;
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent((float) 0.2);
    }

    @Override
    protected void initView() {

    }


    @Override
    protected boolean enableRightSliding() {
        return true;
    }
}
