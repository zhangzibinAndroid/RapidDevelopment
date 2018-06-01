package com.zzb.myalllibrary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.zzb.myalllibrary.adapter.JinMoAdapter;
import com.zzb.zzblibrary.refreshview.BaseDividerItem;
import com.zzb.zzblibrary.refreshview.RefreshRecycleView;
import com.zzb.zzblibrary.refreshview.call.RefreshCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshActivity extends AppCompatActivity implements RefreshCall {

    @BindView(R.id.refreshRecycleView)
    RefreshRecycleView refreshRecycleView;
//    private CeHuaAdapter ceHuaAdapter;
    private JinMoAdapter jinMoAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);

        //设置刷新接口，默认不能刷新
        refreshRecycleView.setRefreshCall(this);

        //设置是否可以上拉刷新
        refreshRecycleView.setPullLoadEnable(true);
//        设置是否可以下拉刷新
        refreshRecycleView.setPullRefreshEnable(true);
        //解决滑动冲突
        refreshRecycleView.setMoveForHorizontal(true);

        refreshRecycleView.setAutoLoadMore(true);
        //设置分割线
        refreshRecycleView.addItemDecoration(new BaseDividerItem(10, Color.RED));

//        ceHuaAdapter = new CeHuaAdapter(this);
        jinMoAdapter = new JinMoAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        refreshRecycleView.setAdapter(jinMoAdapter);
        refreshRecycleView.setLayoutManager(linearLayoutManager);

        // 设置静默加载模式
        refreshRecycleView.setSilenceLoadMore(true);

        //设置静默加载时提前加载的item个数
        refreshRecycleView.setPreLoadCount(4);

        refreshRecycleView.enableReleaseToLoadMore(false);
        refreshRecycleView.enableRecyclerViewPullUp(true);
        refreshRecycleView.enablePullUpWhenLoadCompleted(true);


        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        jinMoAdapter.addData(list);
        refreshRecycleView.showEmptyImage(jinMoAdapter.getList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onPullRefresh(boolean isPullDown) {
        refreshRecycleView.setAdapter(jinMoAdapter);
        refreshRecycleView.setLayoutManager(linearLayoutManager);
        list.clear();
        jinMoAdapter.getList().clear();
        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        jinMoAdapter.addData(list);
//        refreshRecycleView.stopRefresh();
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        if (list.size()>29){
            refreshRecycleView.setLoadComplete(true);
            refreshRecycleView.stopLoadMore();
        }else {
            for (int i = 0; i < 10; i++) {
                list.add("1");
                jinMoAdapter.addData("1");

            }
            jinMoAdapter.notifyDataSetChanged();
            // 刷新完成必须调用此方法停止加载
            refreshRecycleView.stopLoadMore();
        }


    }
}
