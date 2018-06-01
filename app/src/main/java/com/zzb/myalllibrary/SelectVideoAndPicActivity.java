package com.zzb.myalllibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.media.MediaSelectUtils;
import com.zzb.zzblibrary.media.PickerConfig;
import com.zzb.zzblibrary.media.entity.Media;
import com.zzb.zzblibrary.permission.OnPermissionsListener;
import com.zzb.zzblibrary.permission.Permission;
import com.zzb.zzblibrary.permission.XPermission;
import com.zzb.zzblibrary.widget.AutoTitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectVideoAndPicActivity extends BaseViewActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;

    private MediaSelectUtils mediaSelectUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_select_video_and_pic;
    }

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {
        titleBar.setVisibility(View.GONE);
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
        XPermission.getPermissions(mContext, Permission.STORAGE, true, true, new OnPermissionsListener() {
            @Override
            public void missPermission(String[] permissions) {

            }
        });

        mediaSelectUtils = new MediaSelectUtils();
    }

    @Override
    protected void initView() {
        mediaSelectUtils.setOnFileCallBack(new MediaSelectUtils.OnFileCallBack() {
            @Override
            public void OnFile(ArrayList<Media> list, List<File> files, List<String> paths) {
                String text = "";
                for (int i = 0; i < paths.size(); i++) {
                    text = text + "path: " + paths.get(i) + "\n";
                }
                tvContent.setText(text);
            }
        });
    }

    @OnClick(R.id.btn_select)
    public void onViewClicked() {
        mediaSelectUtils.takeMediaListInActivity(mContext, PickerConfig.PICKER_IMAGE_VIDEO, 15);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaSelectUtils.onActivityResult(requestCode, resultCode, data);
    }


}
