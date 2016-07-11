package com.iwind.red_apple;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.ResponseUtils;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者：HuGuoJun
 * 2016/6/2 10:35
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act__splash)
public class SplashActivity extends EaseBaseActivity {

    @ViewInject(R.id.iv_splash)
    ImageView iv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        InitView();
    }

    @Override
    public void InitView() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_splash, "scaleX", 1f, 1.13F);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_splash, "scaleY", 1f, 1.13F);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!MyApplication.getInstance().getUserName().equals("")) {
                            Login();
                        } else {
                            startActivity(new Intent(context, LoginActivity.class));
                            finish();
                        }
                    }
                }, 3000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 用户登录
     */
    private void Login() {

        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.LOGIN);
        params.addBodyParameter(ConstantString.USER_NAME, MyApplication.getInstance().getUserName());
        params.addBodyParameter(ConstantString.PASSWORD, MyApplication.getInstance().getPass());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result, ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        MyApplication.getInstance().setUserId(jsonObject.getString(ConstantString.USER_ID));
                        MyApplication.getInstance().setToken(jsonObject.getString(ConstantString.TOKEN));
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log("错误");
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
    public void InitData() {

    }

    @Override
    public void setOnClickListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
