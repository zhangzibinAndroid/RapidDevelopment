package com.zzb.myalllibrary;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zcw.togglebutton.ToggleButton;
import com.zzb.myalllibrary.fragment.FirstFragment;
import com.zzb.myalllibrary.fragment.SecondFragment;
import com.zzb.zzblibrary.upversion.VersionUtils;
import com.zzb.zzblibrary.utils.AppManager;
import com.zzb.zzblibrary.utils.FragmentUtils;
import com.zzb.zzblibrary.utils.JumpActivityUtils;
import com.zzb.zzblibrary.utils.NotificationsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 作者： 张梓彬
 * 日期： 2017/11/14 0014
 * 时间： 上午 9:58
 * 描述： 内部侧滑式
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String url = "http://i1.img.969g.com/pub/imgx2016/09/14/284_093744_76e89_lit.gif";
    @BindView(R.id.toggleButton)
    ToggleButton toggleBtn;

    private JumpActivityUtils jumpActivityUtils;
    FragmentManager manager;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private VersionUtils versionUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        jumpActivityUtils = new JumpActivityUtils(this);
        manager = getSupportFragmentManager();
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        versionUtils = new VersionUtils(this);
        setReplaceFragment(R.id.lay_fram, firstFragment);

        NotificationsUtils.init(this);
        //切换开关
//        toggleBtn.toggle();
        //切换无动画
//        toggleBtn.toggle(false);
        //开关切换事件
        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (on){
                    versionUtils.setBackground(true,R.mipmap.ic_launcher,true);
                    versionUtils.getUpVersion("2.0.2", "描述", "http://192.168.0.104/debug.apk ", "debug.apk ", "ceshi");
                }else {
                    versionUtils.setBackground(false,R.mipmap.ic_launcher,false);
                    versionUtils.getUpVersion("2.0.2", "描述", "http://192.168.0.104/debug.apk ", "debug.apk ", "ceshi");
                }
            }
        });

    }


    //设置fragmnent
    protected void setReplaceFragment(@IdRes int containerViewId, Fragment fragment) {
        FragmentUtils.replaceFragment(manager, containerViewId, fragment, false);

    }

    @OnClick({R.id.btn_jump1, R.id.btn_jump2, R.id.btn_jump3, R.id.btn_jump4, R.id.btn_jump5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jump1:
                jumpActivityUtils.jumpActivityRight(RefreshActivity.class);
                /*Intent intent = new Intent(this, RefreshActivity.class);
                startActivity(intent);*/

//                FragmentUtils.replaceFragment(manager, R.id.lay_fram, secondFragment, false);
                break;
            case R.id.btn_jump2:
                FragmentUtils.removeFragment(secondFragment);
                break;
            case R.id.btn_jump3:
//                FragmentUtils.dispatchBackPress(secondFragment);
                FragmentUtils.addFragment(manager, firstFragment, R.id.lay_fram, false);

                break;
            case R.id.btn_jump4:
                FragmentUtils.addFragment(manager, secondFragment, R.id.lay_fram, false);

                break;
            case R.id.btn_jump5:
                FragmentUtils.hideFragments(manager);
                FragmentUtils.showFragment(firstFragment);

                break;
        }
    }
}
