package com.zzb.zzblibrary.base;

import android.app.Activity;
import android.os.Bundle;
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
 * <pre>
 * 若把初始化内容放到initData实现
 * 就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 *
 * 注1:
 * 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 *
 * 注2:
 * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 * eg:
 * transaction.hide(aFragment);
 * transaction.show(aFragment);
 *
 * update 2017/01/23
 * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
 * 一般用于PagerAdapter需要同时刷新全部子Fragment的场景
 * 不要new 新的 PagerAdapter 而采取reset数据的方式
 * 所以要求Fragment重新走initData方法
 * 故使用 {@link BaseLazyFragment#setForceLoad(boolean)}来让Fragment下次执行initData
 *
 * Created by Mumu
 * on 2015/11/2.
 * </pre>
 */
public abstract class LazyFragment extends BaseLazyFragment {
    private View parentView;
    private View childrenView;
    protected AutoLinearLayout layParent;
    protected Activity mContext;
    protected Unbinder unbinder;
    protected IOSLoadingDialog iosLoadingDialog;
    private String className = getClass().getSimpleName();

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
