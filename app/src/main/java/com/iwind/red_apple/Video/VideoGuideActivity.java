package com.iwind.red_apple.Video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.Adapter.ViewPagerAdapter;
import com.iwind.red_apple.Fragment.Frag_Country;
import com.iwind.red_apple.Fragment.Frag_Local;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;
import com.iwind.red_apple.Tax.AddDiscussActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：HuGuoJun
 * 2016/7/13 13:16
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_videoguide)
public class VideoGuideActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.vp_video)
    ViewPager vp_video;
    private List<Fragment> list_frag = new ArrayList<Fragment>();
    private ViewPagerAdapter mAdapter;

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
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        list_frag.add(new Frag_Country());
        list_frag.add(new Frag_Local());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_frag);
        vp_video.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
