package com.iwind.red_apple.Video;

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
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.Constant.ConstantUrl;
import com.iwind.red_apple.R;

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
    private String TEST_URL = "http://192.168.0.200:8080/com.nkbh.pro/a.mp4";


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        try {
            InitView();
            InitData();
            setOnClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mSuperVideoPlayer.loadAndPlay(TEST_URL,
                savedInstanceState.getInt("time"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void InitView() {
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
        title_bar.setRightImageLeftResource(R.drawable.iv_search);
        title_bar.setRightImageRightResource(R.drawable.iv_add);

    }

    @Override
    public void InitData() {
        ShowLoadingDialog();
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
                            videoUrl1.setFormatUrl(ConstantUrl.VIDEO_URL + jsonArray
                                    .getJSONObject(i).getString(ConstantString.CLIENT_VIDEO_URL));
                            Log(ConstantUrl.VIDEO_URL + jsonArray
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
