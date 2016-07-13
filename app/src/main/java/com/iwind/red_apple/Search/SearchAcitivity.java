package com.iwind.red_apple.Search;

import android.os.Bundle;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/7/13 11:30
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_search)
public class SearchAcitivity extends EaseBaseActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
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
