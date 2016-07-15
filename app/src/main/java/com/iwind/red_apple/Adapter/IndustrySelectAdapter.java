package com.iwind.red_apple.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.iwind.red_apple.Constant.ConstantString;
import com.iwind.red_apple.R;

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
public class IndustrySelectAdapter extends BaseAdapter {
    private Context mContext;
    private static List<HashMap<String, String>> mList;

    public IndustrySelectAdapter(Context mContext, List<HashMap<String, String>> mapList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_industry_select, null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_type.setText(mList.get(position).get(ConstantString.TYPE));
        holder.rb_check.setChecked(mList.get(position).get(ConstantString.IS_SELECT).equals("1") ? true : false);
        return convertView;
    }


    public static void SetSelect(int positon) {
        Log.i("main", "" + positon);
        mList.get(positon).put(ConstantString.IS_SELECT, "1");
        for (int i = 0; i < mList.size(); i++) {
            if (i == positon) {
                mList.get(i).put(ConstantString.IS_SELECT, "1");
            } else {
                mList.get(i).put(ConstantString.IS_SELECT, "0");
            }
        }
    }

    private class ViewHolder {
        @ViewInject(R.id.tv_type)
        TextView tv_type;
        @ViewInject(R.id.rb_check)
        RadioButton rb_check;
    }


}
