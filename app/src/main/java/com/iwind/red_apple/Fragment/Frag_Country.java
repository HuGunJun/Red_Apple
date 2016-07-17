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
import com.iwind.red_apple.Video.VideoDetailActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：HuGuoJun
 * 2016/6/12 11:51
 * 邮箱：www.guojunkuaile@qq.com
 */
public class Frag_Country extends Fragment {


    View view;
    @ViewInject(R.id.lv_country)
    XListView lv_country;


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
        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            mList.add(hashMap);
        }
        mHomePageAdapter = new VideoGuideAdapter(getActivity(), mList);
        lv_country.setAdapter(mHomePageAdapter);
        mHomePageAdapter.notifyDataSetChanged();

    }

    public void setOnClickListener() {
        lv_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailActivity.class));
            }
        });
    }
}
