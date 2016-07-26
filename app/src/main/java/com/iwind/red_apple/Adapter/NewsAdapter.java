package com.iwind.red_apple.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwind.red_apple.App.MyApplication;
import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：HuGuoJun
 * 2016/6/12 11:35
 * 邮箱：www.guojunkuaile@qq.com
 */
public class NewsAdapter extends BaseAdapter {


    private Context mContext;
    private List<HashMap<String, String>> mList;


    public NewsAdapter(Context mContext, List<HashMap<String, String>> mapList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        x.image().bind(holder.iv_avator, mList.get(position).get(ConstantString.IV_URL), MyApplication.getInstance()
                .getOptions());
        holder.tv_content.setText(Html.fromHtml(mList.get(position).get(ConstantString.NEW_CONTENT)));
        holder.tv_time.setText(mList.get(position).get(ConstantString.NEW_TIME));
        holder.tv_read_count.setText(mList.get(position).get(ConstantString.ZANCOUTN));
        return convertView;
    }


    private class ViewHolder {
        @ViewInject(R.id.iv_avator)
        ImageView iv_avator;
        @ViewInject(R.id.tv_content)
        TextView tv_content;
        @ViewInject(R.id.tv_read_count)
        TextView tv_read_count;
        @ViewInject(R.id.tv_time)
        TextView tv_time;
    }
}
