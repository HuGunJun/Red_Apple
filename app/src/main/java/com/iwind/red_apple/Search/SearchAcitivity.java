package com.iwind.red_apple.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
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
 * 2016/7/13 11:30
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_search)
public class SearchAcitivity extends EaseBaseActivity {


    @ViewInject(R.id.query)
    EditText query;
    @ViewInject(R.id.search_clear)
    ImageButton search_clear;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.search_clear:
                query.setText("");
                break;
            case R.id.search_all:
                break;
            case R.id.search_discuss:
                break;
            case R.id.search_question:
                break;
            case R.id.search_should_know:
                break;
            case R.id.search_country_client:
                break;
            case R.id.search_local_client:
                break;
            case R.id.search_news:
                break;
        }
    }


    @Override
    public void InitView() {

    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (query.getText().toString().equals("")) {
                    search_clear.setVisibility(View.GONE);
                    Search(query.getText().toString());
                } else {
                    search_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 搜索
     *
     * @param s
     */
    private void Search(String s) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEARCH);
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, s);
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

            }
        });

    }

}
