package com.zzb.myalllibrary;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.zzb.zzblibrary.imageload.GlideUtil;
import com.zzb.zzblibrary.permission.OnPermissionsListener;
import com.zzb.zzblibrary.permission.XPermission;
import com.zzb.zzblibrary.utils.AppManager;
import com.zzb.zzblibrary.utils.NotificationsUtils;
import com.zzb.zzblibrary.utils.WriteLogUtils;
import com.zzb.zzblibrary.utils.takephoto.TakePhotoActivityUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakePhotoActivity extends AppCompatActivity implements TakePhotoActivityUtils.OnImageFileCallBack {
    private static final String TAG = "TakePhotoActivity";
    @BindView(R.id.img_head)
    ImageView imgHead;
    private TakePhotoActivityUtils photoUtils;

    private String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);
        photoUtils = new TakePhotoActivityUtils(this);
        photoUtils.setOnImageFileCallBack(this);
        AppManager.getInstance().addActivity(this);
        XPermission.getPermissions(this, permission, new OnPermissionsListener() {
            @Override
            public void missPermission(String[] permissions) {

            }
        });

        //通知栏是否开启
        NotificationsUtils.init(this);

        WriteLogUtils.getInstance().writeLog("jiances.txt","启动》》");
    }

    @OnClick(R.id.img_head)
    public void onViewClicked() {
        photoUtils.takePhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoUtils.onActivityResultWithCut(requestCode, resultCode, data);
//        photoUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnImageFile(File file, String imagePath) {
        Log.e(TAG, "OnImageFile: " + file);
        Log.e(TAG, "imagePath: " + imagePath);

       /* CompressImageUtils.fileCompress(imagePath,new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                Log.e(TAG, "isSuccess: " + isSuccess);
                Log.e(TAG, "outfile: " + outfile);
                GlideUtil.getInstance().setCircleView2(imgHead, outfile, 3, Color.WHITE);

            }
        });*/

        GlideUtil.getInstance().setCircleView2(imgHead, imagePath, 3, Color.WHITE);

    }

    @Override
    public void onImageFiles(List<File> files, List<String> imagePaths) {
        Log.e(TAG, "OnImageFile: " + files);
        Log.e(TAG, "imagePath: " + imagePaths);
    }


}
