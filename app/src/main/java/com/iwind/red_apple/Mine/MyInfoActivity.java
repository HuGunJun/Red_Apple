package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.actionsheetdialog.ActionSheetDialog;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
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
    @ViewInject(R.id.tv_sex)
    TextView tv_sex;
    @ViewInject(R.id.tv_birthday)
    TextView tv_birth;
    @ViewInject(R.id.tv_area)
    TextView tv_area;

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
                new ActionSheetDialog(context).builder().setCancelable(false).setCanceledOnTouchOutside(false)
                        .addSheetItem(getResources().getString(R.string.photos), ActionSheetDialog.SheetItemColor
                                .Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        }).addSheetItem(getResources().getString(R.string.take_photos), ActionSheetDialog.SheetItemColor
                        .Blue, new ActionSheetDialog.OnSheetItemClickListener() {
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
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_PERSONAL_INFO);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject obj = jsonObject.getJSONObject(ConstantString.OBJ);
                        tv_nick.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString.NICK_NAME)));
                        tv_sex.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString.SEX)));
                        tv_birth.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString.BIRTH)));
                        tv_area.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString.CITY)) + "-" +
                                ResponseUtils.ParaseNull(obj.getString(ConstantString.AREA)));

                        /**
                         * keep go on
                         */
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CloseLoadingDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
