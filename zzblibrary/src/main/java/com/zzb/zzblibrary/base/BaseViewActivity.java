package com.zzb.zzblibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.dialogfinish.dialog.IOSLoadingDialog;
import com.zzb.zzblibrary.swipback.SwipeBackHelper;
import com.zzb.zzblibrary.utils.AppManager;
import com.zzb.zzblibrary.utils.LogUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;
import com.zzb.zzblibrary.widget.title.TitleNestedScrollView;
import com.zzb.zzblibrary.widget.title.TitleScrollView;

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
 * 时间： 下午 1:54
 * 描述： Activity基类
 */

public abstract class BaseViewActivity extends AppCompatActivity implements TitleScrollView.ScrollViewListener, TitleNestedScrollView.ScrollViewListener {
    private AutoTitleBar titleBar, titleNormal;
    private AutoLinearLayout baseLayout, baseActivityLayout2,baseActivityLayout3;
    protected TitleScrollView scrollTitle;
    protected TitleNestedScrollView titleNestedScrollView;
    private View view;
    protected Activity mContext;
    protected Unbinder unbinder;
    protected IOSLoadingDialog iosLoadingDialog;
    private String className = getClass().getSimpleName();
    protected AutoRelativeLayout layParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.base_activity_layout);//把设置布局文件的操作交给继承的子类
        if (enableRightSliding()) {
            SwipeBackHelper.onCreate(this);
            SwipeBackHelper.getCurrentPage(this)
                    .setSwipeBackEnable(true)
                    .setSwipeSensitivity(0.5f)
                    .setSwipeRelateEnable(true)
                    .setSwipeRelateOffset(300);
        }

        if (!removeAppManager()) {
            AppManager.getInstance().addActivity(this);//Activity 管理器
        }
        mContext = this;
        LogUtils.setClassName(className);
        titleBar = (AutoTitleBar) findViewById(R.id.titleBar);
        layParent = (AutoRelativeLayout) findViewById(R.id.lay_parent);
        titleNormal = (AutoTitleBar) findViewById(R.id.titleNormal);
        scrollTitle = (TitleScrollView) findViewById(R.id.scrollTitle);
        titleNestedScrollView = (TitleNestedScrollView) findViewById(R.id.titleNestedScrollView);
        baseLayout = (AutoLinearLayout) findViewById(R.id.baseActivityLayout);
        baseActivityLayout2 = (AutoLinearLayout) findViewById(R.id.baseActivityLayout2);
        baseActivityLayout3 = (AutoLinearLayout) findViewById(R.id.baseActivityLayout3);
        view = View.inflate(this, getLayoutResId(), null);
        if (showScrollTitle()){
            if (openNestedScrollView()) {
                titleNestedScrollView.setVisibility(View.VISIBLE);
                scrollTitle.setVisibility(View.GONE);
                baseActivityLayout2.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                unbinder = ButterKnife.bind(this);
            } else {
                scrollTitle.setVisibility(View.VISIBLE);
                titleNestedScrollView.setVisibility(View.GONE);
                baseLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                unbinder = ButterKnife.bind(this);
            }
        }else {
            baseActivityLayout3.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            unbinder = ButterKnife.bind(this);
        }
        onBoundView(savedInstanceState);

        //首次安装
        if (isFirstInstal()){
            if (!isTaskRoot()) {
                Intent intent = getIntent();
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                    finish();
                    return;
                }
            }
        }

        if (showScrollTitle()) {
            titleBar.setVisibility(View.VISIBLE);
            titleNormal.setVisibility(View.GONE);
            setTitleBar(titleBar);
        } else {
            titleBar.setVisibility(View.GONE);
            titleNormal.setVisibility(View.VISIBLE);
            setTitleBar(titleNormal);
        }
        initView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (enableRightSliding()) {
            SwipeBackHelper.onPostCreate(this);
        }
    }


    protected abstract int getLayoutResId();

    protected abstract void setTitleBar(AutoTitleBar titleBar);

    protected abstract void onBoundView(Bundle savedInstanceState);

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!removeAppManager()) {
            AppManager.getInstance().finishActivity(this);//Activity 管理器
        }

        if (enableRightSliding()) {
            SwipeBackHelper.onDestroy(this);
        }
        unbinder.unbind();
    }


    protected void showIOSLodingDialog() {
        showIOSLodingDialog("正在加载...");
    }

    protected void showIOSLodingDialog(String hintMsg) {
        iosLoadingDialog = new IOSLoadingDialog().setOnTouchOutside(true);
        iosLoadingDialog.setOnTouchOutside(false);
        iosLoadingDialog.setHintMsg(hintMsg);
        iosLoadingDialog.show(this.getFragmentManager(), "iosLoadingDialog");
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


    /**
     * 子类重写这个方法true:允许向右滑动，false:禁止向右滑动
     */
    protected boolean enableRightSliding() {
        return false;
    }


    /**
     * 不进入 Appmanager 管理
     */
    protected boolean removeAppManager() {
        return false;
    }


    private int imageHeight;
    private int colorA = 0;
    private int colorR = 0;
    private int colorG = 0;
    private int colorB = 0;

    protected void initScrollTitle(final View view, int color) {

        colorR = (color & 0xff0000) >> 16;
        colorG = (color & 0x00ff00) >> 8;
        colorB = (color & 0x0000ff);

        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = view.getHeight();
                scrollTitle.setScrollViewListener(BaseViewActivity.this);
            }
        });
    }


    protected void initNetsedScrollTitle(final View view, int color) {

        colorR = (color & 0xff0000) >> 16;
        colorG = (color & 0x00ff00) >> 8;
        colorB = (color & 0x0000ff);

        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = view.getHeight();
                titleNestedScrollView.setScrollViewListener(BaseViewActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(TitleScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {
            titleBar.setBackgroundColor(Color.argb((int) colorA, colorR, colorG, colorB));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            titleBar.setBackgroundColor(Color.argb((int) alpha, colorR, colorG, colorB));
        }
    }


    protected boolean showScrollTitle() {
        return false;
    }


    /**
     * 是否开启NestedScrollView
     */
    protected boolean openNestedScrollView() {
        return false;
    }

    @Override
    public void onScrollChanged(TitleNestedScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            titleBar.setBackgroundColor(Color.argb((int) colorA, colorR, colorG, colorB));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            titleBar.setBackgroundColor(Color.argb((int) alpha, colorR, colorG, colorB));
        }
    }


    /**
     * 设置为true，避免第一次安装首页创建两次
     */
    protected boolean isFirstInstal(){
        return false;
    }
}
