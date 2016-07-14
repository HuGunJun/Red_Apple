package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/7/14 12:00
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_message)
public class MessageActivity extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.lv_message)
    XListView lv_message;



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
