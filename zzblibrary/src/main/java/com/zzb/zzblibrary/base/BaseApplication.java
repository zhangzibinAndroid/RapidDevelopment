package com.zzb.zzblibrary.base;

import android.app.Application;

import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zzb.zzblibrary.utils.BaseCrashHandler;
import com.zzb.zzblibrary.utils.RudenessScreenHelper;

/**
 * 捕获应用异常Application
 * 在这里完成整个应用退出；在这里进行全局变量的传递；在这里完成低内存的释放；在这里捕获未抓住的异常；用于应用配置, 预加载处理
 * BaseCrashHandler handler = BaseCrashHandler.getInstance();
 * handler.init(this);
 */

public class BaseApplication extends Application {

    public static BaseApplication instance;
    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        new RudenessScreenHelper(this, 750).activate();
		AutoLayoutConifg.getInstance().useDeviceSize();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    // 在内存低时,发送广播可以释放一些内存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
