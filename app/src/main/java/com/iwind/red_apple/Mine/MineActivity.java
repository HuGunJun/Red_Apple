package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * User: HuGuoJun
 * Date: 2016-07-10
 * Time: 09:16
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act__mine)
public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    public void InitView() {

    }

    public void InitData() {

    }

    public void setOnClickListener() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_myinfo:
                startActivity(new Intent(getApplicationContext(), MyInfoActivity.class));
                break;
            case R.id.rl_my_question:
                startActivity(new Intent(getApplicationContext(), MyQuestionActivity.class));
                break;
            case R.id.rl_my_answer:
                startActivity(new Intent(getApplicationContext(), MyAnswerActivity.class));
                break;
            case R.id.rl_my_collection:
                startActivity(new Intent(getApplicationContext(), MyCollectionActivity.class));
                break;
            case R.id.rl_user_callback:
                startActivity(new Intent(getApplicationContext(), UserCallBackActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            case R.id.lv_sign://签到

                break;
            case R.id.lv_message://消息
                startActivity(new Intent(getApplicationContext(),MessageActivity.class));
                break;
        }
    }
}
