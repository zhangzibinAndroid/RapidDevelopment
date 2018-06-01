package com.zzb.zzblibrary.utils.takephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.zzb.zzblibrary.dialogfinish.dialog.ActionSheetDialog;
import com.zzb.zzblibrary.fileprovider.FileProvider7;
import com.zzb.zzblibrary.imageselect.utils.ImageSelectorUtils;
import com.zzb.zzblibrary.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/21 0021
 * 时间： 下午 2:39
 * 描述： 拍照，调用相册工具类
 */

public class TakePhotoActivityUtils implements OnActivityCallBack.OnActivityResultCallBackWithCut, OnActivityCallBack.OnActivityResultCallBack {
    private Activity context;

    private static final int REQUEST_CODE_TAKE_PHOTO = 330;
    private static final int CHOOSE_PHOTO = 331;
    private Uri fileUri;
    private File saveFile;
    private String savePath;
    private ArrayList<String> images = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private String filename;

    public TakePhotoActivityUtils(Context context) {
        this.context = (Activity) context;
        savePath = Environment.getExternalStorageDirectory() + "/takePhotos";
        saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
    }


    public TakePhotoActivityUtils(Context context, String fir) {
        this.context = (Activity) context;
        savePath = Environment.getExternalStorageDirectory() + "/" + fir;
        saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
    }

    //弹出dialog选择框
    public void takePhoto() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                takePhotoNoCompress();
                            }
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                choosePhone();
                            }
                        }).show();
    }


    /**
     * 直接调用拍照功能，无其他
     */
    public void takeCamera() {
        takePhotoNoCompress();
    }


    /**
     * 直接调用相册，选择单张图片，无其他
     */
    public void chooseAlbum() {
        choosePhone();
    }


    /**
     * 直接调用相册，选择多张图片，无其他
     */
    public void chooseAlbumMore() {
        choosePhoneMore();
    }


    //选择多张图片
    public void takePhotoMore() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                takePhotoNoCompress();
                            }
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                choosePhoneMore();
                            }
                        }).show();
    }


    //FileProvider7在android7.0上拍照剪裁
    private void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
            File file = new File(savePath, filename);
            fileUri = FileProvider7.getUriForFile(context, file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            context.startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    private void choosePhone() {
       /* Intent intent;
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, CHOOSE_PHOTO);*/
        ImageSelectorUtils.openPhoto(context, CHOOSE_PHOTO, true, 0);


    }

    //选择多张图片
    private void choosePhoneMore() {
        ImageSelectorUtils.openPhoto(context, CHOOSE_PHOTO);
    }


    /**
     * 头像拍照上传剪裁
     **/
    @Override
    public void onActivityResultWithCut(int requestCode, int resultCode, Intent data) {
        images.clear();
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {

            //通知图库更新
            upDatePhoto(new File(savePath + "/" + filename));

            startPhotoZoom(fileUri);
        } else if (resultCode == RESULT_OK && requestCode == CHOOSE_PHOTO) {
            images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            if (images != null) {
                File imageFile = new File(images.get(0));
                Uri uri = fileUri = FileProvider7.getUriForFile(context, imageFile);
                startPhotoZoom(uri);
            }
        } else if (resultCode == RESULT_OK && requestCode == 333) {
            if (data != null) {
                setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
            }
        }
    }


    /**
     * 拍照上传不剪裁
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "";
        File file = null;
        images.clear();
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            path = savePath + "/" + filename;
            //通知图库更新
            upDatePhoto(new File(path));

            images.add(path);
        } else if (resultCode == RESULT_OK && requestCode == CHOOSE_PHOTO) {
            images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            if (images != null) {
                path = images.get(0);
            }
        }

        if (images != null && !path.equals("") && images.size() == 1) {
            file = new File(path);

            onImageFileCallBack.OnImageFile(file, path);
            fileList.clear();
            fileList.add(file);
            onImageFileCallBack.onImageFiles(fileList, images);
        } else if (images != null && !path.equals("") && images.size() > 1) {
            fileList.clear();
            for (int i = 0; i < images.size(); i++) {
                fileList.add(new File(images.get(i)));
            }
            onImageFileCallBack.onImageFiles(fileList, images);
        }
    }


    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            uploadPic(photo);
        }
    }


    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            LogUtils.e("The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, 333);
    }


    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        String imagePath = PhotoUtils.savePhoto(bitmap, savePath, String.valueOf(System.currentTimeMillis()));
        File file = new File(imagePath);
        try {
            onImageFileCallBack.OnImageFile(file, imagePath);
            onImageFileCallBack.onImageFiles(null, null);
        } catch (Exception e) {
        }
    }


    //选择单张图片的时候调用
    public OnImageFileCallBack onImageFileCallBack;

    public interface OnImageFileCallBack {
        void OnImageFile(File file, String imagePath);

        void onImageFiles(List<File> files, List<String> imagePaths);
    }

    public void setOnImageFileCallBack(OnImageFileCallBack onImageFileCallBack) {
        this.onImageFileCallBack = onImageFileCallBack;
    }


    private String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


    //通知图库更新
    private void upDatePhoto(File file) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
    }
}
