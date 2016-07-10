package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 用户反馈
 * 作者：HuGuoJun
 * 2016/6/13 09:28
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_usercallback)
public class UserCallBackActivity extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.et_content)
    EditText et_content;

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

    }

    @Override
    public void InitView() {
        title_bar.setTitle(getResources().getString(R.string.user_callback));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightText(getResources().getString(R.string.commit));
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
