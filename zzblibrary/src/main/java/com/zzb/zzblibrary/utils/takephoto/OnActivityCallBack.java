package com.zzb.zzblibrary.utils.takephoto;

import android.content.Intent;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/21 0021
 * 时间： 下午 2:40
 * 描述： 拍照，调用相册回调
 */

public class OnActivityCallBack {

    public interface OnActivityResultCallBackWithCut {
        void onActivityResultWithCut(int requestCode, int resultCode, Intent data);
    }


    public interface OnActivityResultCallBack {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
