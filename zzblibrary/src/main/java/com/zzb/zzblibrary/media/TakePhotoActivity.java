package com.zzb.zzblibrary.media;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.zzb.zzblibrary.imageselect.ImageBaseActivity;
import com.zzb.zzblibrary.media.entity.Media;
import com.zzb.zzblibrary.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class TakePhotoActivity extends ImageBaseActivity {
    Uri NuriForFile ;
    File mTmpFile = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            mTmpFile =  createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            NuriForFile=FileProvider.getUriForFile(this,this.getPackageName() + ".share", mTmpFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, NuriForFile);
            startActivityForResult(intent, 100);
        } else {
            if(mTmpFile != null && mTmpFile.exists()) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                startActivityForResult(intent, 101);
            }else{
                ToastUtils.show(this,"take error");
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<Media> medias=new ArrayList<>();
        if(requestCode==100||requestCode==101&&resultCode==RESULT_OK){
            if(mTmpFile.length()>0) {
                Media media = new Media(mTmpFile.getPath(), mTmpFile.getName(), 0, 1, mTmpFile.length(), 0, "");
                medias.add(media);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, medias);
                setResult(PickerConfig.RESULT_CODE, intent);
            }
            finish();
        }
    }

    
    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}
