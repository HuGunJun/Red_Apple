package com.easemob.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.easeui.R;
import com.easemob.easeui.widget.switchview.SegmentView;

/**
 * 标题栏
 */
public class EaseTitleBar extends RelativeLayout {

    protected ImageView leftImage;
    protected ImageView rightImage_left, rightImage_right;
    protected RelativeLayout titleLayout;
    protected TextView tv_right, title;
    protected SegmentView segmentView;

    public EaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightImage_left = (ImageView) findViewById(R.id.right_image_left);
        rightImage_right = (ImageView) findViewById(R.id.right_image_right);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
        title = (TextView) findViewById(R.id.title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        segmentView = (SegmentView) findViewById(R.id.segmentView);
        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseTitleBar);
            String titles = ta.getString(R.styleable.EaseTitleBar_titleBarTitle);
            title.setText(titles);
            if (titles != null) {
                segmentView.setVisibility(View.GONE);
            } else {
                segmentView.setVisibility(View.VISIBLE);
            }
            Drawable leftDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage_left.setImageDrawable(rightDrawable);
            }

            Drawable background = ta.getDrawable(R.styleable.EaseTitleBar_titleBarBackground);
            if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

            ta.recycle();
        }
    }


    /**
     * 设置title——bar整体背景
     *
     * @param background
     */
    public void setTitleLayoutBackground(Drawable background) {
        titleLayout.setBackgroundDrawable(background);
    }

    /**
     * 设置左边图片资源
     *
     * @param resId
     */
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }


    /**
     * 设置左边点击事件
     *
     * @param clickListener
     */
    public void setLeftImageClickListener(OnClickListener clickListener) {
        leftImage.setOnClickListener(clickListener);
    }


    /**
     * 设置标题文字
     *
     * @param strtitle
     */
    public void setTitle(String strtitle) {
        title.setText(strtitle);
        if (!strtitle.equals("") || strtitle != null) {
            segmentView.setVisibility(View.GONE);
        } else {
            segmentView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置中间文字的点击事件
     *
     * @param onClickListener
     */
    public void setTitleClickListener(OnClickListener onClickListener) {
        title.setOnClickListener(onClickListener);
    }


    /**
     * 设置右边菜单里面的左边图片资源
     *
     * @param resId
     */
    public void setRightImageLeftResource(int resId) {
        rightImage_left.setImageResource(resId);
    }


    /**
     * 设置右边菜单里面的左边图片按钮是否可见
     *
     * @param visiable
     */
    public void setRightImageLeftVisiable(boolean visiable) {
        rightImage_left.setVisibility(visiable ? VISIBLE : GONE);
    }

    /**
     * 设置右边菜单左边图片点击事件
     *
     * @param onClickListener
     */
    public void setRightImageLeftClickListener(OnClickListener onClickListener) {
        rightImage_left.setOnClickListener(onClickListener);
    }


    /**
     * 设置右边菜单的右边图片资源
     *
     * @param resId
     */
    public void setRightImageRightResource(int resId) {
        rightImage_right.setImageResource(resId);
    }


    /**
     * 设置右边菜单里面的右边按钮可见性
     *
     * @param visiable
     */
    public void setRightImageRightVisiable(boolean visiable) {
        rightImage_right.setVisibility(visiable ? VISIBLE : GONE);
    }

    /**
     * 设置右边菜单的右边图片点击事件
     *
     * @param onClickListener
     */
    public void setRightImageRightClickListener(OnClickListener onClickListener) {
        rightImage_right.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边文字
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        tv_right.setText(rightText);
    }

    /**
     * 设置右边文字点击事件
     *
     * @param onClickListener
     */
    public void setRightTextClickListener(OnClickListener onClickListener) {
        tv_right.setOnClickListener(onClickListener);

    }

    /**
     * 设置标签改变监听事件
     *
     * @param listener
     */
    public void setSegmentViewIndexChangedListener(SegmentView.OnIndexChangedListener listener) {
        segmentView.setOnIndexChangedListener(listener);
    }


    /**
     * 设置标签默认标签
     *
     * @param index
     */
    public void setSegmentViewIndex(int index) {
        segmentView.setIndex(index);
    }
}
