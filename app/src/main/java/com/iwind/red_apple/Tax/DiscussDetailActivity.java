package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.DiscussDetailAdapter;
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
import java.util.HashMap;
import java.util.List;

/**
 * 讨论详情页面
 * 作者：HuGuoJun
 * 2016/6/8 9:35
 * 邮箱：www.guojunkuaile@qq.com
 */

@ContentView(R.layout.activity_act__discuss__detail)
public class DiscussDetailActivity extends EaseBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.title_bar)
    EaseTitleBar mEaseTitleBar;
    @ViewInject(R.id.lv_question_describe)
    LinearLayout lv_question_describe;
    @ViewInject(R.id.lv_discuss_detail)
    XListView lv_discuss_detail;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;
    View discuss_detail_header;
    TextView tv_label;
    TextView tv_messagecount;
    TextView tv_seeknumber;
    TextView tv_sharenumber;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private DiscussDetailAdapter mDiscussDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        UpdateDiscuss(1);
        setOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowLoadingDialog();
        InitData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_add_answer:
                startActivity(new Intent(context, AddAnswerActivity.class).putExtra(ConstantString.FORUM_ID,
                        getIntent().getExtras().getString
                                (ConstantString.FORUM_ID)));
                break;
            case R.id.lv_collection_talk:
                break;
        }
    }

    public void InitView() {
        mEaseTitleBar.setLeftImageResource(R.drawable.ease_mm_title_back);
        mEaseTitleBar.setRightImageRightResource(R.drawable.iv_share);
        lv_discuss_detail.setPullRefreshEnable(false);
        lv_discuss_detail.setPullLoadEnable(false);
        discuss_detail_header = LayoutInflater.from(context).inflate(R.layout
                .view_discuss_detail_header, lv_discuss_detail, false);
        tv_label = (TextView) discuss_detail_header.findViewById(R.id.tv_label);
        tv_messagecount = (TextView) discuss_detail_header.findViewById(R.id
                .tv_messagecount);
        tv_seeknumber = (TextView) discuss_detail_header.findViewById(R.id.tv_seeknumber);
        tv_sharenumber = (TextView) discuss_detail_header.findViewById(R.id
                .tv_sharenumber);

//        tv_label.setText(getIntent().getExtras().getString(ConstantString.TAX_TYPE));
//        tv_messagecount.setText(getIntent().getExtras().getString(ConstantString.MESSAGE_COUNT));
//        tv_question_describe.setText(getIntent().getExtras().getString(ConstantString
//                .FORUM_TITLE));
//        tv_seeknumber.setText(getIntent().getExtras().getString(ConstantString.SEEKNUMBER));
//        tv_sharenumber.setText(getIntent().getExtras().getString(ConstantString.SHARENUMBER));
    }

    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                .GET_DISCUSS_DETAIL);
        params.addBodyParameter(ConstantString.FORUM_ID, getIntent().getExtras().getString
                (ConstantString.FORUM_ID));
        Log(ConstantUrl.BASE_URL + ConstantUrl.GET_DISCUSS_DETAIL + "?" + ConstantString.FORUM_ID
                + "=" + getIntent().getExtras().getString
                (ConstantString.FORUM_ID));
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
                        tv_label.setText(ResponseUtils.ParaseNull(OBJ.getString(ConstantString
                                .TAX_TYPE)));
                        tv_question_describe.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.FORUM_TITLE)));
                        tv_messagecount.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.MESSAGE_COUNT)).equals("") ? "0" : ResponseUtils
                                .ParaseNull(OBJ.getString
                                        (ConstantString.MESSAGE_COUNT)));
                        tv_seeknumber.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.SEEKNUMBER)).equals("") ? "0" : ResponseUtils
                                .ParaseNull(OBJ.getString
                                        (ConstantString.SEEKNUMBER)));
                        tv_sharenumber.setText(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.SHARENUMBER)).equals("") ? "0" : ResponseUtils
                                .ParaseNull(OBJ.getString
                                        (ConstantString.SHARENUMBER)));
                        mEaseTitleBar.setTitle(ResponseUtils.ParaseNull(OBJ.getString
                                (ConstantString.FORUM_TITLE)));
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        mList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.USERNAME, ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i).getString(ConstantString
                                            .USERNAME)));
                            hashMap.put(ConstantString.USER_PIC, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString.USER_PIC)));
                            hashMap.put(ConstantString.FORUMMESSAGEID, jsonArray.getJSONObject(i)
                                    .getString(ConstantString.FORUMMESSAGEID));
//                            hashMap.put(ConstantString.NICK_NAME, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
//                                    .getString(ConstantString.NICK_NAME)));
                            hashMap.put(ConstantString.FORUMMESSAGE, ResponseUtils.ParaseNull(jsonArray.getJSONObject
                                    (i).getString(ConstantString.FORUMMESSAGE)));
                            hashMap.put(ConstantString.ZANCOUTN, ResponseUtils.ParaseNull(jsonArray.getJSONObject(i)
                                    .getString(ConstantString
                                            .ZANCOUTN)).equals("") ? "0" : ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i)
                                    .getString(ConstantString
                                            .ZANCOUTN)));
                            mList.add(hashMap);
                        }
                        mDiscussDetailAdapter = new DiscussDetailAdapter(context, mList);
                        lv_discuss_detail.removeHeaderView(discuss_detail_header);
                        lv_discuss_detail.addHeaderView(discuss_detail_header);
                        lv_discuss_detail.setAdapter(mDiscussDetailAdapter);
                        mDiscussDetailAdapter.notifyDataSetChanged();

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
     * 修改讨论对象  1  为增加查看次数  2为增加分享次数
     *
     * @param which
     */
    private void UpdateDiscuss(int which) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_DISCUSS);
        params.addBodyParameter(ConstantString.FORUM_ID, getIntent().getExtras().getString
                (ConstantString.FORUM_ID));
        params.addBodyParameter(ConstantString.TYPE, which + "");
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

    public void setOnClickListener() {
        mEaseTitleBar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        mEaseTitleBar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mEaseTitleBar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lv_question_describe.getVisibility() == View.VISIBLE) {
                    lv_question_describe.setVisibility(View.GONE);
                } else {
                    lv_question_describe.setVisibility(View.VISIBLE);
                }
            }
        });
        lv_discuss_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    Intent intent = new Intent(context, AnswerDetailActivity.class);
                    intent.putExtra(ConstantString.FORUMMESSAGEID, mList.get(position - 2).get
                            (ConstantString.FORUMMESSAGEID));
                    intent.putExtra(ConstantString.FORUM_TITLE, tv_question_describe.getText()
                            .toString());
                    startActivity(intent);
                }
            }
        });
        mEaseTitleBar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoadingDialog();
                UpdateDiscuss(2);
            }
        });
    }
}