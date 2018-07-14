package com.gdtc.sjjms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseViewHolder;
import com.gdtc.sjjms.bean.NewCenter;
import com.gdtc.sjjms.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llf on 2017/4/19.
 * 发现的适配器，分为两种样式
 */

public class RemenAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private ArrayList<NewCenter.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;

    public RemenAdapter(Context context, ArrayList<NewCenter.ResultsBean> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    public void replaceAll(List<NewCenter.ResultsBean> elements) {
        if (datas.size() > 0) {
            datas.clear();
        }
        datas.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(List<NewCenter.ResultsBean> elements) {
        datas.addAll(elements);
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == ITEM_NOIMAGE) {
//            icon_back new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cardview_remen, parent, false));
//        } else if (viewType == ITEM_HASIMAGE) {
//            icon_back new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cardview_remen, parent, false));
//        } else {
//            footerView = LayoutInflater.from(mContext).inflate(viewFooter, parent, false);
//            icon_back new BaseViewHolder(footerView);
//        }

        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cardview_remen, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (!(viewFooter != 0 && position == getItemCount() - 1)) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }

            //int type = getItemViewType(position);
            final NewCenter.ResultsBean item = datas.get(position);

            ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.cover), Config.BANNER_BASE_URL+item.picName);


            //存在记录的高度时先Layout再异步加载图片
//            if (datas.get(holder.getAdapterPosition()).getHeight() > 0) {
//                ViewGroup.LayoutParams layoutParams = holder.getView(R.id.cover).getLayoutParams();
//                layoutParams.height = datas.get(holder.getAdapterPosition()).getHeight();
//            }

//            //获取屏幕宽度
//            WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics dm = new DisplayMetrics();
//            Display display = windowManager.getDefaultDisplay();
//            display.getMetrics(dm);
//            final int screenWidth = dm.widthPixels;
//
//            String url = Config.BANNER_BASE_URL+item.picName;
//            Glide.with(mContext).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(new SimpleTarget<Bitmap>(screenWidth, screenWidth) {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
//                                if (datas.get(holder.getAdapterPosition()).getHeight() <= 0) {
//                                    int width = resource.getWidth();
//                                    int height = resource.getHeight();
//                                    int realHeight = screenWidth * height / width / 2;
//                                    datas.get(holder.getAdapterPosition()).setHeight(realHeight);
//                                    ViewGroup.LayoutParams lp = holder.getView(R.id.cover).getLayoutParams();
//                                    lp.height = realHeight;
//                                    if(width < screenWidth / 2)
//                                        lp.width = screenWidth / 2;
//                                }
//                                ((ImageView) holder.getView(R.id.cover)).setImageBitmap(resource);
//                            }
//                        }
//                    });

            //CircleImageView avatar = holder.getView(R.id.avatar);
//            if(item.text.contains(";")){//返回的json中含有分号 一开始用的是startWith方法 结果有两个分号 只能去掉一个
//                String str=item.text.replace(';', ' ');
//                holder.setText(R.id.title, str);
//            }else{
//                holder.setText(R.id.title, item.text);
//            }
//            holder.setText(R.id.content, item.addTime);

//            holder.setText(R.id.author, item.getAuthor());
//            holder.setText(R.id.seeNum, item.getWatch());
//            holder.setText(R.id.commentNum, item.getComments());
//            holder.setText(R.id.loveNum, item.getLike());
//            ImageLoaderUtils.loadingImg(mContext, avatar, HOST + item.getAuthorImg());
//            avatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebViewActivity.lanuch(mContext, HOST + "/member/index.php?uid" + item.getAuthor());
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (viewFooter != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        final int screenWidth = dm.widthPixels;
        return Math.round((float) screenWidth / (float) datas.get(position).getHeight() * 10f);


//        int type = ITEM_HASIMAGE;
//        if (viewFooter != 0 && position == getItemCount() - 1) {
//            type = TYPE_FOOTER;
//            icon_back type;
//        }
//        if (TextUtils.isEmpty(datas.get(position)._id)) {
//            type = ITEM_NOIMAGE;
//        } else {
//            type = ITEM_HASIMAGE;
//        }
//        icon_back type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterVisible(int visible) {
        footerView.setVisibility(visible);
    }

    //设置点击事件
    public void setOnItemClickLitener(OnItemClickListener mLitener) {
        mOnItemClickListener = mLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
