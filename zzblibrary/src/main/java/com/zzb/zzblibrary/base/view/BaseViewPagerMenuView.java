package com.zzb.zzblibrary.base.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.base.menu.AutoMenuViewpager;
import com.zzb.zzblibrary.base.menu.MenuBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/8 0008
 * 时间： 下午 1:58
 * 描述： ViewPager的菜单栏，懒加载
 */

public class BaseViewPagerMenuView extends AutoLinearLayout {
    private XViewpager lay_pager;
    private AutoMenuViewpager menu;
    private static List<Fragment> fragmentList = new ArrayList<>();
    private Activity mContext;

    public BaseViewPagerMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.base_viewpager_menu_view, this);

        findId();
    }

    private void findId() {
        menu = findViewById(R.id.menu);
        lay_pager = findViewById(R.id.lay_pager);
        AutoUtils.autoSize(menu);
    }

    /**
     * @param fragmentManager 设置管理器
     * @param showIndex       展示第几个
     * @param selectColor     选中颜色
     * @param unSelectColor   未选中颜色
     * @param isCanScroll     是否可以滑动
     * @param withScroll      点击切换时可否滑动
     * @param isBackGround    点击时是否显示选择器效果
     */
    public void addMenu(List<MenuBeans> list, FragmentManager fragmentManager, int showIndex, int selectColor, int unSelectColor, boolean isCanScroll, boolean withScroll, boolean isBackGround) {
        fragmentList.clear();
        for (int i = 0; i < list.size(); i++) {
            MenuBeans menuBeans = list.get(i);
            fragmentList.add(menuBeans.getFragment());
        }

        lay_pager.setCurrentItem(showIndex);
        lay_pager.setScanScroll(isCanScroll);
        menu.addMenu(list, showIndex, selectColor, unSelectColor, lay_pager, withScroll, isBackGround);
        lay_pager.setAdapter(new CustomLazyFragmentPagerAdapter(fragmentManager));
        menu.setOnTitleChangedListener(new AutoMenuViewpager.OnTitleChangedListener() {
            @Override
            public void OnTitleChanged(String title) {
                try {
                    onTitleChangedListener.OnTitleChanged(title);
                } catch (Exception e) {
                }
            }
        });

        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    private static class CustomLazyFragmentPagerAdapter extends FragmentPagerAdapter {

        private CustomLazyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            for (int i = 0; i < fragmentList.size(); i++) {
                if (position == i) {
                    return fragmentList.get(i);
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }


    public OnTitleChangedListener onTitleChangedListener;

    public interface OnTitleChangedListener {
        void OnTitleChanged(String title);
    }

    /**
     * @param onTitleChangedListener 设置标题切换监听
     */
    public void setOnTitleChangedListener(OnTitleChangedListener onTitleChangedListener) {
        this.onTitleChangedListener = onTitleChangedListener;
    }


    /**
     * @param onPageChangeListener 设置滑动监听
     */
    public void setOnPageChangeListener(AutoMenuViewpager.OnPageChangeListener onPageChangeListener) {
        menu.setOnPageChangeListener(onPageChangeListener);
    }


    /**
     * @param item 设置当前页
     */
    public void setCurrentItem(int item) {
        lay_pager.setCurrentItem(item);
    }

    /**
     * @param item         设置当前页
     * @param smoothScroll 设置是否可以滑动
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        lay_pager.setCurrentItem(item, smoothScroll);
    }

    /**
     * @param limit 设置预加载数目
     */
    public void setOffscreenPageLimit(int limit) {
        lay_pager.setOffscreenPageLimit(limit);
    }


}
