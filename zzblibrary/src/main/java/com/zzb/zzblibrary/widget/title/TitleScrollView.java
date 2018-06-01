package com.zzb.zzblibrary.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class TitleScrollView extends ScrollView{
    public interface ScrollViewListener {

        void onScrollChanged(TitleScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public TitleScrollView(Context context) {
        super(context);
    }

    public TitleScrollView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
    }

    public TitleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
