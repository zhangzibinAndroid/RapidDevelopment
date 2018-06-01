package com.zzb.zzblibrary.httputils.builder;


import com.zzb.zzblibrary.httputils.OkHttpUtils;
import com.zzb.zzblibrary.httputils.request.OtherRequest;
import com.zzb.zzblibrary.httputils.request.RequestCall;


public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
