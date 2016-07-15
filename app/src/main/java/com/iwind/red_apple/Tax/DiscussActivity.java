package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DateUtils;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.DiscussAdapter;
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
 * User: HuGuoJun
 * Date: 2016-07-05
 * Time: 22:36
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act_discuss)
public class DiscussActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.listview)
    XListView lv_discuss;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private DiscussAdapter discussAdapter;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ShowLoadingDialog();
        InitView();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {
        title_bar.setTitle(getResources().getString(R.string.discuss));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        lv_discuss.setPullLoadEnable(true);
        lv_discuss.setPullRefreshEnable(true);
    }

    @Override
    public void InitData() {

        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_DISCUSS);
        params.addBodyParameter(ConstantString.PAGE, page + "");
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.USER_NAME, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.USER_NAME)));
                            hashMap.put(ConstantString.FORUM_TIME, DateUtils.ParseTimeMillisToTime(ResponseUtils
                                    .ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.FORUM_TIME))));
                            hashMap.put(ConstantString.FORUM_CONTENT, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.FORUM_CONTENT)));
                            hashMap.put(ConstantString.MESSAGE_COUNT, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i)
                                    .getString(ConstantString.MESSAGE_COUNT)));
                            hashMap.put(ConstantString.TAX_TYPE, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.TAX_TYPE)));
                            mList.add(hashMap);
                        }
                        discussAdapter = new DiscussAdapter(context, mList);
                        lv_discuss.setAdapter(discussAdapter);
                        discussAdapter.notifyDataSetChanged();
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
     * 设置点击事件
     */
    @Override
    public void setOnClickListener() {
        lv_discuss.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                InitData();
            }

            @Override
            public void onLoadMore() {
                page++;
                InitData();

            }
        });
        lv_discuss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DiscussDetailActivity.class);
                intent.putExtra(ConstantString.DISSCUSS_TITLE, "这是什么特么的标题擦擦擦啊擦擦啊擦");
                startActivity(intent);
            }
        });
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }


}
