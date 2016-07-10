package com.iwind.red_apple.Tax;

/**
 * User: HuGuoJun
 * Date: 2016-07-10
 * Time: 09:14
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.easemob.easeui.widget.cycleview.CycleViewPager;
import com.easemob.easeui.widget.cycleview.CycleVpEntity;
import com.easemob.easeui.widget.cycleview.ViewFactory;
import com.iwind.red_apple.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 税务首页
 */
@ContentView(R.layout.activity_tax)
public class TaxActivity extends AppCompatActivity implements View.OnClickListener {


    @ViewInject(R.id.lv_discuss)
    LinearLayout lv_discuss;
    @ViewInject(R.id.lv_answer)
    LinearLayout lv_answer;
    @ViewInject(R.id.lv_new_move)
    LinearLayout lv_new_move;
    @ViewInject(R.id.lv_tax_know)
    LinearLayout lv_tax_know;
    @ViewInject(R.id.lv_personal_tax)
    LinearLayout lv_personal_tax;
    @ViewInject(R.id.lv_should_know)
    LinearLayout lv_should_know;
    @ViewInject(R.id.lv_imageviewturn)
    LinearLayout lv_imageviewturn;
    private CycleViewPager cycleViewPager;
    private View vhdf;
    String[] imageUrls = {
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Init();
    }

    private void Init() {
        /**
         * 初始化轮播图
         */
        vhdf = getLayoutInflater().inflate(R.layout.ease_viewpage, null);
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        ViewFactory.initialize(this, vhdf, cycleViewPager, cycData(imageUrls),
                new CycleViewPager.ImageCycleViewListener() {

                    @Override
                    public void onImageClick(CycleVpEntity info, int postion,
                                             View imageView) {

                    }
                });
        cycleViewPager.SetIndicatorResouse(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        lv_imageviewturn.addView(vhdf);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_should_know://办税须知
                startActivity(new Intent(this, ShouldKnowActivity.class));
                break;
            case R.id.lv_personal_tax://个人税务
                startActivity(new Intent(this, PersonalTaxActivity.class));
                break;
            case R.id.lv_tax_know://税务常识
                startActivity(new Intent(this, TaxKnowActivity.class));
                break;
            case R.id.lv_discuss:
                startActivity(new Intent(this, DiscussActivity.class));
                break;
            case R.id.lv_answer:
                break;
            case R.id.lv_new_move:
                startActivity(new Intent(this, NewActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("预留");
    }

    /**
     * 返回广告数据
     *
     * @return
     */
    public static List<CycleVpEntity> cycData(String[] ImageS) {
        List<CycleVpEntity> list = new ArrayList<CycleVpEntity>();
        for (int i = 0; i < ImageS.length; i++) {
            CycleVpEntity cyc = new CycleVpEntity();
            cyc.setIurl(ImageS[i]);
            cyc.setCurl("www.baidu.com");
            cyc.setTitle("奇怪的标题" + i);
            list.add(cyc);
        }
        return list;
    }


}
