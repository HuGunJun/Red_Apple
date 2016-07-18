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
        setonClickListener();
    }


    private void Update(final int i) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_WORK);
        params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString(ConstantString.WORK_ID));
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

            }
        });
    }

    private void setonClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    @Override
    public void onClick(View v) {

    }

    @Override
    public void InitView() {
        title_bar.setTitle(getIntent().getExtras().getString(ConstantString.WORK_TITLE));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.drawable.iv_share);
    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_WORK_DETAIL);
        params.addBodyParameter(ConstantString.WORK_ID, getIntent().getExtras().getString(ConstantString.WORK_ID));
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
                        tv_describe.setText(ResponseUtils.ParaseNull(jsonObject.getString(ConstantString.WORK_TITLE)));
                        tv_content.setText(ResponseUtils.ParaseNull(object.getString(ConstantString.WORK_CONTENT)));
                        tv_great_count.setText(ResponseUtils.ParaseNull(object.getString(ConstantString.ZANCOUTN))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(object.getString(ConstantString
                                .ZANCOUTN)));
                        tv_down_count.setText(ResponseUtils.ParaseNull(object.getString(ConstantString.CAICOUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(object.getString(ConstantString
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
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
