package com.zzb.zzblibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.callutils.OnClickCallBack;

/**
 * 作者： 张梓彬
 * 日期： 2017/12/8 0008
 * 时间： 下午 1:20
 * 描述： 标题栏
 */

public class AutoTitleBar extends AutoLinearLayout {
    private ImageView iv_left, iv_right;
    private TextView tv_title, tvBack, tvRight;
    private AutoRelativeLayout titbarLayout;
    private AutoLinearLayout layLeft;
    private AutoRelativeLayout layRight;
    public static final int DISMISS_IMAGE = -1;
    private RelativeLayout.LayoutParams lp;

    public AutoTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this);
        iv_left = (ImageView) findViewById(R.id.imgLeft);
        iv_right = (ImageView) findViewById(R.id.imgRight);
        tv_title = (TextView) findViewById(R.id.tvTitle);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvBack = (TextView) findViewById(R.id.tvBack);
        titbarLayout = (AutoRelativeLayout) findViewById(R.id.layTitlebar);
        layLeft = (AutoLinearLayout) findViewById(R.id.layLeft);
        layRight = (AutoRelativeLayout) findViewById(R.id.lay_right);
        tvBack.setVisibility(GONE);
        AutoUtils.autoSize(titbarLayout);
    }

    public void setTitleBar(String title, int imageleft, int imageright, final OnClickCallBack onClickCallBack) {
        tv_title.setText(title);
        if (imageleft == DISMISS_IMAGE) {
            iv_left.setVisibility(View.GONE);
        } else {
            iv_left.setImageResource(imageleft);
            layLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        onClickCallBack.OnClickLeft();
                    } catch (Exception e) {
                    }
                }
            });
        }


        if (imageright == DISMISS_IMAGE) {
            iv_right.setVisibility(View.GONE);
        } else {
            iv_right.setImageResource(imageright);
            layRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        onClickCallBack.OnClickRight();
                    } catch (Exception e) {
                    }
                }
            });
        }

        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onRightTextClickListener.onClick(v);
                } catch (Exception e) {
                }
            }
        });
    }


    public void setLeftImageViewBackground(int drawableResources) {
        iv_left.setBackgroundResource(drawableResources);
    }

    public void setRightImageViewBackground(int drawableResources) {
        iv_right.setBackgroundResource(drawableResources);
    }

    public void setTitleLayoutColor(int color) {
        titbarLayout.setBackgroundColor(color);
    }

    @SuppressLint("NewApi")
    public void setTitleLayoutColor(Drawable background) {
        titbarLayout.setBackground(background);
    }


    public void setTitleLayoutColorResource(@DrawableRes int resid) {
        titbarLayout.setBackgroundResource(resid);
    }

    public void setTitleColor(int color) {
        tv_title.setTextColor(color);
    }


    public void setTextLeft(String textLeft) {
        tvBack.setVisibility(VISIBLE);
        tvBack.setText(textLeft);
    }


    public void setTextLeftVisibility(boolean isVisibility) {
        if (isVisibility) {
            tvBack.setVisibility(VISIBLE);
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layLeft.setLayoutParams(lp);
        } else {
            tvBack.setVisibility(GONE);
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layLeft.setLayoutParams(lp);
        }
    }


    public void setRightText(String text) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(text);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(getResources().getColor(color));
    }

    public void setRightTextSize(int size) {
        tvRight.setTextSize(getResources().getDimension(size));
    }



    public OnRightTextClickListener onRightTextClickListener;

    public interface OnRightTextClickListener{
        void onClick(View v);
    }

    public void setOnRightTextClickListener(OnRightTextClickListener onRightTextClickListener) {
        this.onRightTextClickListener = onRightTextClickListener;
    }
}