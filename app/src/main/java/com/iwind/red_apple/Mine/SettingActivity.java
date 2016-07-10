package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.MainActivity;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置页面
 * 作者：HuGuoJun
 * 2016/6/13 09:38
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_setting)
public class SettingActivity extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        InitView();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_pass:
                startActivity(new Intent(context, Act_ChangePass.class));
                break;
            case R.id.btn_quite:
                MyApplication.getInstance().clearUserInfo();
                MainActivity.instance.finish();
                finish();
                break;
            case R.id.rl_about_us:
                startActivity(new Intent(context, Act_AboutUs.class));
                break;
        }
    }

    @Override
    public void InitView() {
        title_bar.setTitle(getResources().getString(R.string.setting));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
