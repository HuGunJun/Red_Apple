package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.MessageAdapter;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：HuGuoJun
 * 2016/7/14 12:00
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_message)
public class MessageActivity extends EaseBaseActivity {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.lv_message)
    XListView lv_message;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private MessageAdapter mMessageAdapter;
    int page = 0;

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

    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.message));
        lv_message.setPullLoadEnable(true);
        lv_message.setPullRefreshEnable(true);
    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_MESSAGE);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.PAGE, page + "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        Log(ConstantUrl.BASE_URL + ConstantUrl.GET_MESSAGE + "?" + ConstantString.USER_ID + "=" + MyApplication
                .getInstance().getUserid() + "&" + ConstantString.TOKEN + "=" + MyApplication.getInstance().getToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                lv_message.stopLoadMore();
                lv_message.stopRefresh();
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        if (jsonArray.length() < 0) {
                            Toast(getResources().getString(R.string.no_more_messages));
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.TIDING_ID, jsonArray.getJSONObject(i).getString(ConstantString
                                    .TIDING_ID));
                            hashMap.put(ConstantString.TIDING_TITLE, ResponseUtils.ParaseNull(jsonArray.getJSONObject
                                    (i).getString
                                    (ConstantString.TIDING_TITLE)));
                            hashMap.put(ConstantString.TIDING_CONTENT, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString
                                            (ConstantString.TIDING_CONTENT)));
                            hashMap.put(ConstantString.TIDING_TIME, ResponseUtils
                                    .ParaseNull(jsonArray.getJSONObject
                                            (i).getString
                                            (ConstantString.TIDING_TIME)));
                            mList.add(hashMap);
                        }

                        Collections.sort(mList, new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                                if (Long.parseLong(lhs.get(ConstantString.TIDING_TIME)) > Long.parseLong(rhs.get
                                        (ConstantString.TIDING_TIME))) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

                        mMessageAdapter = new MessageAdapter(context, mList);
                        lv_message.setAdapter(mMessageAdapter);
                        mMessageAdapter.notifyDataSetChanged();

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
        lv_message.setXListViewListener(new XListView.IXListViewListener() {
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
}
