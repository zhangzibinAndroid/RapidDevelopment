package com.zzb.zzblibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者： 张梓彬
 * 日期： 2017/9/30 0030
 * 时间： 下午 4:22
 * 描述： 时间戳与日期转换 日期格式yyyy/MM/dd HH:mm:ss
 */

public class DateUtils {

    //日期转换成时间戳
    public long getTime(String text) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = format.parse(text);
        long time = date.getTime();
        return time;
    }

    //日期转换成时间戳
    public long getTime(String mformat,String text) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(mformat);
        Date date = format.parse(text);
        long time = date.getTime();
        return time;
    }

    //时间戳转换成日期
    public String getDate(long time){
        String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(time));
        return date;
    }

    //时间戳转换成日期
    public String getDate(String format,long time){
        String date = new SimpleDateFormat(format).format(new Date(time));
        return date;
    }
}
