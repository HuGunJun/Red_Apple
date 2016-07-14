package com.easemob.easeui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：HuGuoJun
 * 2016/7/14 13:30
 * 邮箱：www.guojunkuaile@qq.com
 */
public class DateUtils {

    /**
     * 将毫秒转换为时间
     *
     * @param timeStr
     * @return
     */
    public static String ParseTimeMillisToTime(String timeStr) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.parseLong(timeStr)));
    }
}
