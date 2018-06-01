package com.zzb.zzblibrary.refreshview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.refreshview.call.DeteachCall;
import com.zzb.zzblibrary.refreshview.call.RefreshCall;
import com.zzb.zzblibrary.utils.LogUtils;
import com.zzb.zzblibrary.xrefreshview.XRefreshView;

import java.util.List;


/**
 * 作者： 张梓彬
 * 日期： 2018/1/9 0009
 * 时间： 上午 10:31
 * 描述： 可刷新的RecycleView
 */
public class RefreshRecycleView extends LinearLayout implements DeteachCall {
    protected XRefreshView xRefreshView;
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    private int downIsRefresh = 1;
    private Handler downHandler, refreshUpHandler;
    private Runnable downRunable, upRunable;
    private int upIsRefresh = 1;
    private boolean pullLoadEnable = false; //设置是否可以上拉刷新
    private boolean pullRefreshEnable = false; //设置是否可以下拉刷新

    public RefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.base_recycleview_refresh, this);
        findId();
    }

    private void findId() {
        xRefreshView = findViewById(R.id.xRefreshView);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setPullRefreshEnable(false);
    }


    /**
     * @param refreshCall 设置刷新
     * @param time 设置下拉刷新时间
     **/
    public void setRefreshCall(final RefreshCall refreshCall, final int time) {

        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                if (pullRefreshEnable) {
                    downIsRefresh = 2;
                    downHandler = new Handler();
                    downHandler.postDelayed(downRunable = new Runnable() {
                        @Override
                        public void run() {
                            xRefreshView.stopRefresh();

                        }
                    }, time);
                } else {
                    xRefreshView.stopRefresh();
                }

            }

            @Override
            public void onRefresh(boolean isPullDown) {
                if (isPullDown) {
                    try {
                        refreshCall.onPullRefresh(isPullDown);
                    } catch (Exception e) {
                        LogUtils.e("下拉刷新异常: 请设置refreshRecycleView.setRefreshCall" + e.getMessage());
                    }
                }
            }

            @Override
            public void onLoadMore(final boolean isSilence) {
                if (pullLoadEnable) {
                    try {
                        refreshCall.onLoadMore(isSilence);
                    } catch (Exception e) {
                        LogUtils.e("上拉刷新异常: 请设置refreshRecycleView.setRefreshCall" + e.getMessage());
                    }
                } else {
                    xRefreshView.stopLoadMore();
                }


            }

            @Override
            public void onRelease(float direction) {
            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {
            }
        });

    }



    /**
     * @param refreshCall 设置刷新
     **/
    public void setRefreshCall(final RefreshCall refreshCall) {

        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                if (pullRefreshEnable) {
                    downIsRefresh = 2;
                    downHandler = new Handler();
                    downHandler.postDelayed(downRunable = new Runnable() {
                        @Override
                        public void run() {
                            xRefreshView.stopRefresh();

                        }
                    }, 1000);
                } else {
                    xRefreshView.stopRefresh();
                }

            }

            @Override
            public void onRefresh(boolean isPullDown) {
                if (isPullDown) {
                    try {
                        refreshCall.onPullRefresh(isPullDown);
                    } catch (Exception e) {
                        LogUtils.e("下拉刷新异常: 请设置refreshRecycleView.setRefreshCall" + e.getMessage());
                    }
                }
            }

            @Override
            public void onLoadMore(final boolean isSilence) {
                if (pullLoadEnable) {
                    try {
                        refreshCall.onLoadMore(isSilence);
                    } catch (Exception e) {
                        LogUtils.e("上拉刷新异常: 请设置refreshRecycleView.setRefreshCall" + e.getMessage());
                    }
                } else {
                    xRefreshView.stopLoadMore();
                }


            }

            @Override
            public void onRelease(float direction) {
            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {
            }
        });

    }
    /**
     * 停止下拉刷新
     */
    public void stopRefresh() {
        xRefreshView.stopRefresh();
    }

    /**
     * header可下拉的最大距离
     *
     * @param headMoveDistence
     */
    public void setHeadMoveLargestDistence(int headMoveDistence) {
        xRefreshView.setHeadMoveLargestDistence(headMoveDistence);
    }


    /**
     * 设置在下拉刷新被禁用的情况下，是否允许界面被下拉
     *
     * @param moveHeadWhenDisablePullRefresh 默认是true
     */
    public void setMoveHeadWhenDisablePullRefresh(boolean moveHeadWhenDisablePullRefresh) {
        xRefreshView.setMoveHeadWhenDisablePullRefresh(moveHeadWhenDisablePullRefresh);
    }

    /**
     * 设置在上拉加载被禁用的情况下，是否允许界面被上拉
     *
     * @param moveFootWhenDisablePullLoadMore 默认为true
     */
    public void setMoveFootWhenDisablePullLoadMore(boolean moveFootWhenDisablePullLoadMore) {
        xRefreshView.setMoveFootWhenDisablePullLoadMore(moveFootWhenDisablePullLoadMore);
    }

    /**
     * 设置静默加载更多，旨在提供被刷新的view滚动到底部的监听，自动静默加载更多
     *
     * @param enable 是否启用静默加载模式，需要继承BaseRecyclerAdapter
     */
    public void setSilenceLoadMore(boolean enable) {
        xRefreshView.setSilenceLoadMore(enable);
    }

    /**
     * 设置静默加载时提前加载的item个数
     *
     * @param count
     */
    public void setPreLoadCount(int count) {
        xRefreshView.setPreLoadCount(count);
    }

    /**
     * 设置当下拉刷新完成以后，headerview和footerview被固定的时间
     * 注:考虑到ui效果，只有时间大于1s的时候，footerview被固定的效果才会生效
     *
     * @param pinnedTime
     */
    public void setPinnedTime(int pinnedTime) {
        xRefreshView.setPinnedTime(pinnedTime);
    }

    /**
     * 设置是否自动加载更多，默认是
     *
     * @param autoLoadMore true则自动刷新
     */
    public void setAutoLoadMore(boolean autoLoadMore) {
        xRefreshView.setAutoLoadMore(autoLoadMore);
    }

    /**
     * 是否开启Recyclerview的松开加载更多功能，默认开启
     */
    public void enableReleaseToLoadMore(boolean enable) {
        xRefreshView.enableReleaseToLoadMore(enable);
    }

    /**
     * 设置在被刷新的view滑倒最底部的时候，是否允许被刷新的view继续往上滑动，默认是true
     */
    public void enableRecyclerViewPullUp(boolean enable) {
        xRefreshView.enableRecyclerViewPullUp(enable);
    }

    /**
     * 设置在数据加载完成以后,是否可以向上继续拉被刷新的view,默认为true
     *
     * @param enable
     */
    public void enablePullUpWhenLoadCompleted(boolean enable) {
        xRefreshView.enablePullUpWhenLoadCompleted(enable);
    }

    /**
     * 设置Recylerview的滚动监听事件
     */
    public void setOnRecyclerViewScrollListener(RecyclerView.OnScrollListener scrollListener) {
        xRefreshView.setOnRecyclerViewScrollListener(scrollListener);
    }

    public void stopLoadMore() {
        xRefreshView.stopLoadMore();
    }

    public void stopLoadMore(boolean hideFooter) {
        xRefreshView.stopLoadMore(hideFooter);
    }

    /**
     * @param Refresh 设置是否可以自动刷新
     */
    public void setRefresh(boolean Refresh) {
        xRefreshView.setAutoRefresh(Refresh);
    }


    /**
     * @param headerView 自定义头布局
     */
    public void setHeaderView(View headerView) {
        xRefreshView.setCustomHeaderView(headerView);
    }


    /**
     * @param footerView 自定义底部布局
     */
    public void setFooterView(View footerView) {
        xRefreshView.setCustomFooterView(footerView);
    }

    /**
     * @param pullLoadEnable 设置是否可以上拉刷新
     */
    public void setPullLoadEnable(boolean pullLoadEnable) {
        this.pullLoadEnable = pullLoadEnable;
        if (pullLoadEnable) {
            xRefreshView.setPullLoadEnable(true);
        } else {
            xRefreshView.setPullLoadEnable(false);
        }
    }

    /**
     * @param pullRefreshEnable 设置是否可以下拉刷新
     */
    public void setPullRefreshEnable(boolean pullRefreshEnable) {
        this.pullRefreshEnable = pullRefreshEnable;
        // 设置是否可以下拉刷新
        if (pullRefreshEnable) {
            xRefreshView.setPullRefreshEnable(true);
        } else {
            xRefreshView.setPullRefreshEnable(false);
        }
    }


    /**
     * @param isForHorizontalMove 处理横向移动与XRefreshView的冲突
     */
    public void setMoveForHorizontal(boolean isForHorizontalMove) {
        xRefreshView.setMoveForHorizontal(isForHorizontalMove);
    }

    /**
     * 此方法当没有更多数据时调用，不要和stopLoadMore()同时调用
     *
     * @param isCompleted 加载是否结束
     */
    public void setLoadComplete(boolean isCompleted) {
        xRefreshView.setLoadComplete(isCompleted);
    }

    @Override
    public void onDestroy() {
        if (downIsRefresh == 2) {
            downHandler.removeCallbacks(downRunable);
        }

        if (upIsRefresh == 2) {
            refreshUpHandler.removeCallbacks(upRunable);
        }
    }


    /**
     * 以下是recycleView的一些方法
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        mRecyclerView.addItemDecoration(decor, index);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }

    public void addOnChildAttachStateChangeListener(RecyclerView.OnChildAttachStateChangeListener listener) {
        mRecyclerView.addOnChildAttachStateChangeListener(listener);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.removeOnItemTouchListener(listener);
    }

    public void clearOnScrollListeners() {
        mRecyclerView.clearOnScrollListeners();
    }

    public void clearOnChildAttachStateChangeListeners() {
        mRecyclerView.clearOnChildAttachStateChangeListeners();
    }

    public void setBackgroundColor(@ColorInt int color) {
        mRecyclerView.setBackgroundColor(color);
    }

    public void setRefreshBackgroundColor(@ColorInt int color) {
        xRefreshView.setBackgroundColor(color);
    }

    public void setRefreshBackgroundResource(@DrawableRes int resid) {
        xRefreshView.setBackgroundResource(resid);
    }

    public void setBackgroundResource(@DrawableRes int resid) {
        mRecyclerView.setBackgroundResource(resid);
    }

    @SuppressLint("NewApi")
    public void setBackground(Drawable background) {
        mRecyclerView.setBackground(background);
    }


    /**
     * RecycleView显示为空的时候展示默认的图片
     */
    public void showEmptyImage(List list) {
        if (list == null || list.size() == 0) {
            EmptyAdapter emptyAdapter = new EmptyAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(emptyAdapter);
        }
    }

    private class EmptyAdapter extends RecyclerView.Adapter {
        private int image;

        public void setImage(int image) {
            this.image = image;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_image, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imgEmpty;

            ViewHolder(View view) {
                super(view);
                imgEmpty = findViewById(R.id.img_empty);
            }
        }
    }

}
