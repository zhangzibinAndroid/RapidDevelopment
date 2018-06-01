package com.zzb.myalllibrary.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zzb.myalllibrary.R;
import com.zzb.zzblibrary.base.BaseViewFragment;
import com.zzb.zzblibrary.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BaseViewFragment {

    public SecondFragment() {
        // Required empty public constructor
    }


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtils.e("onCreateView: SecondFragment");
        return inflater.inflate(R.layout.fragment_second, container, false);
    }*/

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_second;
    }



    @Override
    protected void onBoundView(Bundle savedInstanceState, View childrenView) {
        LogUtils.e("onBoundView: 14569");
    }

    @Override
    protected void initView() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
