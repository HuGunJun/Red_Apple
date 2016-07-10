package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
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
 * User: HuGuoJun
 * Date: 2016-07-06
 * Time: 22:24
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act_detail)
public class DetailActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        x.view().inject(this);
        InitView();
        setonClickListener();
    }

    private void setonClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "分享", Toast.LENGTH_SHORT).show();
            }
        });
        title_bar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_question_describe.getVisibility() == View.VISIBLE) {
                    tv_question_describe.setVisibility(View.GONE);
                } else {
                    tv_question_describe.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void InitView() {
        title_bar.setTitle(getIntent().getExtras().getString(ConstantString.DISSCUSS_TITLE));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.mipmap.ic_launcher);
    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {

    }
}
