package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
 * 新增讨论现在行业和税种页面
 * 作者：HuGuoJun
 * 2016/7/14 14:18
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_add_discuss_select_industry_and_tax)
public class AddDiscussSelectIndustryAndTax extends EaseBaseActivity {


    public static final int REQUEST_INDUSTRY = 1001;//
    public static final int REQUEST_TYPE = 1002;//

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    String discussTitle;
    @ViewInject(R.id.et_discuss_content)
    EditText et_discuss_content;
    @ViewInject(R.id.tv_industry)
    TextView tv_industry;
    @ViewInject(R.id.tv_type)
    TextView tv_type;
    String hlabel = "", label = "";

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
            case R.id.rl_tax_type:
                startActivityForResult(new Intent(context, IndustryAndTypeSelectActivity.class).putExtra
                        (ConstantString.TYPE, REQUEST_TYPE), REQUEST_TYPE);
                break;
            case R.id.rl_industry:
                startActivityForResult(new Intent(context, IndustryAndTypeSelectActivity.class).putExtra
                        (ConstantString.TYPE, REQUEST_INDUSTRY), REQUEST_INDUSTRY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_INDUSTRY:
                if (data != null) {
                    hlabel = data.getExtras().getString(ConstantString.LABEL_ID);
                    tv_industry.setText(data.getExtras().getString(REQUEST_INDUSTRY + ""));
                }
                break;
            case REQUEST_TYPE:
                if (data != null) {
                    label = data.getExtras().getString(ConstantString.LABEL_ID);
                    tv_type.setText(data.getExtras().getString(REQUEST_TYPE + ""));
                }
                break;
        }

    }

    @Override
    public void InitView() {
        discussTitle = getIntent().getStringExtra(ConstantString.FORUM_TITLE);
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.add_discuss));
        title_bar.setRightText(getResources().getString(R.string.done));
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
     * 提交
     */
    private void Commit() {
        if (TextUtils.isEmpty(discussTitle)) {
            Toast(getResources().getString(R.string.please_input_discuss_title));
            return;
        }
        if (TextUtils.isEmpty(tv_industry.getText().toString())) {
            Toast("请进行" + getResources().getString(R.string.industry_select));
            return;
        }
        if (TextUtils.isEmpty(tv_type.getText().toString())) {
            Toast("请进行" + getResources().getString(R.string.tax_type_select));
            return
                    ;
        }
        if (TextUtils.isEmpty(et_discuss_content.getText().toString())) {
            Toast(getResources().getString(R.string.please_input_discuss));
            return;
        }

        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.ADD_FORUM);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.FORUM_TITLE, discussTitle);
        params.addBodyParameter(ConstantString.FORUM_CONTENT, et_discuss_content.getText().toString());
        params.addBodyParameter(ConstantString.LABLES, label + hlabel);
        params.addBodyParameter(ConstantString.KEYWORD, tv_industry.getText().toString() + "," + tv_type.getText()
                .toString() + "," + discussTitle);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        setResult(RESULT_OK);
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
