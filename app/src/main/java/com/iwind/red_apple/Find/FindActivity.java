package com.iwind.red_apple.Find;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.iwind.red_apple.R;
import com.iwind.red_apple.Tax.NewActivity;
import com.iwind.red_apple.Tax.PersonalTaxActivity;
import com.iwind.red_apple.Tax.ShouldKnowActivity;
import com.iwind.red_apple.Tax.TaxKnowActivity;
import com.iwind.red_apple.Video.VideoGuideActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * User: HuGuoJun
 * Date: 2016-07-10
 * Time: 09:15
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */
@ContentView(R.layout.activity_act__find)
public class FindActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_news://最新资讯
                startActivity(new Intent(this, NewActivity.class));
                break;
            case R.id.lv_guide_video://视频指导
                startActivity(new Intent(this, VideoGuideActivity.class));
                break;
            case R.id.lv_do_tax_know://办税须知
                startActivity(new Intent(this, ShouldKnowActivity.class));
                break;
            case R.id.lv_nomal_know://税务常识
                startActivity(new Intent(this, TaxKnowActivity.class));
                break;
            case R.id.lv_personal_industry://个人业务
                startActivity(new Intent(this, PersonalTaxActivity.class));
                break;
        }
    }

    long exitTime = 0;

    // 监听两次返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string
                        .again_login_out), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

}
