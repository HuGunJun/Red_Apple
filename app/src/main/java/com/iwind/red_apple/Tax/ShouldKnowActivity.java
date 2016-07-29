package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.ShouldKnowAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 税务须知
 * 作者：HuGuoJun
 * 2016/7/6 17:33
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_should_know)
public class ShouldKnowActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.lv_should_know)
    XListView lv_should_know;
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;

    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private ShouldKnowAdapter mNewsAdapter;
    private int page;

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
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_should_know.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(ConstantString.WORK_TITLE, mList.get(position - 1).get(ConstantString.WORK_TITLE));
                intent.putExtra(ConstantString.WORK_ID, mList.get(position - 1).get(ConstantString.WORK_ID));
                startActivity(intent);
            }
        });
        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddDiscussActivity.class));
            }
        });
        title_bar.setRightImageLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchAcitivity.class));
            }
        });
        lv_should_know.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mList.clear();
                InitData();
            }

            @Override
            public void onLoadMore() {
                page++;
                InitData();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.should_know));
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        lv_should_know.setPullLoadEnable(true);
        lv_should_know.setPullRefreshEnable(true);
    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.TAX_SHOULDKNOW);
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        params.addBodyParameter(ConstantString.PAGE, page + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                lv_should_know.stopRefresh();
                lv_should_know.stopLoadMore();
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        if (jsonArray.length() < 0) {
                            Toast(getResources().getString(R.string.no_more_data));
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.WORK_ID, jsonArray.getJSONObject(i).getString(ConstantString
                                    .WORK_ID));
                            hashMap.put(ConstantString.WORK_CONTENT, ResponseUtils.ParaseNull(jsonArray.getJSONObject
                                    (i).getString
                                    (ConstantString.WORK_CONTENT)));
                            hashMap.put(ConstantString.WORK_LABEL, jsonArray.getJSONObject(i).getString
                                    (ConstantString.WORK_LABEL));
                            hashMap.put(ConstantString.WORK_TITLE, jsonArray.getJSONObject(i).getString
                                    (ConstantString.WORK_TITLE));
                            hashMap.put(ConstantString.ZANCOUTN, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString
                                            (ConstantString.ZANCOUTN)).equals("") ? "0" : ResponseUtils.ParaseNull
                                    (jsonArray
                                            .getJSONObject(i).getString
                                                    (ConstantString.ZANCOUTN)));
                            mList.add(hashMap);
                        }
                        mNewsAdapter = new ShouldKnowAdapter(context, mList);
                        lv_should_know.setAdapter(mNewsAdapter);
                        mNewsAdapter.notifyDataSetChanged();
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
