package com.iwind.red_apple.Mine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseBaseActivity;
import com.easemob.easeui.utils.FileUtils;
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

import java.io.File;

/**
 * 个人信息 修改头像/昵称
 * 作者：HuGuoJun
 * 2016/6/12 16:19
 * 邮箱：www.guojunkuaile@qq.com
 */
@ContentView(R.layout.act_myinfo)
public class MyInfoActivity extends EaseBaseActivity {
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final int REQUEST_CHAGE_NICK = 1001;//修改昵称请求
    private static final int REQUEST_CHAGE_SEX = 1002;//修改性别
    private static final int REQUEST_CHAGE_BIRTH = 1003;//修改生日
    private static final int REQUEST_CHAGE_AREA = 1004;//修改地区
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
    @ViewInject(R.id.iv_avator)
    ImageView iv_avator;

    //用户头像文件名称
    private String User_Photos_File_Name;

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
    protected void onResume() {
        super.onResume();
        x.image().bind(iv_avator, ConstantUrl.USER_PIC + MyApplication.getInstance().getUserPic(), MyApplication
                .getInstance().getOptions());
        InitData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_area:
                startActivityForResult(new Intent(context, EditUserInfoActivity.class).putExtra(ConstantString
                                .CHAGE_INFO_TYPE, REQUEST_CHAGE_AREA),
                        REQUEST_CHAGE_AREA);
                break;
            case R.id.rl_change_birth:
                startActivityForResult(new Intent(context, EditUserInfoActivity.class).putExtra(ConstantString
                                .CHAGE_INFO_TYPE, REQUEST_CHAGE_BIRTH),
                        REQUEST_CHAGE_BIRTH);
                break;
            case R.id.rl_change_sex:
                startActivityForResult(new Intent(context, EditUserInfoActivity.class).putExtra(ConstantString
                                .CHAGE_INFO_TYPE, REQUEST_CHAGE_SEX),
                        REQUEST_CHAGE_SEX);
                break;
            case R.id.rl_change_nick:
                startActivityForResult(new Intent(context, EditUserInfoActivity.class).putExtra(ConstantString
                                .CHAGE_INFO_TYPE, REQUEST_CHAGE_NICK),
                        REQUEST_CHAGE_NICK);
                break;
            case R.id.rl_change_avatar:
                new ActionSheetDialog(context).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem(getResources().getString(R.string.photos),
                                ActionSheetDialog.SheetItemColor
                                        .Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Intent intents = null;
                                        intents = new Intent(
                                                Intent.ACTION_PICK, null);
                                        intents.setDataAndType(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
                                        startActivityForResult(intents,
                                                PHOTO_REQUEST_GALLERY);

                                    }
                                }).addSheetItem(getResources().getString(R.string.take_photos),
                        ActionSheetDialog.SheetItemColor
                                .Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intents = null;
                                intents = new Intent();
                                Intent intent_camera = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.camera");
                                if (intent_camera != null) {
                                    intents.setPackage("com.android.camera");
                                }
                                intents.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 指定调用相机拍照后照片的储存路径
                                intents.putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(new File(FileUtils
                                                .getImagePath(context,
                                                        false)
                                                + User_Photos_File_Name)));
                                startActivityForResult(intents,
                                        PHOTO_REQUEST_TAKEPHOTO);


                            }
                        }).show();
                break;
        }
    }


    @Override
    public void InitView() {
        User_Photos_File_Name = MyApplication.getInstance().getUserName() + ".jpg";
        title_bar.setTitle(getResources().getString(R.string.personal_info));
        title_bar.setLeftImageResource(R.drawable.ease_mm_title_back);
    }

    @Override
    public void InitData() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                .GET_PERSONAL_INFO);
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log(result);
                CloseLoadingDialog();
                if (ResponseUtils.isSuccess(context, ConstantString.RESULT_STATE, result,
                        ConstantString.STATE,
                        ConstantString.RESULT_INFO)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject obj = jsonObject.getJSONObject(ConstantString.OBJ);
                        tv_nick.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString
                                .NICK_NAME)));
                        tv_sex.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString.SEX)));
                        tv_birth.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString
                                .BIRTH)));
                        tv_area.setText(ResponseUtils.ParaseNull(obj.getString(ConstantString
                                .CITY)) + "-" +
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
//                tv_nick.setText(data.getExtras().getString(ConstantString.NICK));
                break;
            case REQUEST_CHAGE_SEX:
                break;
            case REQUEST_CHAGE_BIRTH:
                break;
            case REQUEST_CHAGE_AREA:
                break;
            case PHOTO_REQUEST_TAKEPHOTO:
                startPhotoZoom(Uri.fromFile(new File(FileUtils.getImagePath(
                        context, false) + User_Photos_File_Name)), 150);
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 150);
                break;
            case PHOTO_REQUEST_CUT:
                UpLoadUserPic();
                break;
        }
    }

    /**
     * 开始裁剪
     *
     * @param uri
     * @param size
     */

    public void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(FileUtils.getImagePath(context, true)
                        + User_Photos_File_Name)));
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    private void UpLoadUserPic() {
        ShowLoadingDialog();
        RequestParams params = new RequestParams(ConstantUrl.BASE_URL + ConstantUrl
                .UPLOAD_USER_ICON);
        params.addBodyParameter("imgFile", new File(FileUtils.getImagePath(context, true)
                + User_Photos_File_Name));
        params.addBodyParameter(ConstantString.USER_ID, MyApplication.getInstance().getUserid());
        params.addBodyParameter(ConstantString.TOKEN, MyApplication.getInstance().getToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CloseLoadingDialog();
                Log(result);
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


}
