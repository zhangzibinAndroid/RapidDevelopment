package com.zzb.zzblibrary.utils;

import android.content.Context;
import android.widget.Toast;

import com.zzb.zzblibrary.widget.XToast7;

/**
 * Toast工具
 */
public class ToastUtils {
    private static XToast7 toast = null;
    public static boolean isShow = true;

    /**
     * application中调用，开启或者关闭Toast
     */
    public static void setToastShow(boolean isShow) {
        ToastUtils.isShow = isShow;
    }

    private ToastUtils() {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            showMessage(context, message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int message) {
        if (isShow)
            showMessage(context, message + "", Toast.LENGTH_LONG);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, CharSequence message) {
        if (isShow)
            showMessage(context, message, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int message) {
        if (isShow)
            showMessage(context, message + "", Toast.LENGTH_SHORT);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            showMessage(context, message + "", duration);
    }

    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            showMessage(context, message + "", duration);
    }

    private static void showMessage(final Context context, final CharSequence message, final int time) {
        if (toast == null) {
            toast = XToast7.makeText(context.getApplicationContext(), message, time);
        } else {
            toast.setText(message.toString());
        }
        toast.show();
    }

    /**
     * 关闭当前Toast
     */
    public static void cancelCurrentToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
