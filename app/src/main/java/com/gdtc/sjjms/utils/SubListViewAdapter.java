package com.gdtc.sjjms.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.bean.AreaTwoBean;

import java.util.List;



/**
 * Created by Administrator on 2016/8/20.
 */
public class SubListViewAdapter extends BaseAdapter {

//    List<List<String>> sub_items;
    private List<AreaTwoBean.ResultsBean> sub_items;
    private Context context;
    private int root_position;
    private LayoutInflater inflater;

    public SubListViewAdapter(Context context, List<AreaTwoBean.ResultsBean> sub_items) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.sub_items = sub_items;
    }
//    public void setRoot_position(int position){
//        this.root_position = position;
//    }
        public void updateData(List<AreaTwoBean.ResultsBean> list) {
            sub_items.clear();
            sub_items.addAll(list);
            this.notifyDataSetChanged();
        }

    public void setItems(List<AreaTwoBean.ResultsBean> sub_items) {
        this.sub_items = sub_items;
    }
    @Override
    public int getCount() {

        if (sub_items == null || sub_items.size() == 0){
            return 0;
        }else {
//            return sub_items.get(root_position) == null ? 0:sub_items.get(root_position).size();
            return sub_items.size();
        }
    }

    @Override
    public Object getItem(int position) {

//        return sub_items.get(root_position).get(position);
        return sub_items.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.root_listview_item, parent, false);
            holder.item_text = (TextView) convertView.findViewById(R.id.item_name_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_text.setText(sub_items.get(position).getRegionalStreet());
        return convertView;
    }
    class ViewHolder{
        TextView item_text;
    }

}
