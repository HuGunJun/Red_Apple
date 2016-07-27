package com.iwind.red_apple.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * 我的提问适配器
 * 作者：HuGuoJun
 * 2016/6/12 17:54
 * 邮箱：www.guojunkuaile@qq.com
 */
public class MyQuestionAdapter extends BaseAdapter {

    List<HashMap<String, String>> list;
    Context context;

    public MyQuestionAdapter(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myquestion, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String[] s = list.get(position).get(ConstantString.FORUMKEYWORD).split(",");
        String label = "";
        for (int i = 0; i < s.length; i++) {
            if (!s[i].equals("")) {
                label = label + s[i] + "-";
            }
        }
        holder.tv_content.setText(list.get(position).get(ConstantString.FORUM_CONTENT));
        holder.tv_type.setText(label);
        holder.tv_title.setText(list.get(position).get(ConstantString.FORUM_TITLE));


        return convertView;
    }

    private class ViewHolder {
        @ViewInject(R.id.tv_type)
        TextView tv_type;
        @ViewInject(R.id.tv_content)
        TextView tv_content;
        @ViewInject(R.id.tv_title)
        TextView tv_title;
    }
}
