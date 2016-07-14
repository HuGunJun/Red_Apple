package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/6/17 10:49
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_about_us)
public class Act_AboutUs extends EaseBaseActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        //给页面设置工具栏
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置工具栏标题
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbar.setTitle("cheeseName");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {

    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {

    }
}
