package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
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
 * 编辑page
 * 作者：HuGuoJun
 * 2016/6/12 17:09
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_edit_nick)
public class EditUserInfoActivity extends EaseBaseActivity {


    private static final int REQUEST_CHAGE_NICK = 1001;//修改昵称请求
    private static final int REQUEST_CHAGE_SEX = 1002;//修改性别
    private static final int REQUEST_CHAGE_BIRTH = 1003;//修改生日
    private static final int REQUEST_CHAGE_AREA = 1004;//修改地区

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.et_update_info)
    EditText et_update_info;

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
        if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_NICK)) {
            title_bar.setTitle(getResources().getString(R.string.update_nick));
        } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_SEX)) {
            title_bar.setTitle(getResources().getString(R.string.update_sex));
        } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_BIRTH)) {
            title_bar.setTitle(getResources().getString(R.string.update_birth));
        } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_AREA)) {
            title_bar.setTitle(getResources().getString(R.string.update_area));
        }
        title_bar.setRightText(getResources().getString(R.string.done));
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
        title_bar.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_NICK)) {
                    if (!TextUtils.isEmpty(et_update_info.getText().toString())) {
                        updateUserInfo(REQUEST_CHAGE_NICK, 3);
                    } else {
                        Toast("请输入修改信息");
                    }
                } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_SEX)) {
                    updateUserInfo(REQUEST_CHAGE_SEX, 3);
                } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_BIRTH)) {
                    updateUserInfo(REQUEST_CHAGE_BIRTH, 3);
                } else if (getIntent().getExtras().getInt(ConstantString.CHAGE_INFO_TYPE) == (REQUEST_CHAGE_AREA)) {
                    updateUserInfo(REQUEST_CHAGE_AREA, 3);
                }
            }
        });
    }

    public void updateUserInfo(int whichinfo, int cityorarea) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.UPDATE_USERINFO);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        switch (whichinfo) {
            case REQUEST_CHAGE_NICK:
                params.addBodyParameter(ConstantString.NICK, et_update_info.getText().toString());
                break;
            case REQUEST_CHAGE_SEX:
                params.addBodyParameter(ConstantString.SEX, et_update_info.getText().toString());
                break;
            case REQUEST_CHAGE_BIRTH:
                params.addBodyParameter(ConstantString.BIRTH, et_update_info.getText().toString());
                break;
            case REQUEST_CHAGE_AREA:
                if (cityorarea == 0) {
                    params.addBodyParameter(ConstantString.CITY, et_update_info.getText().toString());
                } else if (cityorarea == 1) {
                    params.addBodyParameter(ConstantString.AREA, et_update_info.getText().toString());
                }
                break;
        }
        ShowLoadingDialog();
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                CloseLoadingDialog();
            }
        });
    }

}
