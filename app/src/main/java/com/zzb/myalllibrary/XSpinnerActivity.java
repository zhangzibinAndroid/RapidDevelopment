package com.zzb.myalllibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzb.zzblibrary.utils.ToastUtils;
import com.zzb.zzblibrary.widget.spinner.DefaultSpinnerAdapter;
import com.zzb.zzblibrary.widget.spinner.XSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XSpinnerActivity extends AppCompatActivity {

    @BindView(R.id.xSpinner)
    XSpinner xSpinner;
    private List<String> stringList = new ArrayList<>();
    private DefaultSpinnerAdapter defaultSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xspinner);
        ButterKnife.bind(this);
        stringList.add("啦啦");
        stringList.add("玩");
        stringList.add("打算啦");
        stringList.add("啦打啦");
        defaultSpinnerAdapter = new DefaultSpinnerAdapter(this);
        xSpinner.setAdapter(defaultSpinnerAdapter);
        xSpinner.setSelection(0, true);
        defaultSpinnerAdapter.addAllDataToMyadapter(stringList);

        xSpinner.setDropDownVerticalOffset(R.dimen.dp20);
    }

    @OnClick(R.id.btn_toast)
    public void onViewClicked() {
        ToastUtils.show(XSpinnerActivity.this,"这是个toast",10000);
    }
}
