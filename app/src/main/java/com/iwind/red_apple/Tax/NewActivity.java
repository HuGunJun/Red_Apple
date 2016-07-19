package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DateUtils;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseChatMessageList;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.NewsAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 资讯页面
 * 作者：HuGuoJun
 * 2016/6/12 10:43
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_news)
public class NewActivity extends EaseBaseActivity {
    @ViewInject(R.id.lv_news)
    XListView lv_news;
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    NewsAdapter mNewsAdapter;
    List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    int page = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        ShowLoadingDialog();
        InitView();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {

    }


    public void InitView() {
        title_bar.setTitle("资讯");
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
    }

    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEARCH_NEWS);
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, "");
        params.addBodyParameter(ConstantString.PAGE, page + "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
                            hashMap.put(ConstantString.NEW_ID, jsonArray.getJSONObject(i).getString(ConstantString
                                    .NEW_ID));
                            hashMap.put(ConstantString.ZANCOUTN, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.ZANCOUTN)).equals("") ? "0" : ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i)
                                            .getString(ConstantString.ZANCOUTN)));
                            hashMap.put(ConstantString.NEW_CONTENT, ResponseUtils.ParaseNull(jsonArray.getJSONObject
                                    (i).getString(ConstantString.NEW_CONTENT)));
                            hashMap.put(ConstantString.NEW_TIME, DateUtils.ParseTimeMillisToTime(ResponseUtils
                                    .ParaseNull(jsonArray.getJSONObject(i).getString(ConstantString.NEW_TIME))));
                            mList.add(hashMap);
                        }
                        mNewsAdapter = new NewsAdapter(context, mList);
                        lv_news.setAdapter(mNewsAdapter);
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

    public void setOnClickListener() {
        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddDiscussActivity.class));
            }
        });
        title_bar.setRightImageLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchAcitivity.class));
            }
        });
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        lv_news.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
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
}
