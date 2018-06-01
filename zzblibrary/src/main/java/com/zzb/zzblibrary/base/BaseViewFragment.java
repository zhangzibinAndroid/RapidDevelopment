package com.zzb.zzblibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhy.autolayout.AutoLinearLayout;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.dialogfinish.dialog.IOSLoadingDialog;
import com.zzb.zzblibrary.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： 张梓彬
 * 日期： 2017/12/9 0009
 * 时间： 下午 3:05
 * 描述： Fragment基类
 */

public abstract class BaseViewFragment extends Fragment {
    private View parentView;
    private View childrenView;
    protected AutoLinearLayout layParent;
    protected Activity mContext;
    protected Unbinder unbinder;
    protected IOSLoadingDialog iosLoadingDialog;
    private String className = getClass().getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.base_fragment_layout, container, false);
        layParent = parentView.findViewById(R.id.baseFragmentLayout);
        mContext = getActivity();
        LogUtils.setClassName(className);
        childrenView = View.inflate(mContext, getLayoutResId(), null);
        layParent.addView(childrenView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, childrenView);
        onBoundView(savedInstanceState, childrenView);
        initView();
        return parentView;
    }

    protected abstract int getLayoutResId();

    protected abstract void onBoundView(Bundle savedInstanceState, View childrenView);

    protected abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected void showIOSLodingDialog() {
        showIOSLodingDialog("正在加载...");
    }

    protected void showIOSLodingDialog(String hintMsg) {
        iosLoadingDialog = new IOSLoadingDialog().setOnTouchOutside(true);
        iosLoadingDialog.setOnTouchOutside(false);
        iosLoadingDialog.setHintMsg(hintMsg);
        iosLoadingDialog.show(mContext.getFragmentManager(), "iosLoadingDialog");
    }

    protected void dismissIOSDialog() {
        if (iosLoadingDialog != null) {
            iosLoadingDialog.dismiss();
        }
    }

    /**
     * 【强制】新建线程时，必须通过线程池提供（AsyncTask 或者 ThreadPoolExecutor
     * 或者其他形式自定义的线程池），不允许在应用中自行显式创建线程。
     * 说明：
     * 使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解
     * 决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致
     * 阿里巴巴 Android 开发手册
     * 消耗完内存或者“过度切换”的问题。另外创建匿名线程不便于后续的资源使用分析，
     * 对性能分析等会造成困扰。
     **/
    protected void addThreads(Runnable... runnable) {
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue);
        for (int i = 0; i < runnable.length; i++) {
            executorService.execute(runnable[i]);
        }
    }


    protected void addThread(Runnable runnable) {
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue);
        executorService.execute(runnable);
    }



}
