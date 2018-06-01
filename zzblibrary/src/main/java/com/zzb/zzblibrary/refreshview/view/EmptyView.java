package com.zzb.zzblibrary.refreshview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;

public class EmptyView extends AutoLinearLayout {
    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
    }


}
