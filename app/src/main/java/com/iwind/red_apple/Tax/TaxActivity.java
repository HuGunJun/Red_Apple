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
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.cycleview.CycleViewPager;
import com.easemob.easeui.widget.cycleview.CycleVpEntity;
import com.easemob.easeui.widget.cycleview.ViewFactory;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Date;
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
    //最新提问
    @ViewInject(R.id.tv_new_question)
    TextView tv_new_question;
    @ViewInject(R.id.tv_new_answer)
    TextView tv_new_answer;
    @ViewInject(R.id.tv_new_move)
    TextView tv_new_move;
    private CycleViewPager cycleViewPager;
    private View vhdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        GetImageTurn();
    }

    /**
     * 获取轮播图
     */
    private void GetImageTurn() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_IMAGE_TURN);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (ResponseUtils.isSuccess(getApplicationContext(), ConstantString.RESULT_STATE, result,
                            ConstantString.STATE, ConstantString.RESULT_INFO)) {
                        JSONArray jsonArray = object.getJSONArray(ConstantString.ARRAY);

                        List<CycleVpEntity> list = new ArrayList<CycleVpEntity>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            CycleVpEntity cyc = new CycleVpEntity();
                            //图片id
                            cyc.setId(jsonArray.getJSONObject(i).getString(ConstantString.CAROUSE_ID));
                            //图片地址
                            cyc.setIurl(jsonArray.getJSONObject(i).getString(ConstantString.CAROUSE_URL));
                            //图片名称
                            cyc.setTitle(jsonArray.getJSONObject(i).getString(ConstantString.CAROUSE_NAME));
                            list.add(cyc);
                        }
                        /**
                         * 初始化轮播图
                         */
                        vhdf = getLayoutInflater().inflate(R.layout.ease_viewpage, null);
                        cycleViewPager = (CycleViewPager) getFragmentManager()
                                .findFragmentById(R.id.fragment_cycle_viewpager_content);
                        ViewFactory.initialize(getApplicationContext(), vhdf, cycleViewPager, list,
                                new CycleViewPager.ImageCycleViewListener() {

                                    @Override
                                    public void onImageClick(CycleVpEntity info, int postion,
                                                             View imageView) {

                                    }
                                });
                        cycleViewPager.SetIndicatorResouse(R.mipmap.iv_turn_on, R.mipmap.iv_turn_off);
                        lv_imageviewturn.addView(vhdf);
                        GetNew();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("main", "失败了");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /***
     * 获得最新条数
     */
    private void GetNew() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GET_NEW);
        params.addBodyParameter(ConstantString.DATE, new Date().getTime() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("main", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (ResponseUtils.isSuccess(getApplicationContext(), ConstantString.RESULT_STATE, result,
                            ConstantString.STATE,
                            ConstantString.RESULT_INFO)) {
                        tv_new_question.setText(jsonObject.getJSONObject(ConstantString.MAP).getString(ConstantString
                                .FORUMCOUNT));
                        tv_new_answer.setText(jsonObject.getJSONObject(ConstantString.MAP).getString(ConstantString
                                .MESSAGECOUNT));
                        tv_new_move.setText(jsonObject.getJSONObject(ConstantString.MAP).getString(ConstantString
                                .NEWSCOUNT));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

            }
        });
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
}
