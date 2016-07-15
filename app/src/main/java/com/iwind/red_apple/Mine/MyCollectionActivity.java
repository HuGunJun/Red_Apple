package com.iwind.red_apple.Mine;

import android.os.Bundle;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.MyCollectionAdapter;
import com.iwind.red_apple.App.MyApplication;
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
    int page = 0;

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


        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(ConstantString.USER_NAME, "" + i * 10);
            hashMap.put(ConstantString.CONTENT, "本报讯（通讯员 吴川宁 记者 " +
                    "王茸）5日下午，南京交警高速六大队民警执勤时，查获9起未随车携带驾驶证驾驶机动车的交通违法行为，并对相关驾驶员予以处罚。溧阳的赵先生利用星期天驾车到南京游玩，行驶到宁杭高速南京收费站时被拦下例行检查。当交警要其出示驾驶证时，赵先生掏不出来，他解释道，自己有驾驶证，放在了平时上下班开的小车上。“今天天气好，我开了另一辆越野车带家人到南京玩，忘记把驾驶证取出来了。”他解释道，不过，他的解释并未获得交警的通融，他因没带驾驶证被罚款50元记1分。没一会儿，市民李先生也被交警查出没带驾驶证，原来他一直把证放在包里，当天出门时没带包。“我又不是无证驾驶，交警在系统里都能查");
            hashMap.put(ConstantString.READ_COUNT, "100000");
            hashMap.put(ConstantString.TYPE, "纳服2.3");
            hashMap.put(ConstantString.IV_URL, "http://");
            mList.add(hashMap);
        }
        mHomePageAdapter = new MyCollectionAdapter(this, mList);
        lv_mycollection.setAdapter(mHomePageAdapter);
        mHomePageAdapter.notifyDataSetChanged();
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
                page = 0;
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
