package com.iwind.red_apple.Utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.easemob.easeui.utils.ResponseUtils;
import com.iwind.red_apple.Constant.ConstantString;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * 作者：HuGuoJun
 * 2016/7/26 11:59
 * 邮箱：www.guojunkuaile@qq.com
 */
public class ShowImageInText {
    TextView mTextView;

    public ShowImageInText(final String source, TextView textView) {
        this.mTextView = textView;
        new Thread() {
            @Override
            public void run() {
                Spanned sp = null;
                sp = Html.fromHtml(source, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable mDrawable = null;
                        try {
                            mDrawable = Drawable.createFromStream(
                                    new URL(source).openStream(), "");
                            mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(),
                                    mDrawable.getIntrinsicHeight());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return mDrawable;
                    }
                }, null);
                Message msg = new Message();
                msg.obj = sp;
                mHandler.sendMessage(msg);
            }
        }.start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Spanned sp = (Spanned) msg.obj;
            mTextView.setText(sp);
        }
    };


}
