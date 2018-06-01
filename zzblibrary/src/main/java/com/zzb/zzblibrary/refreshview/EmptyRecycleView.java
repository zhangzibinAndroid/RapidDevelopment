package com.zzb.zzblibrary.refreshview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zzb.zzblibrary.R;

import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/3/14 0014
 * 时间： 上午 9:34
 * 描述： recycleView如果没有数据则展示空图片
 */

public class EmptyRecycleView extends AutoLinearLayout{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private AutoRelativeLayout layImage;
    private ImageView mImageView;

    public EmptyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.recycleview_empty, this);
        mContext = context;
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        layImage = findViewById(R.id.layImage);
        mImageView = findViewById(R.id.mImageView);
    }


    /**
     * RecycleView显示为空的时候展示默认的图片
     */
    public void showEmptyImage(List list) {
        if (list == null || list.size() == 0) {
            mRecyclerView.setVisibility(GONE);
            layImage.setVisibility(VISIBLE);
        }else {
            mRecyclerView.setVisibility(VISIBLE);
            layImage.setVisibility(GONE);
        }
    }


    public void showEmptyImage(List list,@DrawableRes int image) {
        if (list == null || list.size() == 0) {
            mRecyclerView.setVisibility(GONE);
            mImageView.setImageDrawable(getResources().getDrawable(image));
            layImage.setVisibility(VISIBLE);
        }else {
            mRecyclerView.setVisibility(VISIBLE);
            mImageView.setImageDrawable(getResources().getDrawable(image));
            layImage.setVisibility(GONE);
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

    public void setBackgroundResource(@DrawableRes int resid) {
        mRecyclerView.setBackgroundResource(resid);
    }

    @SuppressLint("NewApi")
    public void setBackground(Drawable background) {
        mRecyclerView.setBackground(background);
    }


}
