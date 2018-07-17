package com.gdtc.sjjms.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.bean.AreaOneBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class RootListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
//    private List<String> items;
    private List<AreaOneBean.ResultsBean> items;
    private int selectedPosition = -1;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public RootListViewAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setItems(List<AreaOneBean.ResultsBean> items) {
        this.items = items;
    }

    @Override
    public int getCount() {

        return items == null?0:items.size();
    }

    @Override
    public Object getItem(int position) {

        return items.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.root_listview_item, parent , false);
            holder.item_text =(TextView) convertView.findViewById(R.id.item_name_text);
            holder.item_layout = (LinearLayout)convertView.findViewById(R.id.root_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        /**
         * 该项被选中时改变背景色
         */
        if(selectedPosition == position){
            holder.item_layout.setBackgroundResource(R.color.sub_gray_backcolor);
        }else{
            holder.item_layout.setBackgroundResource(R.color.white);
        }
        holder.item_text.setText(items.get(position).getRegionalStreet());
        return convertView;
    }
    class ViewHolder{
        TextView item_text;
        LinearLayout item_layout;
    }
}
