package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DateUtils;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.MyCollectionAdapter;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Tax.AnswerDetailActivity;
import com.iwind.red_apple.Tax.DiscussDetailActivity;
import com.iwind.red_apple.Tax.NewsDetailActivity;

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
 * 我的收藏
 * 作者：HuGuoJun
 * 2016/6/13 08:33
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_mycollection)
public class MyCollectionActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.lv_mycollection)
    XListView lv_mycollection;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private MyCollectionAdapter mHomePageAdapter;
    int page = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {
        title_bar.setTitle(getResources().getString(R.string.my_collection));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        lv_mycollection.setPullRefreshEnable(true);
        lv_mycollection.setPullLoadEnable(true);
    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                .GET_MINE_COLLECTION);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.PAGE, page + "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        Log(ConstantUrl.BASE_URL + ConstantUrl.GET_MINE_COLLECTION + "?" + ConstantString.USER_ID +
                "=" + MyApplication.getInstance().getUserid() + "&" + ConstantString.TOKEN + "="
                + MyApplication.getInstance().getToken() + "&" + ConstantString.PAGE + "=" + page
                + "&" +
                ConstantString.ROWS + "=10");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                lv_mycollection.stopRefresh();
                lv_mycollection.stopLoadMore();
                Log(result);
                CloseLoadingDialog();
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

                            hashMap.put(ConstantString.COLLECTION_ID, jsonArray.getJSONObject(i).getString
                                    (ConstantString.COLLECTION_ID));
                            hashMap.put(ConstantString.MOUDEL_ID, jsonArray.getJSONObject(i).getString(ConstantString
                                    .MOUDEL_ID));
                            hashMap.put(ConstantString.LABLES, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.LABLES)));
                            hashMap.put(ConstantString.ZANCOUTN, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.ZANCOUTN)).equals("") ? "0" :
                                    ResponseUtils.ParaseNull(jsonArray
                                            .getJSONObject(i).getString(ConstantString.ZANCOUTN)));
                            hashMap.put(ConstantString.CONTENT, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.CONTENT)));
                            hashMap.put(ConstantString.TITLE, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.TITLE)));
                            hashMap.put(ConstantString.MOUDEL_TYPE, jsonArray.getJSONObject(i).getString
                                    (ConstantString.MOUDEL_TYPE));
                            mList.add(hashMap);
                        }
                        mHomePageAdapter = new MyCollectionAdapter(context, mList);
                        lv_mycollection.setAdapter(mHomePageAdapter);
                        mHomePageAdapter.notifyDataSetChanged();
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

    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_mycollection.setXListViewListener(new XListView.IXListViewListener() {
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
        lv_mycollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 1.办事须知2.问题库
                 3.论坛
                 4.论坛某留言
                 5.客户端
                 6.新闻
                 */
                if (mList.get(position - 1).get(ConstantString.MOUDEL_TYPE).equals("3")) {
                    startActivity(new Intent(context, DiscussDetailActivity.class).putExtra(ConstantString.FORUM_ID,
                            mList
                                    .get(position - 1).get(ConstantString.MOUDEL_ID)));
                }
                if (mList.get(position - 1).get(ConstantString.MOUDEL_TYPE).equals("4")) {
                    startActivity(new Intent(context, AnswerDetailActivity.class).putExtra(ConstantString
                            .FORUMMESSAGEID, mList.get(position - 1).get(ConstantString.MOUDEL_ID)));
                }
                if (mList.get(position - 1).get(ConstantString.MOUDEL_TYPE).equals("6")) {
                    startActivity(new Intent(context, NewsDetailActivity.class).putExtra(ConstantString.NEW_ID, mList
                            .get
                                    (position
                                            - 1).get(ConstantString.MOUDEL_ID)));
                }
            }
        });
    }
}
