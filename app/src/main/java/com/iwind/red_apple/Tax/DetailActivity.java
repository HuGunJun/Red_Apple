package com.iwind.red_apple.Tax;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.print.PageRange;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.Adapter.ShouldKnowAdapter;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

/**
 * User: HuGuoJun
 * Date: 2016-07-06
 * Time: 22:24
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act_detail)
public class DetailActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.tv_describe)
    TextView tv_describe;
    @ViewInject(R.id.tv_content)
    TextView tv_content;
    @ViewInject(R.id.tv_great_count)
    TextView tv_great_count;
    @ViewInject(R.id.tv_downcount)
    TextView tv_down_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        Update(1);
        setOnClickListener();
    }


    /**
     * 1查看 2分享
     *
     * @param i
     */
    private void Update(final int i) {
        RequestParams params = null;
        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_PROBLEM);
            params.addBodyParameter(ConstantString.PROBLEM_ID, getIntent().getExtras().getString
                    (ConstantString.PROBLEM_ID));
        }
        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_WORK);
            params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString
                    (ConstantString.WORK_ID));
        }
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_collection:
                Collection();
                break;
            case R.id.lv_cai:
                MethodCaiOrZan(1);
                break;
            case R.id.lv_zan:
                MethodCaiOrZan(2);
                break;
            case R.id.lv_personal:
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
        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
            params.addBodyParameter(ConstantString.PROBLEM_ID, getIntent().getExtras().getString
                    (ConstantString.PROBLEM_ID));
        }
        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
            params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString
                    (ConstantString.WORK_ID));
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
                InitData();
            }
        });

    }


    /**
     * 收藏
     */
    private void Collection() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.COLLECTION);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
            params.addBodyParameter(ConstantString.MOUDEL_ID, getIntent().getExtras().getString
                    (ConstantString.PROBLEM_ID));
            params.addBodyParameter(ConstantString.MOUDEL_TYPE, "2");//2为问题详情收藏
        }
        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
            params.addBodyParameter(ConstantString.MOUDEL_ID, getIntent().getExtras().getString
                    (ConstantString.WORK_ID));
            params.addBodyParameter(ConstantString.MOUDEL_TYPE, "1");//1为办税须知详情收藏
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log(result);
                        if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                                ConstantString.STATE,
                                ConstantString.RESULT_INFO)) {
                            Toast("操作成功");
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
                }

        );
    }


    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.drawable.iv_share);
    }

    @Override
    public void InitData() {
        RequestParams params = null;
        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                    .GET_PROBLEM_DETAIL);
            params.addBodyParameter(ConstantString.PROBLEM_ID, getIntent().getExtras().getString
                    (ConstantString.PROBLEM_ID));
        }
        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
            params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                    .GET_WORK_DETAIL);
            params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString
                    (ConstantString.WORK_ID));
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
                        JSONObject object = jsonObject.getJSONObject(ConstantString.OBJ);
                        if (getIntent().getExtras().getString(ConstantString.PROBLEM_ID) != null) {
                            tv_content.setText(ResponseUtils.ParaseNull
                                    (object.getString
                                            (ConstantString.PROBLEM_CONTENT)));
                            title_bar.setTitle(ResponseUtils.ParaseNull(object.getString
                                    (ConstantString.PROBLEM_TITLE)));
                            tv_describe.setText(ResponseUtils.ParaseNull(object.getString
                                    (ConstantString.PROBLEM_TITLE)));
                        }
                        if (getIntent().getExtras().getString(ConstantString.WORK_ID) != null) {
                            tv_content.setText(ResponseUtils.ParaseNull
                                    (object.getString
                                            (ConstantString.WORK_CONTENT)));
                            title_bar.setTitle(ResponseUtils.ParaseNull(object.getString
                                    (ConstantString.WORK_TITLE)));
                            tv_describe.setText(ResponseUtils.ParaseNull(object.getString
                                    (ConstantString.WORK_TITLE)));
                        }


                        tv_great_count.setText(ResponseUtils.ParaseNull(object.getString
                                (ConstantString.ZANCOUTN))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(object.getString
                                (ConstantString
                                        .ZANCOUTN)));
                        tv_down_count.setText(ResponseUtils.ParaseNull(object.getString
                                (ConstantString.CAICOUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(object.getString
                                (ConstantString
                                        .CAICOUNT)));
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
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowLoadingDialog();
                Update(2);
                Toast.makeText(getApplicationContext(), "分享", Toast.LENGTH_SHORT).show();
            }
        });
        title_bar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_describe.getVisibility() == View.VISIBLE) {
                    tv_describe.setVisibility(View.GONE);
                } else {
                    tv_describe.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
