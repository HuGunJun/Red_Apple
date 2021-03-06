package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.easemob.easeui.widget.EaseAlertDialog;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
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
    private long exitTime = 0;
    @ViewInject(R.id.iv_avatar)
    ImageView iv_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();

    }

    public void InitView() {
        x.image().bind(iv_avatar, ConstantUrl.USER_PIC + MyApplication.getInstance().getUserPic(), MyApplication
                .getInstance().getOptions());

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
                startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                break;
        }
    }

    // 监听两次返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string
                        .again_login_out), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }
}
