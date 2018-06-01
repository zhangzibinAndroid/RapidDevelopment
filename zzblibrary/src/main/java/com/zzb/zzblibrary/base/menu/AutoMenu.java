package com.zzb.zzblibrary.base.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.utils.FragmentUtils;

import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/5 0005
 * 时间： 下午 2:57
 * 描述： 菜单栏
 */

public class AutoMenu extends AutoLinearLayout {
    private AutoLinearLayout layMenu;
    private Context context;
    private TextView[] textViews ;

    public AutoMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.menu_layout, this);
        layMenu = findViewById(R.id.lay_menu);
    }

    public void addMenu(final List<MenuBeans> menulist, final FragmentManager fragmentManager, int showIndex, final int selectColor, final int unSelectColor) {
        layMenu.removeAllViews();
        textViews = new TextView[menulist.size()];
        for (int i = 0; i < menulist.size(); i++) {
            final MenuBeans menuBean = menulist.get(i);
            final TextView textView = new TextView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuBean.getHeight(), 1);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setText(menuBean.getText());
            textView.setTextSize(px2sp(context, menuBean.getTextSize()));
            textView.setTextColor(menuBean.getTextColor());
            Drawable drawableTop = menuBean.getDrawableTop();
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
            textView.setCompoundDrawables(null, drawableTop, null, null);
            textViews[i] = textView;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < textViews.length; j++) {
                        textViews[j].setSelected(false);
                        textViews[j].setTextColor(unSelectColor);
                    }

                    try {
                        FragmentUtils.hideFragments(fragmentManager);
                        FragmentUtils.showFragment(menuBean.getFragment());
                        textView.setSelected(true);
                        textView.setTextColor(selectColor);
                        menuBean.onMenuClickListener.onMenuClick(v);
                    } catch (Exception e) {
                    }

                }
            });
            textView.setBackgroundResource(R.drawable.text_select);
            textView.setPadding(0, 10, 0, 5);
            layMenu.addView(textView);
        }

        textViews[showIndex].setSelected(true);
        textViews[showIndex].setTextColor(selectColor);

    }


    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


}
