package com.zzb.zzblibrary.widget.spinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zzb.zzblibrary.R;

/**
 * 作者： 张梓彬
 * 日期： 2018/4/9 0009
 * 时间： 下午 2:48
 * 描述： Spinner封装使用
 */

public class XSpinner extends AutoLinearLayout {
    protected AutoRelativeLayout layoutSpinner;
    protected Spinner spinner;
    protected ImageView imgRight;
    protected Context mContext;


    public XSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_spinner, this);
        initView();
    }

    private void initView() {
        layoutSpinner = (AutoRelativeLayout) findViewById(R.id.layout_spinner);
        spinner = (Spinner) findViewById(R.id.spinner);
        imgRight = (ImageView) findViewById(R.id.img_right);
    }


    public void setXSpinnerBackground(@DrawableRes int resid) {
        layoutSpinner.setBackgroundResource(resid);
    }

    @SuppressLint("NewApi")
    public void setXSpinnerBackground(Drawable background) {
        layoutSpinner.setBackground(background);
    }

    public void setXSpinnerBackgroundColor(@ColorInt int color) {
        layoutSpinner.setBackgroundColor(color);
    }

    public void setXSpinnerImageDrawable(@Nullable Drawable drawable) {
        imgRight.setImageDrawable(drawable);
    }

    public void setXSpinnerImageBitmap(Bitmap bm) {
        imgRight.setImageBitmap(bm);
    }

    public void setXSpinnerImageVisibility(int visibility){
        imgRight.setVisibility(visibility);
    }

    public void setFocusables(boolean focusable){
        spinner.setFocusable(focusable);
    }

    public int getSelectedItemPosition() {
        return spinner.getSelectedItemPosition();
    }

    public void setClickables(boolean isClickable){
        spinner.setClickable(isClickable);
    }


    /**
     * 以下是spinner的一些方法
     */
    public void setAdapter(SpinnerAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    public void setDropDownHorizontalOffset(@DimenRes int pt) {
        //单位用pt，采用RudenessScreenHelper做屏幕适配，效果很好
        int pixels = getResources().getDimensionPixelSize(pt);
        spinner.setDropDownHorizontalOffset((int) pixels);
    }

    @SuppressLint("NewApi")
    public void setDropDownVerticalOffset(@DimenRes int pt) {
        //单位用pt，采用RudenessScreenHelper做屏幕适配，效果很好
        int pixels = getResources().getDimensionPixelSize(pt);
        spinner.setDropDownVerticalOffset(pixels);
    }

    @SuppressLint("NewApi")
    public void setDropDownWidth(int pixels) {
        spinner.setDropDownWidth(pixels);
    }

    public void setGravity(int gravity) {
        spinner.setGravity(gravity);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        spinner.setOnItemClickListener(l);
    }

    @SuppressLint("NewApi")
    public void setPopupBackgroundDrawable(Drawable background) {
        spinner.setPopupBackgroundDrawable(background);
    }

    @SuppressLint("NewApi")
    public void setPopupBackgroundResource(@DrawableRes int resId) {
        spinner.setPopupBackgroundResource(resId);

    }

    public void setSelection(int position) {
        spinner.setSelection(position);
    }

    public void setSelection(int position, boolean animate) {
        spinner.setSelection(position, animate);
    }

    public void getSelectedItem() {
        spinner.getSelectedItem();
    }

    public int getBaseline() {
        return spinner.getBaseline();
    }


    @SuppressLint("NewApi")
    public CharSequence getAccessibilityClassName() {
        return spinner.getAccessibilityClassName();
    }

    @SuppressLint("NewApi")
    public int getDropDownHorizontalOffset() {
        return spinner.getDropDownHorizontalOffset();
    }

    @SuppressLint("NewApi")
    public int getDropDownVerticalOffset() {
        return spinner.getDropDownVerticalOffset();
    }

    public CharSequence getPrompt() {
        return spinner.getPrompt();
    }


}
