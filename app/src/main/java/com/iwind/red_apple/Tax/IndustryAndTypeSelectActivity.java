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
import com.iwind.red_apple.Adapter.IndustrySelectAdapter;
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
 * 行业选择
 * 作者：HuGuoJun
 * 2016/7/14 14:46
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_industryselect)
public class IndustryAndTypeSelectActivity extends EaseBaseActivity {

    public static final int REQUEST_INDUSTRY = 1001;//
    public static final int REQUEST_TYPE = 1002;//

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.lv_industry_select)
    XListView lv_industry_select;
    private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private IndustrySelectAdapter mIndustrySelectAdapter;
    private String selectString = "";
    List<String> selectid = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
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
        if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_INDUSTRY) {
            title_bar.setTitle(getResources().getString(R.string.industry_select));
        }
        if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_TYPE) {
            title_bar.setTitle(getResources().getString(R.string.tax_type_select));
        }
        title_bar.setRightText(getResources().getString(R.string.done));
        lv_industry_select.setPullLoadEnable(false);
        lv_industry_select.setPullRefreshEnable(false);
    }

    @Override
    public void InitData() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_LABEL);
        if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_INDUSTRY) {
            params.addBodyParameter(ConstantString.LABLETYPE, "1");
        }
        if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_TYPE) {
            params.addBodyParameter(ConstantString.LABLETYPE, "2");
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.TYPE, jsonArray.getJSONObject(i).getString(ConstantString
                                    .LABELNAME));
                            hashMap.put(ConstantString.IS_SELECT, "0");
                            list.add(hashMap);
                        }
                        mIndustrySelectAdapter = new IndustrySelectAdapter(context, list);
                        lv_industry_select.setAdapter(mIndustrySelectAdapter);
                        mIndustrySelectAdapter.notifyDataSetChanged();
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
                if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_INDUSTRY) {
                    if (selectString.equals("")) {
                        Toast("请选择行业");
                    } else {
                        Intent in = new Intent();
                        in.putExtra(REQUEST_INDUSTRY + "", selectString);
                        setResult(RESULT_OK, in);
                        finish();
                    }
                }
                if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_TYPE) {
                    if (selectString.equals("")) {
                        Toast("请选择税种");
                    } else {
                        Intent in = new Intent();
                        in.putExtra(REQUEST_TYPE + "", selectString);
                        setResult(RESULT_OK, in);
                        finish();
                    }
                }
            }
        });
        lv_industry_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_INDUSTRY) {
                    IndustrySelectAdapter.SetSelect(position - 1);
                    mIndustrySelectAdapter.notifyDataSetChanged();
                    selectString = list.get(position - 1).get(ConstantString.TYPE);
                }
                if (getIntent().getExtras().getInt(ConstantString.TYPE) == REQUEST_TYPE) {
                    if (selectid.size() > 1) {
                        Toast("税种最多选择两项");
                        return;
                    }
                    if (!selectid.contains(position + "")) {
                        selectid.add(position + "");
                        list.get(position - 1).put(ConstantString.IS_SELECT, "1");
                        selectString = selectString + list.get(position - 1).get(ConstantString.TYPE) + ",";
                    } else {
                        list.get(position - 1).put(ConstantString.IS_SELECT, "0");
                        selectid.remove(position + "");
                    }
                    mIndustrySelectAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
