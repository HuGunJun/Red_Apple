package com.iwind.red_apple.Find;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * User: HuGuoJun
 * Date: 2016-07-10
 * Time: 09:15
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act__find)
public class FindActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_news:
                break;
            case R.id.lv_guide_video:
                break;
            case R.id.lv_should_know:
                break;
            case R.id.lv_nomal_know:
                break;
            case R.id.lv_personal_industry:
                break;
        }
    }
}
