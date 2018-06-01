package com.zzb.zzblibrary.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;
import com.zzb.zzblibrary.R;
import com.zzb.zzblibrary.base.menu.AutoMenu;
import com.zzb.zzblibrary.base.menu.MenuBeans;
import com.zzb.zzblibrary.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/5 0005
 * 时间： 下午 4:57
 * 描述： 菜单基类
 */

public class BaseMenuView extends AutoLinearLayout {
    private AutoMenu menu;
    List<Fragment> fragmentList = new ArrayList<>();

    public BaseMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_menu, this);
        findId();
    }

    //初始化控件
    private void findId() {
        menu = findViewById(R.id.menu);
        AutoUtils.autoSize(menu);
    }

    public void addMenu(List<MenuBeans> list, FragmentManager fragmentManager, int showIndex, int selectColor, int unSelectColor) {
        for (int i = 0; i < list.size(); i++) {
            MenuBeans menuBeans = list.get(i);
            fragmentList.add(menuBeans.getFragment());
        }

        FragmentUtils.addFragments(fragmentManager, fragmentList, showIndex, R.id.lay_menu);
        menu.addMenu(list, fragmentManager, showIndex, selectColor, unSelectColor);
    }


}
