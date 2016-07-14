package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 新增讨论现在行业和税种页面
 * 作者：HuGuoJun
 * 2016/7/14 14:18
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_add_discuss_select_industry_and_tax)
public class AddDiscussSelectIndustryAndTax extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    String discussTitle;
    @ViewInject(R.id.et_discuss_content)
    EditText et_discuss_content;

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
            case R.id.rl_tax_type:
                break;
            case R.id.rl_industry:
                startActivity(new Intent(context, IndustrySelectActivity.class));
                break;
        }
    }

    @Override
    public void InitView() {
        discussTitle = getIntent().getStringExtra(ConstantString.DISSCUSS_TITLE);
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.add_discuss));
        title_bar.setRightText(getResources().getString(R.string.done));
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
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commit();
            }
        });

    }

    /**
     * 提交
     */
    private void Commit() {
        ShowLoadingDialog();
    }
}
