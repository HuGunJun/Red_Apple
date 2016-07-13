package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.switchview.SegmentView;
import com.iwind.red_apple.Adapter.ViewPagerAdapter;
import com.iwind.red_apple.Fragment.Frag_Hot;
import com.iwind.red_apple.Fragment.Frag_Recommend;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯页面
 * 作者：HuGuoJun
 * 2016/6/12 10:43
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_news)
public class NewActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.vp_news)
    ViewPager vp_news;

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


    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        title_bar.setSegmentViewTab(new String[]{"推荐", "热门"});
        list_frag.add(new Frag_Hot());
        list_frag.add(new Frag_Recommend());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list_frag);
        vp_news.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void InitData() {

    }

    public void setOnClickListener() {
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
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar.setSegmentViewIndexChangedListener(new SegmentView.OnIndexChangedListener() {
            @Override
            public void onChanged(SegmentView view, int index) {
                vp_news.setCurrentItem(index);
            }
        });
        vp_news.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title_bar.setSegmentViewIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
