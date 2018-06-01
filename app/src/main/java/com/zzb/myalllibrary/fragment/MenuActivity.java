package com.zzb.myalllibrary.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.zzb.myalllibrary.R;
import com.zzb.zzblibrary.base.menu.MenuBeans;
import com.zzb.zzblibrary.base.view.BaseMenuView;
import com.zzb.zzblibrary.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    /*@BindView(R.id.frame)
    AutoFrameLayout frame;
    @BindView(R.id.tablayout)
    AutoTabLayout tablayout;
    @BindView(R.id.tv_all_kinds)
    TextView tv_all_kinds;*/
    @BindView(R.id.baseMenu)
    BaseMenuView baseMenu;
    private String[] allKinds = {"全部分类", "微课习作", "活动习作", "独立习作"};
    private List<String> list = new ArrayList<>();
    private List<MenuBeans> listmenus = new ArrayList<>();
    private FragmentManager fragmentManager;
    protected String className = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        LogUtils.setClassName(className);
        LogUtils.e("onCreate: 222");
        /*tablayout.addTab(tablayout.newTab().setText("Tab 2"));
        tablayout.addTab(tablayout.newTab().setText("Tab 3"));
        tablayout.addTab(tablayout.newTab().setText("Tab 4"));
        tablayout.addTab(tablayout.newTab().setText("Tab 5"));

        list.add("dsadas");
        list.add("dsadsacxzdas");
        list.add("casd");

        tv_all_kinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopSearchUtils.getInstance(MenuActivity.this).showListPop(list, tv_all_kinds, new PopSearchUtils.OnClickCallBack() {
                    @Override
                    public void OnClickCall(String text) {
                        Toast.makeText(MenuActivity.this, "点击了：" + text, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/


        fragmentManager = getSupportFragmentManager();

        final FirstFragment firstFragment = new FirstFragment();
        final SecondFragment secondFragment = new SecondFragment();


        MenuBeans menuBeans = new MenuBeans();
        menuBeans.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
        menuBeans.setText("哈哈");
        menuBeans.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px30));
        menuBeans.setHeight(getResources().getDimensionPixelSize(R.dimen.px110));
        menuBeans.setTextColor(getResources().getColor(R.color.textcolor));
        menuBeans.setFragment(firstFragment);


        MenuBeans menuBeans2 = new MenuBeans();
        menuBeans2.setDrawableTop(getResources().getDrawable(R.drawable.ic_select));
        menuBeans2.setText("啦啦");
        menuBeans2.setTextColor(getResources().getColor(R.color.textcolor));
        menuBeans2.setHeight(getResources().getDimensionPixelSize(R.dimen.px110));
        menuBeans2.setTextSize(getResources().getDimensionPixelOffset(R.dimen.px30));
        menuBeans2.setFragment(secondFragment);

        int unSetectColor = getResources().getColor(R.color.textcolor);
        int setectColor = getResources().getColor(R.color.selectColor);

        listmenus.add(menuBeans);
        listmenus.add(menuBeans2);
        baseMenu.addMenu(listmenus,fragmentManager,0,setectColor,unSetectColor);

    }
}
