package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 新增讨论
 * 作者：HuGuoJun
 * 2016/6/8 14:37
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act__add_answer)
public class AddAnswerActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar)
    EaseTitleBar mEaseTitleBar;

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

    public void InitView() {
        mEaseTitleBar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mEaseTitleBar.setTitle(getResources().getString(R.string.add_answer));
        mEaseTitleBar.setRightText(getResources().getString(R.string.brown));
    }


    public void InitData() {

    }

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
