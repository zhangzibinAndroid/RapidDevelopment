package com.zzb.zzblibrary.base.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.base.view.XViewpager;

import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/5 0005
 * 时间： 下午 2:57
 * 描述： 菜单栏
 */

public class AutoMenuViewpager extends AutoLinearLayout {
    private AutoLinearLayout layMenu;
    private Context context;
    private TextView[] textViews;
    private LinearLayout.LayoutParams lp;

    public AutoMenuViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.menu_layout, this);
        layMenu = findViewById(R.id.lay_menu);

    }

    public void addMenu(final List<MenuBeans> menulist, int showIndex, final int selectColor, final int unSelectColor, final XViewpager viewpager, final boolean withScroll, boolean isBackGround) {
        layMenu.removeAllViews();
        textViews = new TextView[menulist.size()];

        for (int i = 0; i < menulist.size(); i++) {
            final MenuBeans menuBean = menulist.get(i);
            final TextView textView = new TextView(context);
            if (menuBean.getHeight() == 0) {
                this.lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            } else {
                this.lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuBean.getHeight(), 1);
            }
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setText(menuBean.getText());
            textView.setTextSize(menuBean.getTextSize());
            textView.setTextColor(menuBean.getTextColor());
            textView.setTag(i);
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
                        textView.setSelected(true);
                        textView.setTextColor(selectColor);
                        viewpager.setCurrentItem((Integer) textView.getTag(), withScroll);
                        onTitleChangedListener.OnTitleChanged(textView.getText().toString().trim());
                        menuBean.onMenuClickListener.onMenuClick(v);
                    } catch (Exception e) {
                    }

                }
            });
            if (isBackGround) {
                textView.setBackgroundResource(R.drawable.text_select);
            }
            textView.setPadding(0, 10, 0, 5);
            layMenu.addView(textView);
        }

        textViews[showIndex].setSelected(true);
        textViews[showIndex].setTextColor(selectColor);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                } catch (Exception e) {
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (TextView textview : textViews) {
                    textview.setSelected(false);
                    textview.setTextColor(unSelectColor);

                }
                textViews[position].setTextColor(selectColor);
                textViews[position].setSelected(true);
                try {
                    onTitleChangedListener.OnTitleChanged(textViews[position].getText().toString().trim());
                } catch (Exception e) {
                }
                try {
                    onPageChangeListener.onPageSelected(position);
                } catch (Exception e) {
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                try {
                    onPageChangeListener.onPageScrollStateChanged(state);
                } catch (Exception e) {
                }

            }
        });


    }

    public OnTitleChangedListener onTitleChangedListener;

    public interface OnTitleChangedListener {
        void OnTitleChanged(String title);
    }

    public void setOnTitleChangedListener(OnTitleChangedListener onTitleChangedListener) {
        this.onTitleChangedListener = onTitleChangedListener;
    }

    public OnPageChangeListener onPageChangeListener;

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }


}
