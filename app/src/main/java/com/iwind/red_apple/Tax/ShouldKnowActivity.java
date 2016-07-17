package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.ShouldKnowAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 税务须知
 * 作者：HuGuoJun
 * 2016/7/6 17:33
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_should_know)
public class ShouldKnowActivity extends EaseBaseActivity implements View.OnClickListener {

    @ViewInject(R.id.lv_should_know)
    XListView lv_should_know;
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;

    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private ShouldKnowAdapter mNewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        InitData();
        setOnClickListener();
    }


    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_should_know.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(ConstantString.FORUM_TITLE, "这是什么特么的标题擦擦擦啊擦擦啊擦");
                startActivity(intent);
            }
        });
        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddDiscussActivity.class));
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
    public void onClick(View v) {

    }

    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setTitle(getResources().getString(R.string.should_know));
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        lv_should_know.setPullLoadEnable(false);
        lv_should_know.setPullRefreshEnable(false);
    }

    @Override
    public void InitData() {


        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            mList.add(hashMap);
        }
        mNewsAdapter = new ShouldKnowAdapter(getApplicationContext(), mList);
        lv_should_know.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }
}
