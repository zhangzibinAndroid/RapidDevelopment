package com.zzb.myalllibrary.application;

import com.zxy.tiny.CompressImageUtils;
import com.zxy.tiny.Tiny;
import com.zzb.zzblibrary.base.BaseApplication;
import com.zzb.zzblibrary.utils.LogUtils;

/**
 * Created by 张梓彬 on 2018/1/22 0022.
 */

public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CompressImageUtils.init(this);
        LogUtils.setIsOpenLog(true);
    }
}
