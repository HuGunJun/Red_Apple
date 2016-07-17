package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 新增讨论页面
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
    @ViewInject(R.id.et_discuss_title)
    EditText et_discuss_title;

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
        mEaseTitleBar.setRightText(getResources().getString(R.string.next));
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
                if (TextUtils.isEmpty(et_discuss_title.getText().toString())) {
                    Toast(getResources().getString(R.string.please_input_discuss_title));
                    return;
                }
                startActivity(new Intent(context, AddDiscussSelectIndustryAndTax.class).putExtra(ConstantString
                        .FORUM_TITLE, et_discuss_title.getText().toString()));
            }
        });
    }
}
