package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DateUtils;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/7/19 17:31
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_message_detail)
public class MessageDetailActivity extends EaseBaseActivity {
    @ViewInject(R.id.tv_message_content)
    TextView tv_message_content;
    @ViewInject(R.id.tv_time)
    TextView tv_time;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        InitView();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {
        tv_message_content.setText(getIntent().getExtras().getString(ConstantString.TIDING_CONTENT));
        tv_time.setText(DateUtils.ParseTimeMillisToTime(getIntent().getExtras().getString(ConstantString.TIDING_TIME)));

    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {

    }
}
