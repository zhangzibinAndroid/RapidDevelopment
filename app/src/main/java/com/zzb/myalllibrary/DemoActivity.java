package com.zzb.myalllibrary;

import android.os.Bundle;
import android.view.View;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.dialogfinish.DialogSample;
import com.zzb.zzblibrary.permission.OnPermissionsListener;
import com.zzb.zzblibrary.permission.Permission;
import com.zzb.zzblibrary.permission.XPermission;
import com.zzb.zzblibrary.utils.JumpActivityUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

import butterknife.OnClick;

public class DemoActivity extends BaseViewActivity {
    private JumpActivityUtils jumpActivityUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {
        titleBar.setTitleBar("案例", AutoTitleBar.DISMISS_IMAGE, AutoTitleBar.DISMISS_IMAGE, null);
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
        jumpActivityUtils = new JumpActivityUtils(mContext);
    }

    @Override
    protected void initView() {
        XPermission.getPermissions(mContext, Permission.STORAGE, true, true, new OnPermissionsListener() {
            @Override
            public void missPermission(String[] permissions) {

            }
        });

    }

    @OnClick({R.id.btn_HttpDemoActivity, R.id.btn_MainActivity, R.id.btn_HomeActivity, R.id.btn_MenuViewpagerActivity, R.id.btn_RefreshActivity, R.id.btn_TakePhotoActivity, R.id.btn_GuideActivity, R.id.btn_SlidingBackActivity, R.id.btn_TitleActivity, R.id.btn_SelectVideoAndPicActivity, R.id.btn_ButtonSampleActivity, R.id.btn_DialogSample, R.id.btn_XSpinnerActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_HttpDemoActivity:
                jumpActivityUtils.jumpActivityRight(HttpDemoActivity.class);
                break;
            case R.id.btn_MainActivity:
                jumpActivityUtils.jumpActivityRight(MainActivity.class);
                break;
            case R.id.btn_HomeActivity:
                jumpActivityUtils.jumpActivityRight(HomeActivity.class);

                break;
            case R.id.btn_MenuViewpagerActivity:
                jumpActivityUtils.jumpActivityRight(MenuViewpagerActivity.class);

                break;
            case R.id.btn_RefreshActivity:
                jumpActivityUtils.jumpActivityRight(RefreshActivity.class);

                break;
            case R.id.btn_TakePhotoActivity:
                jumpActivityUtils.jumpActivityRight(TakePhotoActivity.class);

                break;
            case R.id.btn_GuideActivity:
                jumpActivityUtils.jumpActivityRight(MainActivity.class);

                break;
            case R.id.btn_SlidingBackActivity:
                jumpActivityUtils.jumpActivityRight(SlidingBackActivity.class);

                break;
            case R.id.btn_TitleActivity:
                jumpActivityUtils.jumpActivityRight(TitleActivity.class);

                break;
            case R.id.btn_SelectVideoAndPicActivity:
                jumpActivityUtils.jumpActivityRight(SelectVideoAndPicActivity.class);

                break;
            case R.id.btn_ButtonSampleActivity:
                jumpActivityUtils.jumpActivityRight(ButtonSampleActivity.class);

                break;
            case R.id.btn_DialogSample:
                jumpActivityUtils.jumpActivityRight(DialogSample.class);

                break;
            case R.id.btn_XSpinnerActivity:
                jumpActivityUtils.jumpActivityRight(XSpinnerActivity.class);
                break;
        }
    }
}
