package com.iwind.red_apple.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easemob.easeui.widget.loadding.ZProgressHUD;

/**
 * 作者：HuGuoJun
 * 2016/7/18 12:42
 * 邮箱：www.guojunkuaile@qq.com
 */
public class BaseFragment extends Fragment {

    private ZProgressHUD progressHUD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 显示加载对话框
     */
    public void ShowLoadingDialog() {
        progressHUD = ZProgressHUD.getInstance(getActivity());
        progressHUD.setMessage("请稍后");
        progressHUD.setCancelable(true);
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
    }

    /**
     * 关闭加载对话框
     */
    public void CloseLoadingDialog() {
        progressHUD = ZProgressHUD.getInstance(getActivity());
        progressHUD.dismiss();
    }


    public void Log(String str) {
        Log.i("main", str);
    }
}
