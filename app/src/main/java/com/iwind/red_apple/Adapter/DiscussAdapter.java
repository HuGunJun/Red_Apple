package com.iwind.red_apple.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * 首页适配器
 * 作者：HuGuoJun
 * 2016/6/7 10:21
 * 邮箱：www.guojunkuaile@qq.com
 */
public class DiscussAdapter extends BaseAdapter {

    private Context mContext;
    private List<HashMap<String, String>> mList;

    public DiscussAdapter(Context mContext, List<HashMap<String, String>> mapList) {
        this.mContext = mContext;
        this.mList = mapList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_discuss, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageOptions options = MyApplication.getInstance().getOptions();
        x.image().bind(holder.iv_avator, mList.get(position).get(ConstantString.IV_URL), options);
//        Glide.with(mContext).load(mList.get(position).get(ConstantString.IV_URL)).error(R
//                .drawable.icon_default_avatar).into(holder.iv_avator);
        holder.tv_content.setText(mList.get(position).get(ConstantString.CONTENT));
        holder.tv_name.setText(mList.get(position).get(ConstantString.USER_NAME));
        holder.tv_read_count.setText(mList.get(position).get(ConstantString.READ_COUNT));
        holder.tv_type.setText(mList.get(position).get(ConstantString.TYPE));
        return convertView;
    }


    private class ViewHolder {
        @ViewInject(R.id.tv_name)
        TextView tv_name;
        @ViewInject(R.id.iv_avator)
        ImageView iv_avator;
        @ViewInject(R.id.tv_content)
        TextView tv_content;
        @ViewInject(R.id.tv_read_count)
        TextView tv_read_count;
        @ViewInject(R.id.tv_type)
        TextView tv_type;
    }
}
