package com.iwind.red_apple.Video;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.DensityUtil;
import com.easemob.easeui.utils.ResponseUtils;
import com.easemob.easeui.widget.EaseTitleBar;
import com.easemob.easeui.widget.videoview.MediaController;
import com.easemob.easeui.widget.videoview.SuperVideoPlayer;
import com.easemob.easeui.widget.videoview.Video;
import com.easemob.easeui.widget.videoview.VideoUrl;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;
import com.iwind.red_apple.Tax.PersonalInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 作者：HuGuoJun
 * 2016/7/13 13:45
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.activity_act_video_detail)
public class VideoDetailActivity extends EaseBaseActivity {

    @ViewInject(R.id.title_bar)
    EaseTitleBar title_bar;
    @ViewInject(R.id.tv_question_describe)
    TextView tv_question_describe;
    @ViewInject(R.id.video_player_item_1)
    SuperVideoPlayer mSuperVideoPlayer;
    @ViewInject(R.id.tv_answer_content)
    TextView tv_answer_content;
    @ViewInject(R.id.tv_zan_count)
    TextView tv_zan_count;
    @ViewInject(R.id.tv_cai_count)
    TextView tv_cai_count;

    String test_url = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        try {
            InitView();
            ShowLoadingDialog();
            LoadVideoView();
            Update(1);
            setOnClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadVideoView() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GETCLIENTDETAIL);
        params.addBodyParameter(ConstantString.CLIENT_ID, getIntent().getExtras().getString(ConstantString.CLIENT_ID));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(ConstantString.OBJ);
                        title_bar.setTitle(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_TITLE)));
                        tv_question_describe.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_TITLE)));
                        tv_answer_content.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_CONTENT)));
                        tv_zan_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.ZANCOUTN))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .ZANCOUTN)));
                        tv_cai_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.CAICOUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CAICOUNT)));
                        JSONObject objlist = jsonObject.getJSONObject(ConstantString.ARRAY);
                        //文件列表
                        /**
                         *this area is for filelist
                         */
                        //视频列表
                        JSONArray jsonArray = objlist.getJSONArray(ConstantString.VIDEOLIST);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ArrayList<Video> videoArrayList = new ArrayList<>();
                            Video video = new Video();
                            ArrayList<VideoUrl> arrayList1 = new ArrayList<>();
                            VideoUrl videoUrl1 = new VideoUrl();
                            videoUrl1.setFormatName("720P");
                            test_url = jsonArray
                                    .getJSONObject(i).getString(ConstantString.CLIENT_VIDEO_URL);
                            videoUrl1.setFormatUrl(ConstantUrl.VIDEO_URL + jsonArray
                                    .getJSONObject(i).getString(ConstantString.CLIENT_VIDEO_URL));
                            arrayList1.add(videoUrl1);
                            video.setVideoName("视频");
                            video.setVideoUrl(arrayList1);
                            videoArrayList.add(video);
                            mSuperVideoPlayer.loadMultipleVideo(videoArrayList);
                        }
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

    /**
     * 1查看  2分享
     *
     * @param i
     */
    private void Update(final int i) {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.CLIENT_UPDATE);
        params.addBodyParameter(ConstantString.CLIENT_ID, getIntent().getExtras().getString(ConstantString.CLIENT_ID));
        params.addBodyParameter(ConstantString.TYPE, "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (i == 1) {
                            InitData();
                        }
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

            }
        });
    }


    /**
     * 视频踩赞
     *
     * @param i
     */
    private void MethodCaiOrZan(final int i) {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.CLIENT_CAIORZAN);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.CLIENT_CAIORZAN_TYPE, i + "");
        params.addBodyParameter(ConstantString.CLIENT_ID, getIntent().getExtras().getString
                (ConstantString.CLIENT_ID));
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
                ShowLoadingDialog();
                InitData();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", mSuperVideoPlayer.getSuperVideoView()
                .getCurrentPosition());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null == mSuperVideoPlayer)
            return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
            final WindowManager.LayoutParams attrs = getWindow()
                    .getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.0f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
        mSuperVideoPlayer.loadAndPlay(test_url,
                savedInstanceState.getInt("time"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_cai:
                MethodCaiOrZan(1);
                break;
            case R.id.lv_zan:
                MethodCaiOrZan(2);
                break;
            case R.id.lv_personal:
                startActivity(new Intent(context, PersonalInfo.class).putExtra(ConstantString.CLIENT_ID, getIntent()
                        .getExtras().getString(ConstantString.CLIENT_ID)));
                break;
            case R.id.lv_collection:
                Collection();
                break;
        }
    }

    /**
     * 收藏
     */
    private void Collection() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.COLLECTION);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        params.addBodyParameter(ConstantString.MOUDEL_ID, getIntent().getExtras().getString(ConstantString.CLIENT_ID));
        params.addBodyParameter(ConstantString.MOUDEL_TYPE, "5");//5为客户端详情收藏
        x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log(result);
                        if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                                ConstantString.STATE,
                                ConstantString.RESULT_INFO)) {
                            Toast("操作成功");
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
                }

        );
    }


    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);

    }

    @Override
    public void InitData() {
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl.GETCLIENTDETAIL);
        params.addBodyParameter(ConstantString.CLIENT_ID, getIntent().getExtras().getString(ConstantString.CLIENT_ID));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(ConstantString.OBJ);
                        title_bar.setTitle(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_TITLE)));
                        tv_question_describe.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_TITLE)));
                        tv_answer_content.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CLIENT_CONTENT)));
                        tv_zan_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.ZANCOUTN))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .ZANCOUTN)));
                        tv_cai_count.setText(ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString.CAICOUNT))
                                .equals("") ? "0" : ResponseUtils.ParaseNull(jsonObject1.getString(ConstantString
                                .CAICOUNT)));
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

    @Override
    public void setOnClickListener() {
        title_bar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_bar.setRightImageRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
            }
        });
        title_bar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_question_describe.getVisibility() == View.VISIBLE) {
                    tv_question_describe.setVisibility(View.GONE);
                } else {
                    tv_question_describe.setVisibility(View.VISIBLE);
                }
            }
        });

        mSuperVideoPlayer.setVideoPlayCallback(new SuperVideoPlayer.VideoPlayCallbackImpl() {
            @Override
            public void onCloseVideo() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer
                        .setPageType(MediaController.PageType.SHRINK);
                mSuperVideoPlayer.stopPlay();
            }

            @Override
            public void onSwitchPageType() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    mSuperVideoPlayer
                            .setPageType(MediaController.PageType.SHRINK);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    mSuperVideoPlayer
                            .setPageType(MediaController.PageType.EXPAND);
                }
            }

            @Override
            public void onPlayFinish() {
            }
        });


    }
}
