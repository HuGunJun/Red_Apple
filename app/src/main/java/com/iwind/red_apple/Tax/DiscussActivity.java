package com.iwind.red_apple.Tax;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.xlistview.XListView;
import com.iwind.red_apple.Adapter.DiscussAdapter;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: HuGuoJun
 * Date: 2016-07-05
 * Time: 22:36
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act_discuss)
public class DiscussActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;

    @ViewInject(R.id.listview)
    XListView lv_discuss;

    private List<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private DiscussAdapter discussAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void InitView() {

        title_bar.setTitle(getResources().getString(R.string.discuss));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageRightResource(R.mipmap.ic_launcher);
        title_bar.setRightImageLeftResource(R.drawable.ic_launcher);
        lv_discuss.setPullLoadEnable(true);
        lv_discuss.setPullRefreshEnable(true);
        for (int i = 0; i < 20; i++) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(ConstantString.NAME, "" + i * 10);
            hashMap.put(ConstantString.CONTENT, "本报讯（通讯员 吴川宁 记者 王茸）5日下午，南京交警高速六大队民警执勤时，查获9起未随车携带驾驶证驾驶机动车的交通违法行为，并对相关驾驶员予以处罚。溧阳的赵先生利用星期天驾车到南京游玩，行驶到宁杭高速南京收费站时被拦下例行检查。当交警要其出示驾驶证时，赵先生掏不出来，他解释道，自己有驾驶证，放在了平时上下班开的小车上。“今天天气好，我开了另一辆越野车带家人到南京玩，忘记把驾驶证取出来了。”他解释道，不过，他的解释并未获得交警的通融，他因没带驾驶证被罚款50元记1分。没一会儿，市民李先生也被交警查出没带驾驶证，原来他一直把证放在包里，当天出门时没带包。“我又不是无证驾驶，交警在系统里都能查");
            hashMap.put(ConstantString.READ_COUNT, "100000");
            hashMap.put(ConstantString.TYPE, "纳服2.3");
            hashMap.put(ConstantString.IV_URL, "http://");
            mList.add(hashMap);
        }
        discussAdapter = new DiscussAdapter(this, mList);
        lv_discuss.setAdapter(discussAdapter);
        discussAdapter.notifyDataSetChanged();
    }

    @Override
    public void InitData() {

    }

    /**
     * 设置点击事件
     */
    @Override
    public void setOnClickListener() {
        lv_discuss.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        lv_discuss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DiscussDetailActivity.class);
                intent.putExtra(ConstantString.DISSCUSS_TITLE, "这是什么特么的标题擦擦擦啊擦擦啊擦");
                startActivity(intent);
            }
        });
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "新增", Toast.LENGTH_SHORT).show();
            }
        });
        title_bar.setRightImageLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "查找", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
