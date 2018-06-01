package com.zzb.zzblibrary.utils;

import com.google.gson.Gson;

/**
 * 作者： 张梓彬
 * 日期： 2017/11/13 0013
 * 时间： 下午 3:55
 * 描述： Gson解析
 */

public class GsonUtils {

    //将Json数据解析成相应的映射对象
    public static <M> M parseJsonWithGson(String jsonData, Class<M> type) {
        try {
            Gson gson = new Gson();
            M result = gson.fromJson(jsonData, type);
            return result;
        } catch (Exception e) {
            return null;
        }
    }


}
