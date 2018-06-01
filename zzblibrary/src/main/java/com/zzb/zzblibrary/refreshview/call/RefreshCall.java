package com.zzb.zzblibrary.refreshview.call;

/**
 * 作者： 张梓彬
 * 日期： 2018/1/9 0009
 * 时间： 上午 10:56
 * 描述： 刷新接口销毁处理
 */

public interface RefreshCall {
    void onPullRefresh(boolean isPullDown);

    void onLoadMore(boolean isSilence);

}
