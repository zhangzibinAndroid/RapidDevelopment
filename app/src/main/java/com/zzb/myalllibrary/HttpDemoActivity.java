package com.zzb.myalllibrary;

import android.content.Intent;
import android.os.Bundle;

import com.zzb.myalllibrary.bean.TestBean;
import com.zzb.zzblibrary.base.BaseViewActivity;
import com.zzb.zzblibrary.httputils.XHttpUtils;
import com.zzb.zzblibrary.utils.LogUtils;
import com.zzb.zzblibrary.utils.takephoto.TakePhotoActivityUtils;
import com.zzb.zzblibrary.widget.AutoTitleBar;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;

public class HttpDemoActivity extends BaseViewActivity {
    private static final String TOKEN = "0A66A4FD-146F-4542-8D7B-33CDEC2981F9";
    private static final String BASEURL = "http://xf.vqune.com";
    private static final String MAIN_URL = BASEURL + "/wsdl/AppService.ashx";

    private TakePhotoActivityUtils takePhotoActivityUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_http_demo;
    }

    @Override
    protected void setTitleBar(AutoTitleBar titleBar) {

    }

    @Override
    protected void onBoundView(Bundle savedInstanceState) {
        takePhotoActivityUtils = new TakePhotoActivityUtils(mContext);
        takePhotoActivityUtils.setOnImageFileCallBack(new TakePhotoActivityUtils.OnImageFileCallBack() {
            @Override
            public void OnImageFile(File file, String imagePath) {
                upPicInterface(file);
            }

            @Override
            public void onImageFiles(List<File> files, List<String> imagePaths) {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        takePhotoActivityUtils.takePhoto();
//        login();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhotoActivityUtils.onActivityResult(requestCode, resultCode, data);
    }


    //解析之后回调
    private void login() {
        Map<String, Object> param = new HashMap<>();
        param.put("Action", "LoginServer");
        param.put("Token", TOKEN);
        param.put("UserId", "teacher2");
        param.put("Pwd", "123456");
        XHttpUtils.getInstance().post(TestBean.class, param, MAIN_URL, new XHttpUtils.OnResultCallBack<TestBean>() {
            @Override
            public void onSuccess(TestBean bean, int id) {
                LogUtils.e("getRet_code: " + bean.getRet_code());
                LogUtils.e("getRet_msg: " + bean.getRet_msg());
                LogUtils.e("getRet_data: " + bean.getRet_data());
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }
        });
    }


    //上传图片文件
    private void upPicInterface(File file) {
        Map<String, Object> param = new HashMap<>();
        param.put("Action", "UploadPic");
        param.put("Token", TOKEN);
        param.put("userid", "176042");
        param.put("typeid", "1");
        param.put("fileValue", file);
        XHttpUtils.getInstance().post(param, MAIN_URL, new XHttpUtils.OnResultCallBack<String>() {
            @Override
            public void onSuccess(String str, int id) {
                LogUtils.e("结果: " + str);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }
        });

    }


}
