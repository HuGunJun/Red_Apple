package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.dropdownmenu.DropDownMenu;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.GirdDropDownAdapter;
import com.iwind.red_apple.Adapter.IndustrySelectAdapter;
import com.iwind.red_apple.Adapter.TaxKnowAdapter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 税务常识
 * 作者：HuGuoJun
 * 2016/6/8 17:48
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act__know)
public class TaxKnowActivity extends EaseBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.lv_news)
    XListView lv_news;
    @ViewInject(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private String headers[] = {"行业", "税种"};
    private GirdDropDownAdapter typeadapter, industryadapter;
    private List<HashMap<String, String>> list_industry = new ArrayList<HashMap<String, String>>();
    private List<HashMap<String, String>> list_type = new ArrayList<HashMap<String, String>>();
    private List<View> popupViews = new ArrayList<>();
    private ListView type_listview, industry_listview;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private TaxKnowAdapter mNewsAdapter;
    int page = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        InitView();
        ShowLoadingDialog();
        InitLabelAndType(1);
        setOnClickListener();
    }

    /**
     * 初始化行业和税种标签
     */
    private void InitLabelAndType(final int i) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_LABEL);
        params.addBodyParameter(ConstantString.LABLETYPE, i + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        if (i == 1) {
                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put(ConstantString
                                    .TAX_TYPE, "不限");
                            list_industry.add(hashMap1);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put(ConstantString.TAX_TYPE, jsonArray.getJSONObject(i)
                                        .getString(ConstantString.LABELNAME));
                                list_industry.add(hashMap);
                            }
                            InitLabelAndType(2);
                            industryadapter.notifyDataSetChanged();
                        }
                        if (i == 2) {
                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put(ConstantString
                                    .TAX_TYPE, "不限");
                            list_type.add(hashMap1);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put(ConstantString.TAX_TYPE, jsonArray.getJSONObject(i)
                                        .getString(ConstantString.LABELNAME));
                                list_type.add(hashMap);
                            }
                            typeadapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.nomal_know));
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        type_listview = new ListView(this);
        typeadapter = new GirdDropDownAdapter(this, list_industry);
        type_listview.setDividerHeight(0);
        type_listview.setAdapter(typeadapter);

        industry_listview = new ListView(this);
        industryadapter = new GirdDropDownAdapter(this, list_type);
        industry_listview.setDividerHeight(0);
        industry_listview.setAdapter(industryadapter);

        popupViews.add(type_listview);
        popupViews.add(industry_listview);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        lv_news.setPullLoadEnable(false);
        lv_news.setPullRefreshEnable(false);
    }

    @Override
    public void InitData() {

        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.SEARCH_PROBLEM);
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        params.addBodyParameter(ConstantString.PAGE, page + "");
        params.addBodyParameter(ConstantString.HLABEL, "");
        params.addBodyParameter(ConstantString.TAX_TYPE, "");
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.PROBLEM_ID, jsonArray.getJSONObject(i)
                                    .getString(ConstantString.PROBLEM_ID));
                            hashMap.put(ConstantString.PROBLEM_TITLE, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.PROBLEM_TITLE)));
                            hashMap.put(ConstantString.HLABEL, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.HLABEL)));
                            hashMap.put(ConstantString.TAX_TYPE, ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i).getString(ConstantString
                                            .TAX_TYPE)));
                            hashMap.put(ConstantString.PROBLEM_CONTENT, ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i).getString(ConstantString
                                            .PROBLEM_CONTENT)));
                            hashMap.put(ConstantString.ZANCOUTN, ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i).getString(ConstantString
                                            .ZANCOUTN)).equals("") ? "0" : ResponseUtils.ParaseNull
                                    (jsonArray.getJSONObject(i).getString(ConstantString
                                            .ZANCOUTN)));
                            mList.add(hashMap);
                        }
                        mNewsAdapter = new TaxKnowAdapter(getApplicationContext(), mList);
                        lv_news.setAdapter(mNewsAdapter);
                        mNewsAdapter.notifyDataSetChanged();
                        CloseLoadingDialog();
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
        type_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeadapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : list_industry.get(position)
                        .get(ConstantString.TAX_TYPE));
                mDropDownMenu.closeMenu();
            }
        });
        industry_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industryadapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : list_type.get(position).get
                        (ConstantString.TAX_TYPE));
                mDropDownMenu.closeMenu();
            }
        });
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(ConstantString.PROBLEM_ID, mList.get(position - 1).get
                        (ConstantString.PROBLEM_ID));
                startActivity(intent);
            }
        });
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
    }


    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
