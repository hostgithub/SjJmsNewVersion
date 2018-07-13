package com.gdtc.sjjms.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.bean.RecGoodsBean;

import java.util.List;


public class RecGoodsAdapter extends CommonAdapter<RecGoodsBean> {

    public RecGoodsAdapter(Context context, List<RecGoodsBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public RecGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void setData(List<RecGoodsBean> datas) {
        super.setData(datas);
    }

    @Override
    public void convert(ViewHolder holder, RecGoodsBean recGoodsBean, int position) {

        ImageView img = holder.getView(R.id.iv_goods_img);
        TextView goodsName = holder.getView(R.id.tv_goods_name);
        TextView serviceTime = holder.getView(R.id.tv_service_time);
        TextView score = holder.getView(R.id.tv_score);
        TextView soldOut = holder.getView(R.id.tv_sold_out);

        holder.setText(R.id.tv_goods_name, recGoodsBean.getGoodsName());
        holder.setText(R.id.tv_service_time, "使用时间：" + recGoodsBean.getServiceTime());
        holder.setText(R.id.tv_score, recGoodsBean.getScore() + "积分");
        holder.setText(R.id.tv_sold_out, "已售" + recGoodsBean.getSoldOut() + "份");
    }
}
