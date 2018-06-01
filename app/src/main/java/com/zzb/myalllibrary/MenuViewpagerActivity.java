package com.zzb.myalllibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.zzb.myalllibrary.fragment.FirstFragment;
import com.zzb.myalllibrary.fragment.SecondFragment;
import com.zzb.myalllibrary.fragment.SecondsFragment;
import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.base.menu.MenuBeans;
import com.zzb.zzblibrary.base.view.BaseViewPagerMenuView;
import com.zzb.zzblibrary.widget.AutoTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuViewpagerActivity extends BaseViewActivity {
    @BindView(R.id.viewPagerMenuView)
    BaseViewPagerMenuView viewPagerMenuView;
    private List<MenuBeans> listmenus = new ArrayList<>();
    private FragmentManager fragmentManager;

 /*   @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_viewpager);
        ButterKnife.bind(this);
        //开启沉浸式状态栏
        //需要在显示页面设置三个属性
//        android:fitsSystemWindows="true"
//        android:clipToPadding="true"
//        android:background="@color/actionsheet_blue"
        StatusBar.setStatusBar(this, true, true, R.color.white);
        initView();
    }*/

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_menu_viewpager;
    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
//        StatusBar.setStatusBar(this, true, false, R.color.white);

    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        final FirstFragment firstFragment = new FirstFragment();
        final SecondFragment secondFragment = new SecondFragment();
        final SecondsFragment secondFragment2 = new SecondsFragment();
        final MainFragment mainFragment = new MainFragment();

        MenuBeans menuBeans = new MenuBeans();
        menuBeans.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
        menuBeans.setText("哈哈");
        menuBeans.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
        menuBeans.setTextColor(getResources().getColor(R.color.textcolor));
        menuBeans.setFragment(secondFragment);

        MenuBeans menuBeans2 = new MenuBeans();
        menuBeans2.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
        menuBeans2.setText("啦啦");
        menuBeans2.setTextColor(getResources().getColor(R.color.textcolor));
        menuBeans2.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
        menuBeans2.setFragment(secondFragment2);

        MenuBeans menuBeans3 = new MenuBeans();
        menuBeans3.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
        menuBeans3.setText("GG");
        menuBeans3.setTextColor(getResources().getColor(R.color.textcolor));
        menuBeans3.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px15));
        menuBeans3.setFragment(mainFragment);

        int unSetectColor = getResources().getColor(R.color.textcolor);
        int setectColor = getResources().getColor(R.color.selectColor);

        listmenus.add(menuBeans);
        listmenus.add(menuBeans2);
        listmenus.add(menuBeans3);
        viewPagerMenuView.addMenu(listmenus, fragmentManager, 0, setectColor, unSetectColor, false, false, true);


        /*  viewPagerMenuView.setOnTitleChangedListener(new BaseViewPagerMenuView.OnTitleChangedListener() {
            @Override
            public void OnTitleChanged(String title) {
            }
        });

        viewPagerMenuView.setOnPageChangeListener(new AutoMenuViewpager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.e("onPageScrolled: " + position);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e("onPageSelected: " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.e("state: " + state);
            }
        });*/


    }


    @OnClick(R.id.btn_ce)
    public void onViewClicked() {
        viewPagerMenuView.setCurrentItem(1);
    }
}
