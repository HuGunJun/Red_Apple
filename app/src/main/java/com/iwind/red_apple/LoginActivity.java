package com.iwind.red_apple;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.Mine.Act_ChangePass;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 登录页面
 * 作者：HuGuoJun
 * 2016/6/13 16:03
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_login)
public class LoginActivity extends EaseBaseActivity {
    @ViewInject(R.id.et_username)
    EditText et_username;
    @ViewInject(R.id.et_pass)
    EditText et_pass;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_login);
        x.view().inject(this);
        InitView();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.et_register:
                startActivityForResult(new Intent(context, RegisterActivity.class), 1234);
                break;
            case R.id.tv_forgot_pass:
                startActivity(new Intent(context, Act_ChangePass.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    /**
     * 登陆方法
     */
    private void Login() {
        final String username = et_username.getText().toString();
        final String pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, getResources().getString(R.string.User_name_cannot_be_empty),
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(context, getResources().getString(R.string.Password_cannot_be_empty),
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.LOGIN);
        params.addBodyParameter(ConstantString.LOGIND_ID, username);
        params.addBodyParameter(ConstantString.PASSWORD, pass);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        JSONObject jsonObject = jsonObject1.getJSONObject(ConstantString.OBJ);
                        MyApplication.getInstance().setUserId(jsonObject1.getString(ConstantString
                                .USER_ID));
                        MyApplication.getInstance().setToken(jsonObject1.getString(ConstantString
                                .TOKEN));
                        MyApplication.getInstance().setUserNameAndPwd(username, pass);
                        MyApplication.getInstance().setNickName(ResponseUtils.ParaseNull
                                (jsonObject.getString(ConstantString.NICK_NAME)));
                        MyApplication.getInstance().setUserPic(ResponseUtils.ParaseNull
                                (jsonObject.getString(ConstantString.USER_PIC)));
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log("错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
