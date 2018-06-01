package com.zzb.zzblibrary.slidingmenu;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;
import com.zzb.zzblibrary.slidingmenu.review.SlidingMenu;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/9 0009
 * 时间： 下午 2:10
 * 描述： 页面侧滑
 */

public class AutoSlidingMenu extends SlidingMenu {
    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoSlidingMenu(Context context) {
        super(context);
    }

    public AutoSlidingMenu(Activity activity, int slideStyle) {
        super(activity, slideStyle);
    }

    public AutoSlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public AutoRelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoRelativeLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode())
            mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public static class LayoutParams extends RelativeLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }


    }
}
