package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * User: HuGuoJun
 * Date: 2016-07-12
 * Time: 21:12
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act__add_discuss)
public class AddDiscussActivity extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    private EaseTitleBar mEaseTitleBar;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;

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
        mEaseTitleBar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mEaseTitleBar.setTitle(getResources().getString(R.string.add_discuss));
        mEaseTitleBar.setRightText(getResources().getString(R.string.brown));
    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {
        mEaseTitleBar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEaseTitleBar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "发布", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
