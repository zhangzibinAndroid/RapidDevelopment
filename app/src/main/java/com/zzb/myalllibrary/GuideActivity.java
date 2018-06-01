package com.zzb.myalllibrary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.callutils.OnClickCallBack;
import com.zzb.zzblibrary.guide.GuideView;
import com.zzb.zzblibrary.utils.SPUtils;
import com.zzb.zzblibrary.utils.StatusBar;
import com.zzb.zzblibrary.utils.ToastUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

import butterknife.BindView;

public class GuideActivity extends BaseViewActivity {
    private int[] resource = new int[]{R.drawable.welcome1, R.drawable.welcome4, R.drawable.welcome3, R.drawable.welcome4};

    @BindView(R.id.btn_tiyan)
    Button btnTiyan;

    @BindView(R.id.guideViews)
    GuideView guideViews;
    @BindView(R.id.img_start)
    ImageView img_start;

    private int KIND = 0;

    private static final int GUIDE = 0; //引导页
    private static final int START = 1; //启动页

    private Handler handler = new Handler();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {
        titleBar.setTitleBar("标题", R.drawable.ic_back, AutoTitleBar.DISMISS_IMAGE, new OnClickCallBack() {
            @Override
            public void OnClickLeft() {
                ToastUtils.show(mContext,"点击了11");
            }

            @Override
            public void OnClickRight() {
                ToastUtils.show(mContext,"点击了");
            }
        });

        titleBar.setTextLeftVisibility(true);

        titleBar.setRightText("添加试卷");
        titleBar.setOnRightTextClickListener(new AutoTitleBar.OnRightTextClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext,"点击》》》》》");
            }
        });
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
        layParent.setBackgroundColor(Color.RED);
        StatusBar.setStatusBar(GuideActivity.this, true);

        KIND = (int) SPUtils.get(mContext, "kinds", GUIDE);
        if (KIND == GUIDE) {
            //需要写在setData前面,修改状态栏，保持一致颜色
            guideViews.setOnScrollChangedListerer(new GuideView.OnScrollChangedListerer() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            layParent.setBackgroundColor(Color.RED);
                            StatusBar.setStatusBar(GuideActivity.this, true);
                            break;

                        case 1:
                            layParent.setBackgroundColor(Color.WHITE);
                            StatusBar.setStatusBar(GuideActivity.this, true);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            guideViews.setData(getSupportFragmentManager(), resource, R.drawable.ic_point_select, R.drawable.ic_point_unselect, btnTiyan, GuideView.pointDefaultHeight);

        } else {
            img_start.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        }
    }

    @Override
    protected void initView() {
        btnTiyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(mContext, "kinds", START);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
