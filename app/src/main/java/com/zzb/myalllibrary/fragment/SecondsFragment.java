package com.zzb.myalllibrary.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.zzb.myalllibrary.R;
import com.zzb.zzblibrary.base.BaseViewFragment;
import com.zzb.zzblibrary.media.MediaSelectUtils;
import com.zzb.zzblibrary.media.PickerConfig;
import com.zzb.zzblibrary.media.entity.Media;
import com.zzb.zzblibrary.utils.LogUtils;
import com.zzb.zzblibrary.utils.ToastUtils;
import com.zzb.zzblibrary.utils.takephoto.TakePhotoFragmentUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondsFragment extends BaseViewFragment implements TakePhotoFragmentUtils.OnImageFileCallBack {

    @BindView(R.id.img_head)
    ImageView imgHead;

    private TakePhotoFragmentUtils takePhotoUtils;
    private MediaSelectUtils mediaSelectUtils;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_seconds;
    }



    @Override
    protected void onBoundView(Bundle savedInstanceState, View childrenView) {
        takePhotoUtils = new TakePhotoFragmentUtils(this);
        takePhotoUtils.setOnImageFileCallBack(this);

        mediaSelectUtils = new MediaSelectUtils();
    }

    @Override
    protected void initView() {
        mediaSelectUtils.setOnFileCallBack(new MediaSelectUtils.OnFileCallBack() {
            @Override
            public void OnFile(ArrayList<Media> list, List<File> files, List<String> paths) {
                LogUtils.e("paths: "+paths.size());
                ToastUtils.show(mContext,"paths: "+paths.size());
            }
        });
    }

    @OnClick(R.id.img_head)
    public void onViewClicked() {
//        takePhotoUtils.takePhoto();
        mediaSelectUtils.takeMediaListInFragment(this, PickerConfig.PICKER_IMAGE_VIDEO,10);
//        mediaSelectUtils.takeMediaListInChildFragment(this, PickerConfig.PICKER_IMAGE_VIDEO,10);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaSelectUtils.onActivityResult(requestCode, resultCode, data);
//        takePhotoUtils.onActivityResultWithCut(requestCode, resultCode, data);
    }

    @Override
    public void OnImageFile(File file, String imagePath) {
    }

    @Override
    public void onImageFiles(List<File> files, List<String> imagePaths) {

    }


}
