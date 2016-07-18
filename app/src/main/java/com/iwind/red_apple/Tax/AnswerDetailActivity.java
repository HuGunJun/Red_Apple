package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
 * 2016/7/6 12:01
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act__answer_detail)
public class AnswerDetailActivity extends EaseBaseActivity {


    @ViewInject(R.id.title_bar)
    private EaseTitleBar title_bar;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.iv_avator)
    ImageView iv_avator;
    @ViewInject(R.id.tv_userdescirbe)
    TextView tv_userdescirbe;
    @ViewInject(R.id.tv_great_count)
    TextView tv_great_count;
    @ViewInject(R.id.tv_downcount)
    TextView tv_downcount;
    @ViewInject(R.id.tv_answer_content)
    TextView tv_answer_content;
    String forummessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        InitData();
        setOnClickListener();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_great:
                MethodCaiOrZan(2);
                break;
            case R.id.lv_down:
                MethodCaiOrZan(1);
                break;
            case R.id.lv_collection:
                break;
        }
    }

    /**
     * 讨论回答踩赞
     *
     * @param i
     */
    private void MethodCaiOrZan(final int i) {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.FORUM_ZAIORCAI);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.CAIZANTYPE, i + "");
        params.addBodyParameter(ConstantString.FORUMMESSAGEID, getIntent().getExtras().getString
                (ConstantString.FORUMMESSAGEID));
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
                ShowLoadingDialog();
                InitData();
            }
        });

    }

    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
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
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                .GET_ANSWER_DETAIL);
        params.addBodyParameter(ConstantString.FORUMMESSAGEID, getIntent().getExtras().getString
                (ConstantString.FORUMMESSAGEID));
        Log(ConstantUrl.BASE_URL + ConstantUrl
                .GET_ANSWER_DETAIL + "?" + ConstantString.FORUMMESSAGEID + "=" + getIntent()
                .getExtras()
                .getString
                        (ConstantString.FORUMMESSAGEID));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject OBJ = jsonObject.getJSONObject(ConstantString.OBJ);

                        tv_name.setText(ResponseUtils.ParaseNull(OBJ.getString(ConstantString
                                .NICK_NAME)));
                        tv_great_count.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.ZANCOUTN)).equals("") ? "0" : ResponseUtils
                                .ParaseNull(OBJ.getString
                                        (ConstantString.ZANCOUTN)));
                        tv_downcount.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.CAICOUNT)).equals("") ? "0" : ResponseUtils
                                .ParaseNull(OBJ.getString
                                        (ConstantString.CAICOUNT)));
                        forummessage = ResponseUtils.ParaseNull(OBJ.getString(ConstantString
                                .FORUMMESSAGE));
                        tv_answer_content.setText(ResponseUtils.ParaseNull(OBJ.getString(ConstantString
                                .FORUMMESSAGE)));
                        tv_userdescirbe.setText(ResponseUtils.ParaseNull(OBJ.getString(ConstantString.POSITON)));
                        x.image().bind(iv_avator, ConstantUrl.USER_PIC +
                                OBJ.getString(ConstantString.USER_PIC), MyApplication.getInstance
                                ().getOptions());
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

    @Override
    public void InitView() {
        title_bar.setTitle(getIntent().getExtras().getString(ConstantString.FORUM_TITLE));
        tv_question_describe.setText(getIntent().getExtras().getString(ConstantString.FORUM_TITLE));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.drawable.iv_share);
    }
}
