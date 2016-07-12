package com.iwind.red_apple.App;

import android.app.Application;
import android.content.SharedPreferences;

import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * 应用程序入口
 * 作者：HuGuoJun
 * 2016/6/13 16:58
 * 邮箱：www.guojunkuaile@qq.com
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private static SharedPreferences preferences;
    private static ImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        instance = this;
        preferences = getSharedPreferences(ConstantString.USER_INFO, MODE_PRIVATE);
        options = new ImageOptions.Builder().setFailureDrawableId(R.drawable
                .icon_default_avatar).setUseMemCache(true).setCircular(true).build();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取用户
     *
     * @return
     */
    public String getUserName() {
        return preferences.getString(ConstantString.USER_NAME, "");
    }

    /**
     * @param username
     * @param Pwd
     */
    public void setUserNameAndPwd(String username, String Pwd) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(ConstantString.USER_NAME, username);
        edit.putString(ConstantString.PASSWORD, Pwd);
        edit.commit();
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPass() {
        return preferences.getString(ConstantString.PASSWORD, "");
    }


    /**
     * 获取用户id
     *
     * @return
     */
    public String getUserid() {
        return preferences.getString(ConstantString.USER_ID, "");
    }

    /**
     * 保存用户id
     *
     * @param userIdString
     */
    public void setUserId(String userIdString) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(ConstantString.USER_ID, userIdString);
        edit.commit();
    }

    /**
     * 获取Token
     *
     * @return
     */
    public String getToken() {
        return preferences.getString(ConstantString.TOKEN, "");
    }

    /**
     * 保存用户Token
     *
     * @param tokenString
     */
    public void setToken(String tokenString) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(ConstantString.TOKEN, tokenString);
        edit.commit();
    }


    /**
     * 清除用户信息
     */
    public void clearUserInfo() {
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }


    /**
     * 获取图片缓存设置
     *
     * @return
     */
    public ImageOptions getOptions() {
        if (options != null) {
            return options;
        }
        return new ImageOptions.Builder()
                .setLoadingDrawableId(R.mipmap.ic_launcher).setFailureDrawableId(R.mipmap
                        .icon_default_avatar).setUseMemCache(true).setCircular(true).build();
    }


}
