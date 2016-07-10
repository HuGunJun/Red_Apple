package com.iwind.red_apple;

import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.easemob.easeui.widget.EaseTitleBar;
import com.iwind.red_apple.Find.FindActivity;
import com.iwind.red_apple.Mine.MintActivity;
import com.iwind.red_apple.Tax.TaxActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * User: HuGuoJun
 * Date: 2016-07-10
 * Time: 09:07
 * Email: www.guojunkuaile@qq.com
 * QQ: 1397883456
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends TabActivity {

    private TabHost tabHost;
    private RadioGroup radioderGroup;
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    public static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.titile_color);//通知栏所需颜色
        }
        x.view().inject(this);
        InitView();
        InitData();
        SetOnClickListener();
        instance = this;
    }


    /**
     * 设置点击事件
     */
    private void SetOnClickListener() {
//        title_bar.setRightImageClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Act_Add_Discuss.class));
//            }
//        });
//        title_bar.setRightTextClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Act_Message.class));
//            }
//        });
    }

    /**
     * 设置sdk版本属性 在主界面保持标题栏一致
     *
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void InitView() {
        tabHost = getTabHost();
        radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
        title_bar.setTitle(getResources().getString(R.string.bottom_menu_tax));
        title_bar.setBackgroundColor(getResources().getColor(R.color.titile_color));
        title_bar.setRightImageResource(R.mipmap.ic_launcher);
        title_bar.setRightImageAndTextVisiable(true, false);
    }

    public void InitData() {
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(new Intent(this, TaxActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(new Intent(this, FindActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(new Intent(this, MintActivity.class)));
        radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
        radioderGroup.check(R.id.mainTabs_radio_tax);//默认第一个按钮
        radioderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mainTabs_radio_tax:
                        tabHost.setCurrentTabByTag("1");
                        title_bar.setTitle(getResources().getString(R.string.bottom_menu_tax));
                        title_bar.setRightLayoutVisibility(View.INVISIBLE);
                        break;
                    case R.id.mainTabs_radio_find:
                        tabHost.setCurrentTabByTag("2");
                        title_bar.setTitle(getResources().getString(R.string.bottom_menu_find));
                        title_bar.setRightLayoutVisibility(View.INVISIBLE);
                        break;
                    case R.id.mainTabs_radio_mine:
                        tabHost.setCurrentTabByTag("3");
                        title_bar.setTitle(getResources().getString(R.string.bottom_menu_mine));
                        title_bar.setRightLayoutVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}
