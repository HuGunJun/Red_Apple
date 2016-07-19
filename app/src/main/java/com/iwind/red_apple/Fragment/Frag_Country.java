package com.iwind.red_apple.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.NewsAdapter;
import com.iwind.red_apple.Adapter.VideoGuideAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Tax.NewsDetailActivity;
import com.iwind.red_apple.Video.VideoDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 国税客户端
 * 作者：HuGuoJun
 * 2016/6/12 11:51
 * 邮箱：www.guojunkuaile@qq.com
 */
public class Frag_Country extends BaseFragment {


    View view;
    @ViewInject(R.id.lv_country)
    XListView lv_country;
    int page=1;

    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private VideoGuideAdapter mHomePageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_country, null);
        x.view().inject(this, view);
        InitView();
        InitData();
        setOnClickListener();
        return view;
    }

    public void InitView() {
        lv_country.setPullLoadEnable(true);
        lv_country.setPullRefreshEnable(true);

    }

    public void InitData() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GETCLIENT);
        params.addBodyParameter(ConstantString.SEARCH_CONTENT, "");
        params.addBodyParameter(ConstantString.ROWS, ConstantString.ROWCOUNT);
        params.addBodyParameter(ConstantString.PAGE, "" + page);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                lv_country.stopRefresh();
                lv_country.stopLoadMore();
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(getActivity(), ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray(ConstantString.ARRAY);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put(ConstantString.CLIENT_ID, jsonArray.getJSONObject(i).getString(ConstantString
                                    .CLIENT_ID));
                            hashMap.put(ConstantString.CLIENT_TITLE, ResponseUtils.ParaseNull(jsonArray.getJSONObject
                                    (i).getString
                                    (ConstantString.CLIENT_TITLE)));
                            hashMap.put(ConstantString.CLIENT_CONTENT, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString
                                            (ConstantString.CLIENT_CONTENT)));
                            hashMap.put(ConstantString.MESSAGE_COUNT, ResponseUtils.ParaseNull(jsonArray
                                    .getJSONObject(i).getString(ConstantString.MESSAGE_COUNT)).equals("") ? "0" :
                                    ResponseUtils.ParaseNull(jsonArray
                                            .getJSONObject(i).getString(ConstantString.MESSAGE_COUNT)));
                            mList.add(hashMap);
                        }
                        mHomePageAdapter = new VideoGuideAdapter(getActivity(), mList);
                        lv_country.setAdapter(mHomePageAdapter);
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
                CloseLoadingDialog();
            }
        });


    }

    public void setOnClickListener() {
        lv_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailActivity.class));
            }
        });
        lv_country.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                page = 1;
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
