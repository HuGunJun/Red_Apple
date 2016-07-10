package com.iwind.red_apple;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 注册页面
 * 作者：HuGuoJun
 * 2016/6/13 17:30
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_register)
public class RegisterActivity extends EaseBaseActivity {

    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_vificode)
    EditText et_vificode;
    @ViewInject(R.id.et_pass)
    EditText et_pass;
    @ViewInject(R.id.cb_introduce)
    CheckBox cb_introduce;
    @ViewInject(R.id.btn_get_vifi_code)
    Button btn_get_vifi_code;

    private TimeCount time;


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
            case R.id.btn_register:
                Register();
                break;
            case R.id.btn_get_vifi_code:
                SendSmsCode();
                break;
        }
    }

    /**
     * 发送短信验证码
     */
    private void SendSmsCode() {
        String username = et_username.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        time.start();// 开始计时

    }


    /**
     * 注册方法
     */
    private void Register() {

        final String username = et_username.getText().toString();
        final String pass = et_pass.getText().toString();
        final String vifi_code = et_vificode.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(vifi_code)) {
            Toast.makeText(context, getResources().getString(R.string.vifi_code_can_not_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cb_introduce.isChecked()) {
            Toast.makeText(context, getResources().getString(R.string.please_read_introduce), Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    @Override
    public void InitView() {
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn_get_vifi_code.setText(getResources().getString(R.string.get_sms_code));
            btn_get_vifi_code.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn_get_vifi_code.setClickable(false);//防止重复点击
            btn_get_vifi_code.setText(millisUntilFinished / 1000 + "");
        }
    }
}
