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
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.dropdownmenu.DropDownMenu;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.GirdDropDownAdapter;
import com.iwind.red_apple.Adapter.TaxKnowAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Search.SearchAcitivity;

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
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String industrys[] = {"不限", "one", "two", "three", "four"};
    private List<View> popupViews = new ArrayList<>();
    private ListView type_listview, industry_listview;
    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private TaxKnowAdapter mNewsAdapter;

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
        title_bar.setTitle(getResources().getString(R.string.nomal_know));
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);
        type_listview = new ListView(this);
        typeadapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        type_listview.setDividerHeight(0);
        type_listview.setAdapter(typeadapter);

        industry_listview = new ListView(this);
        industryadapter = new GirdDropDownAdapter(this, Arrays.asList(industrys));
        industry_listview.setDividerHeight(0);
        industry_listview.setAdapter(industryadapter);

        popupViews.add(type_listview);
        popupViews.add(industry_listview);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        lv_news.setPullLoadEnable(false);
        lv_news.setPullRefreshEnable(false);
    }

    @Override
    public void InitData() {
        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            mList.add(hashMap);
        }
        mNewsAdapter = new TaxKnowAdapter(getApplicationContext(), mList);
        lv_news.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
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
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });
        industry_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                industryadapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : industrys[position]);
                mDropDownMenu.closeMenu();
            }
        });
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
