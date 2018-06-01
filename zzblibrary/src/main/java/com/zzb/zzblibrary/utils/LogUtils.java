package com.zzb.zzblibrary.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 作者： 张梓彬
 * 日期： 2017/11/13 0013
 * 时间： 上午 11:41
 * 描述： log管理
 */

public class LogUtils {
    public static boolean isOpenLog = true;
    private static String TAG = "TAG";

    public static void setClassName(String tag) {
        TAG = tag;
    }

    public static void setIsOpenLog(boolean isOpenLog) {
        LogUtils.isOpenLog = isOpenLog;
    }

    public static void d(Object object) {
        if (isOpenLog) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(TAG).d(object);
            Logger.clearLogAdapters();
        }
    }


    public static void d(String message, Object... args) {
        if (isOpenLog) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(TAG).d(message, args);
            Logger.clearLogAdapters();
        }
    }


    public static void e(String text) {
        if (isOpenLog) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(TAG).e(text);
            Logger.clearLogAdapters();
        }
    }

    public static void i(String text) {
        if (isOpenLog) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(TAG).i(text);
            Logger.clearLogAdapters();
        }
    }


    public static void json(String text) {
        if (isOpenLog) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(TAG).json(text);
            Logger.clearLogAdapters();
        }
    }

}
