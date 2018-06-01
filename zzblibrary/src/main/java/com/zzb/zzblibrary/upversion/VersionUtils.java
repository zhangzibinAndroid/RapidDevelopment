package com.zzb.zzblibrary.upversion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.zzb.zzblibrary.fileprovider.FileProvider7;
import com.zzb.zzblibrary.httputils.OkHttpUtils;
import com.zzb.zzblibrary.httputils.callback.FileCallBack;
import com.zzb.zzblibrary.utils.ToastUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 作者： 张梓彬
 * 日期： 2017/11/17 0017
 * 时间： 上午 11:02
 * 描述： 版本更新
 */

public class VersionUtils {
    // 更新版本要用到的一些信息
    private UpdateInfo info;
    private VersionDialog versionDialog;
    private VersionShowMessageDialog versionShowMessageDialog;
    private Activity mContext;
    public String saveFolder = "upVersion";
    private String apkName = "debug.apk";
    private String path = "";
    private String version;
    private String description;
    private boolean isBackground = false;//是否在后台升级
    private int icon;//应用图标

    private static final int NO_1 = 0x1;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private NotificationManager notificationManager;
    private int allSize;
    private boolean isSuccess = false;
    private boolean isShowNotification = false;//是否展示Notification通知栏

    public VersionUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * @param background         是否在后台升级，true是false否，默认否
     * @param icon               应用图标 R.mipmap.icon
     * @param isShowNotification 是否展示Notification通知栏
     */
    public void setBackground(boolean background, int icon, boolean isShowNotification) {
        isBackground = background;
        this.icon = icon;
        this.isShowNotification = isShowNotification;
    }

    /**
     * @param background 是否在后台升级，true是false否，默认否
     * @param icon       应用图标 R.mipmap.icon
     */
    public void setBackground(boolean background, int icon) {
        isBackground = background;
        this.icon = icon;
    }

    /**
     * @param version     版本号
     * @param description 升级描述
     * @param url         apk下载地址
     * @param apkName     apk名称
     * @param saveFolder  下载的apk保存的文件夹，填个名字就行了：如 "文件夹"
     */
    public void getUpVersion(final String version, final String description, final String url, final String apkName, final String saveFolder) {
        try {
            this.apkName = apkName;
            this.saveFolder = saveFolder;
            this.version = version;
            this.description = description;
            path = Environment.getExternalStorageDirectory() + "/" + saveFolder;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            info = new UpdateInfo(version, description, url);
            versionHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("HandlerLeak")
    private Handler versionHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 如果有更新就提示
            if (isNeedUpdate()) {
                showUpdateDialog();
            }
        }
    };


    private void showUpdateDialog() {
        versionShowMessageDialog = new VersionShowMessageDialog("有新版本：" + version, description);
        versionShowMessageDialog.setCancelable(false);
        versionShowMessageDialog.show(mContext.getFragmentManager(), "VersionShowMessageDialog");
        versionShowMessageDialog.setOnClickCallBack(new VersionShowMessageDialog.OnClickCallBack() {
            @Override
            public void onClick(View view) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    versionShowMessageDialog.dismiss();

                    if (isBackground) {
                        //后台升级
                        downFileBackground(info.getUrl());
                    } else {
                        //前台升级
                        downFile(info.getUrl());
                    }
                } else {
                    ToastUtils.show(mContext, "SD卡不可用，请插入SD卡");
                }
            }
        });

    }


    private boolean isNeedUpdate() {
        String v = info.getVersion(); // 最新版本的版本号
        if (v.equals(getVersion())) {
            return false;
        } else {
            return true;
        }
    }

    // 获取当前版本的版本号
    private String getVersion() {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

    /**
     * @param url 下载地址
     */
    private void downFile(final String url) {
        versionDialog = new VersionDialog();
        versionDialog.setCancelable(false);
        versionDialog.show(mContext.getFragmentManager(), "VersionDialog");
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(path, apkName) {
            @Override
            public void onResponse(File response, int id) {
                down();
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                int mtotal = (int) total;
                versionDialog.addAllProgress(mtotal);
                versionDialog.addProgtess((int) (mtotal * progress));
                versionDialog.setCancelable(false);
                versionDialog.setProgress((int) (100 * progress));
            }

            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                versionDialog.dismiss();
                ToastUtils.show(mContext, "更新失败，请稍后再试!");
            }
        });
    }

    private void down() {
        versionHandler.post(new Runnable() {
            public void run() {
                if (!isBackground) {
                    versionDialog.cancleProgress();
                    versionDialog.dismiss();
                }

                update();
            }
        });
    }

    private void update() {
        File file = new File(path, apkName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(mContext, intent, "application/vnd.android.package-archive", file, true);
        mContext.startActivity(intent);
    }


    private void downFileBackground(final String url) {
        //是否展示Notification通知框
        if (isShowNotification) {
            isSuccess = false;
            builder = new NotificationCompat.Builder(mContext);
            builder.setSmallIcon(icon);
            builder.setContentTitle("正在更新");
            builder.setAutoCancel(true);
            builder.setOnlyAlertOnce(true);
            // 需要VIBRATE权限
            builder.setDefaults(Notification.DEFAULT_VIBRATE);
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            notification = new Notification();
        }

        OkHttpUtils.get().url(url).build().execute(new FileCallBack(path, apkName) {
            @Override
            public void onResponse(File response, int id) {
                notificationManager.cancelAll();
                isSuccess = true;
                down();
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                if (isShowNotification) {
                    if (!isSuccess) {
                        allSize = (int) total;

                        builder.setContentText(String.valueOf(getFileSize((int) (allSize * progress))) + "/" + String.valueOf(getFileSize((int) allSize)));
                        builder.setProgress((int) 100, (int) (100 * progress), false);
                        notification = builder.build();
                        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NO_1, notification);
                    }
                }
            }

            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                isSuccess = false;
                ToastUtils.show(mContext, "更新失败，请稍后再试!");
            }
        });
    }


    //字节转换为字符串，并取2位小数
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private String getFileSize(long filesize) {
        StringBuffer buffer = new StringBuffer();
        if (filesize < 1024) {
            buffer.append(filesize);
            buffer.append(" B");
        } else if (filesize < (1024 * 1024)) {
            buffer.append(decimalFormat.format((double) filesize / 1024));
            buffer.append(" KB");
        } else if (filesize < (1024 * 1024 * 1024)) {
            buffer.append(decimalFormat.format((double) filesize / (1024 * 1024)));
            buffer.append(" MB");
        } else {
            buffer.append(decimalFormat.format((double) filesize / (1024 * 1024 * 1024)));
            buffer.append(" GB");
        }
        return buffer.toString();

    }
}
