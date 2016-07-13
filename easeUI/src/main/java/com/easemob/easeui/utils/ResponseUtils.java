package com.easemob.easeui.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：HuGuoJun
 * 2016/7/11 09:13
 * 邮箱：www.guojunkuaile@qq.com
 */
public class ResponseUtils {
    /**
     * 判断返回状态
     *
     * @param context
     * @param Statemark
     * @param json
     * @param indujesign
     * @param resinfo
     * @return
     */
    public static boolean isSuccess(Context context, String Statemark, String json, String indujesign, String resinfo) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getString(Statemark).equals(indujesign)) {
                return true;
            } else {
                Toast.makeText(context, jsonObject.getString(resinfo), Toast.LENGTH_SHORT).show();
                Log.i("main", jsonObject.getString(resinfo));
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回数值判断空值
     *
     * @param json
     * @return
     */
    public static String ParaseNull(String json) {
        if (json.equals("null")) {
            return "";
        } else {
            return json;
        }
    }
}
