package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.actionsheetdialog.ActionSheetDialog;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 个人信息 修改头像/昵称
 * 作者：HuGuoJun
 * 2016/6/12 16:19
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_myinfo)
public class MyInfoActivity extends EaseBaseActivity {

    private static final int REQUEST_CHAGE_NICK = 1001;//修改昵称请求
    @ViewInject(R.id.tv_nick)
    TextView tv_nick;
    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.act_myinfo);
        x.view().inject(this);
        InitView();
        InitData();
        setOnClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_nick:
                startActivityForResult(new Intent(context, EditNickActivity.class), REQUEST_CHAGE_NICK);
                break;
            case R.id.rl_change_avatar:
                new ActionSheetDialog(context).builder().setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem(getResources().getString(R.string.photos), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).addSheetItem(getResources().getString(R.string.take_photos), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                }).show();
                break;
        }
    }

    @Override
    public void InitView() {
        title_bar.setTitle(getResources().getString(R.string.personal_info));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHAGE_NICK:
                tv_nick.setText(data.getExtras().getString(ConstantString.NICK));
                break;
        }
    }
}
