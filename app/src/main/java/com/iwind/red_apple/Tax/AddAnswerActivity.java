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
 * 新增讨论
 * 作者：HuGuoJun
 * 2016/6/8 14:37
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act__add_answer)
public class AddAnswerActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar)
    EaseTitleBar mEaseTitleBar;
    @ViewInject(R.id.et_answer)
    EditText et_answer;

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

    public void InitView() {
        mEaseTitleBar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mEaseTitleBar.setTitle(getResources().getString(R.string.add_answer));
        mEaseTitleBar.setRightText(getResources().getString(R.string.brown));
    }


    public void InitData() {

    }

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
                Brown();
            }
        });
    }

    /**
     * 发布回答
     */
    private void Brown() {
        if (TextUtils.isEmpty(et_answer.getText().toString())) {
            Toast(getResources().getString(R.string.please_input_answer));
            return;
        }
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.ADD_ANSWER);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.FORUM_ID, getIntent().getExtras().getString(ConstantString.FORUM_ID));
        params.addBodyParameter(ConstantString.FORUMMESSAGE, et_answer.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

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
