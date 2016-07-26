package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.LoginActivity;
import com.iwind.red_apple.MainActivity;
import com.iwind.red_apple.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 修改密码
 * 作者：HuGuoJun
 * 2016/6/13 14:59
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_change_pass)
public class Act_ChangePass extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.btn_get_vifi_code)
    Button btn_get_vifi_code;
    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_vificode)
    EditText et_vificode;
    @ViewInject(R.id.et_pass)
    EditText et_pass;

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
            case R.id.btn_get_vifi_code:
                SendSmsCode();
                break;
        }
    }


    /**
     * 发送短信验证码
     */
    private void SendSmsCode() {
        String phone = et_username.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SENDFORGOTPASS);
        params.addBodyParameter(ConstantString.PHONE_NUM, phone);
        params.addBodyParameter(ConstantString.TYPE, "4");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    time.start();// 开始计时
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
            btn_get_vifi_code.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    @Override
    public void InitView() {
        time = new TimeCount(60000, 1000);
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.change_pass));
        title_bar.setRightText(getResources().getString(R.string.commit));
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
     * 修改密码
     */
    private void Commit() {
        String vifi_code = et_vificode.getText().toString();
        String phone = et_username.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(vifi_code)) {
            Toast.makeText(context, getResources().getString(R.string.vifi_code_can_not_be_empty), Toast
                    .LENGTH_SHORT).show();
            return;
        }
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.FORGOTPASS);
        params.addBodyParameter(ConstantString.PHONE_NUM, phone);
        params.addBodyParameter(ConstantString.TYPE, "4");
        params.addBodyParameter(ConstantString.CAPCHA, vifi_code);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        SeconsStep();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void SeconsStep() {
        String pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        String phone = et_username.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.CHAGE_PASS);
        params.addBodyParameter(ConstantString.PASSWORD, pass);
        params.addBodyParameter(ConstantString.PHONE_NUM, phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        setResult(RESULT_OK);
                        startActivity(new Intent(context, LoginActivity.class));
                        MainActivity.instance.finish();
                        MyApplication.getInstance().clearUserInfo();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
