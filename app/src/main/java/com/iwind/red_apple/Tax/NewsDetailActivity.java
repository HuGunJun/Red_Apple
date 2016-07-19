package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.service.carrier.CarrierIdentifier;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DateUtils;
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
 * 2016/7/6 15:32
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act__news_detail)
public class NewsDetailActivity extends EaseBaseActivity implements View.OnClickListener {


    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_news_content)
    TextView tv_news_content;
    @ViewInject(R.id.tv_messagecount)
    TextView tv_messagecount;
    @ViewInject(R.id.tv_sharenumber)
    TextView tv_sharenumber;
    @ViewInject(R.id.tv_seeknumber)
    TextView tv_seeknumber;
    @ViewInject(R.id.tv_cai_count)
    TextView tv_cai_count;
    @ViewInject(R.id.tv_zan_count)
    TextView tv_zan_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        Update(1);
        setOnClickListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_zan:
                MethodCaiOrZan(2);
                break;
            case R.id.lv_cai:
                MethodCaiOrZan(1);
                break;
            case R.id.lv_collection:
                break;
        }
    }


    private void Update(final int i) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_NEW);
        params.addBodyParameter(ConstantString.NEW_ID, getIntent().getExtras().getString(ConstantString.NEW_ID));
        if (i == 1) {
            params.addBodyParameter(ConstantString.TYPE, i + "");
        }
        if (i == 2) {
            params.addBodyParameter(ConstantString.TYPE, i + "");
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (i == 1) {
                            InitData();
                        }
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


    /**
     * 新闻踩赞
     *
     * @param i
     */
    private void MethodCaiOrZan(final int i) {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.NEW_CAIORZAN);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.NEW_CAIORZAN, i + "");
        params.addBodyParameter(ConstantString.NEW_ID, getIntent().getExtras().getString
                (ConstantString.NEW_ID));
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
    public void InitView() {
        title_bar.setTitle(getIntent().getExtras().getString(ConstantString.NEW_TITLE));
        tv_question_describe.setText(getIntent().getExtras().getString(ConstantString.NEW_TITLE));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.drawable.iv_share);
    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_NEWS_DETAIL);
        params.addBodyParameter(ConstantString.NEW_ID, getIntent().getExtras().getString(ConstantString.NEW_ID));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(ConstantString.OBJ);
                        tv_time.setText(DateUtils.ParseTimeMillisToTime(jsonObject1.getString(ConstantString
                                .NEW_TIME)));
                        tv_news_content.setText(Html.fromHtml(ResponseUtils.ParaseNull(jsonObject1.getString
                                (ConstantString
                                        .NEW_CONTENT))));
                        tv_zan_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.ZANCOUTN))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .ZANCOUTN)));
                        tv_cai_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.CAICOUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CAICOUNT)));
                        tv_seeknumber.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.SEEKNUMBER))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .SEEKNUMBER)));
                        tv_sharenumber.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .SHARENUMBER))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .SHARENUMBER)));
                        tv_messagecount.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .MESSAGE_COUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .MESSAGE_COUNT)));
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
                Update(2);
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
}
