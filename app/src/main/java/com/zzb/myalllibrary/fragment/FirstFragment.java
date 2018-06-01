package com.zzb.myalllibrary.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zzb.myalllibrary.R;
import com.zzb.zzblibrary.base.BaseViewFragment;
import com.zzb.zzblibrary.utils.FragmentUtils;
import com.zzb.zzblibrary.utils.LogUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseViewFragment {

    private SecondsFragment secondsFragment;
    public FirstFragment() {
        // Required empty public constructor
    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtils.e("onCreateView: FirstFragment");
        return inflater.inflate(R.layout.fragment_first, container, false);
    }*/

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_first;
    }



    @Override
    protected void onBoundView(Bundle savedInstanceState, View childrenView) {

        /*Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                LogUtils.e("run: 1");
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                LogUtils.e("run: 2");
            }
        };
        addThreads(runnable1,runnable2);*/
        secondsFragment = new SecondsFragment();
        FragmentUtils.addFragment(getChildFragmentManager(),secondsFragment,R.id.layFrams);


    }

    @Override
    protected void initView() {
        LogUtils.e("initView: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        LogUtils.e("onDetach: FirstFragment");
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        LogUtils.e("onDestroyView: FirstFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if(fragment==null)
                    continue;
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


}
