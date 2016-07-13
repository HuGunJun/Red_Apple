package com.iwind.red_apple.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/7/13 11:30
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_search)
public class SearchAcitivity extends EaseBaseActivity {


    @ViewInject(R.id.query)
    EditText query;
    @ViewInject(R.id.search_clear)
    ImageButton search_clear;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.search_clear:
                query.setText("");
                break;
        }
    }

    @Override
    public void InitView() {

    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (query.getText().toString().equals("")) {
                    search_clear.setVisibility(View.GONE);
                } else {
                    search_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
