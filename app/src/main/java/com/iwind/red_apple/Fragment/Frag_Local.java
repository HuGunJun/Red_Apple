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

import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.NewsAdapter;
import com.iwind.red_apple.Adapter.VideoGuideAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Tax.NewsDetailActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：HuGuoJun
 * 2016/6/12 11:46
 * 邮箱：www.guojunkuaile@qq.com
 */
public class Frag_Local extends Fragment {

    View view;
    @ViewInject(R.id.lv_local)
    XListView lv_local;


    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private VideoGuideAdapter mHomePageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_local, null);
        x.view().inject(this, view);
        InitView();
        InitData();
        setOnClickListener();
        return view;
    }

    public void InitView() {
        lv_local.setPullLoadEnable(true);
        lv_local.setPullRefreshEnable(true);

    }

    public void InitData() {
        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(ConstantString.CONTENT, "本报讯（通讯员 吴川宁 记者 " +
                    "王茸）5日下午，南京交警高速六大队民警执勤时，查获9起未随车携带驾驶证驾驶机动车的交通违法行为，并对相关驾驶员予以处罚。溧阳的赵先生利用星期天驾车到南京游玩，行驶到宁杭高速南京收费站时被拦下例行检查。当交警要其出示驾驶证时，赵先生掏不出来，他解释道，自己有驾驶证，放在了平时上下班开的小车上。“今天天气好，我开了另一辆越野车带家人到南京玩，忘记把驾驶证取出来了。”他解释道，不过，他的解释并未获得交警的通融，他因没带驾驶证被罚款50元记1分。没一会儿，市民李先生也被交警查出没带驾驶证，原来他一直把证放在包里，当天出门时没带包。“我又不是无证驾驶，交警在系统里都能查");
            hashMap.put(ConstantString.READ_COUNT, "100000");
            hashMap.put(ConstantString.TIME, "今天10：00");
            hashMap.put(ConstantString.IV_URL, "http://");
            mList.add(hashMap);
        }
        mHomePageAdapter = new VideoGuideAdapter(getActivity(), mList);
        lv_local.setAdapter(mHomePageAdapter);
        mHomePageAdapter.notifyDataSetChanged();

    }

    public void setOnClickListener() {
        lv_local.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "地税", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
