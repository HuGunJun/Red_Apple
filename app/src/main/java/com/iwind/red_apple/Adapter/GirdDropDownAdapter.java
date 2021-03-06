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


public class GirdDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<HashMap<String, String>> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public GirdDropDownAdapter(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position).get(ConstantString.TAX_TYPE));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color
                        .drop_down_selected));
                viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context
                        .getResources().getDrawable(R.drawable.drop_down_checked), null);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color
                        .drop_down_unselected));
                viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }

    static class ViewHolder {
        @ViewInject(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
