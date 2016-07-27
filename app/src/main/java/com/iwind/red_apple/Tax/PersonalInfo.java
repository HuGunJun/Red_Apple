package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/7/20 10:23
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_personalinfo)
public class PersonalInfo extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.et_content)
    EditText et_content;

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
        title_bar.setTitle(getResources().getString(R.string.send_personal_info));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightText(getResources().getString(R.string.button_send));
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
                Send();
            }
        });

    }

    /**
     * 发送私信
     */
    private void Send() {
        if (TextUtils.isEmpty(et_content.getText().toString())) {
            Toast(getResources().getString(R.string.please_input_personal_info));
            return;
        }
        RequestParams params = null;
        //问题库私信
        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEND_PROBLEM_PERSONAL_INFO);
            params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
            params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
            params.addBodyParameter(ConstantString.PROBLEM_ID, getIntent().getExtras().getString(ConstantString
                    .PROBLEM_ID));
            params.addBodyParameter(ConstantString.PROBLEM_PERSONAL_CONTENT, et_content.getText().toString());

        }
        //办税须知私信
        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEND_WORK_PERSONAL_INFO);
            params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
            params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
            params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString(ConstantString
                    .PROBLEM_ID));
            params.addBodyParameter(ConstantString.WORK_PERSONAL_CONTENT, et_content.getText().toString());

        }
        //客户端私信
        if (getIntent().getExtras().getString(ConstantString.CLIENT_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEND_CLIENT_PERSONAL_INFO);
            params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
            params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
            params.addBodyParameter(ConstantString.CLIENT_ID, getIntent().getExtras().getString(ConstantString
                    .CLIENT_ID));
            params.addBodyParameter(ConstantString.CLIENT_MESSAGE_CONTENT, et_content.getText().toString());
        }
        ShowLoadingDialog();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
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
                CloseLoadingDialog();
            }
        });


    }
}
