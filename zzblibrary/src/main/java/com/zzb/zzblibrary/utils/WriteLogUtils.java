package com.zzb.zzblibrary.utils;


import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 作者： 张梓彬
 * 日期： 2017/12/25 0025
 * 时间： 上午 10:17
 * 描述： 记录
 */

public class WriteLogUtils {
    private static String path;
    private static WriteLogUtils instance;

    private WriteLogUtils() {
    }

    public static WriteLogUtils getInstance() {
        if (instance == null) {
            instance = new WriteLogUtils();
            if (SDCardUtils.checkSDCardAvailable()) {
                path = Environment.getExternalStorageDirectory() + "/检测";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                LogUtils.e("SD卡不存在!");
            }


        }
        return instance;
    }


    /**
     * @param folder 文件夹
     */
    public static WriteLogUtils getInstance(String folder) {
        if (instance == null) {
            instance = new WriteLogUtils();
            if (SDCardUtils.checkSDCardAvailable()) {
                path = Environment.getExternalStorageDirectory() + "/" + folder;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                LogUtils.e("SD卡不存在!");
            }
        }
        return instance;
    }

    public void writeLog(String fileName, String content) {
        try {
            FileWriter fw = new FileWriter(path + "/" + fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content + "\n");// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
