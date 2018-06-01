package com.zzb.zzblibrary.utils;

import android.app.Activity;
import android.content.Intent;

import com.zzb.zzblibrary.R;

/**
 * 作者： 张梓彬
 * 日期： 2017/11/17 0017
 * 时间： 下午 9:31
 * 描述： Activity跳转工具类
 */

public class JumpActivityUtils {
    private Activity mContext;
    //2秒内防止2次点击
    private long delay = 200;
    private static long lastTime;
    private String tag = "JumpActivityUtils";

    public JumpActivityUtils(Activity mContext) {
        this.mContext = mContext;
    }

    /**
     * @param time 多次点击的时间
     * */
    public void setClickTime(long time) {
        this.delay = delay;
    }

    //左进
    public void jumpActivityLeft(Class<?> cls) {
        if (!onMoreClick()) {
            baseJump(cls);
            mContext.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        }

    }


    //左进带数据
    public void jumpActivityLeft(Intent intent) {
        if (!onMoreClick()) {
            baseJump(intent);
            mContext.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        }
    }

    //左进
    public void finishActivityLeft() {
        if (!onMoreClick()) {
            mContext.finish();
            mContext.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        }
    }

    //右进
    public void jumpActivityRight(Class<?> cls) {
        if (!onMoreClick()) {
            baseJump(cls);
            mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }


    //缩放进入
    public void jumpActivityScale(Class<?> cls) {
        if (!onMoreClick()) {
            baseJump(cls);
            mContext.overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
        }

    }


    //右进带数据
    public void jumpActivityRight(Intent intent) {
        if (!onMoreClick()) {
            baseJump(intent);
            mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }

    //右进
    public void finishActivityRight() {
        if (!onMoreClick()) {
            mContext.finish();
            mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }

    //基础的跳转
    public void baseJump(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }


    //基础的跳转带数据
    public void baseJump(Intent intent) {
        if (!onMoreClick()) {
            mContext.startActivity(intent);
        }
    }

    //基础的跳转
    public void baseJumpActivity(Class<?> cls) {
        if (!onMoreClick()) {
            Intent intent = new Intent(mContext, cls);
            mContext.startActivity(intent);
        }
    }

    //2秒内是否是一次点击
    public boolean onMoreClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;
        if (time < delay) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}
