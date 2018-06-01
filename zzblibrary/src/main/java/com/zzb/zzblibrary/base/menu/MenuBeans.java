package com.zzb.zzblibrary.base.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/5 0005
 * 时间： 下午 3:01
 * 描述： 菜单
 */

public class MenuBeans {
    private int height;
    private Drawable drawableTop;
    private String text;
    private int textColor;
    private int textSize;
    private Fragment fragment;
    public OnMenuClickListener onMenuClickListener;

    public interface OnMenuClickListener{
        void onMenuClick(View view);
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Drawable getDrawableTop() {
        return drawableTop;
    }

    public void setDrawableTop(Drawable drawableTop) {
        this.drawableTop = drawableTop;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

}
