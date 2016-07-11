package com.easemob.easeui.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：HuGuoJun
 * 2016/7/11 09:13
 * 邮箱：www.guojunkuaile@qq.com
 */
public class ResponseUtils {
    public static boolean isSuccess(String mark, String json, String sign) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(mark).equals(sign);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
