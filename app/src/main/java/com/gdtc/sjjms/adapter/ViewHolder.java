package com.gdtc.sjjms.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/*****************************
 * @Copyright(c) 2014-2018
 * 湖南惟仕信息科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/10
 * @Description：万能ViewHolder
 * @Version:v1.0.0
 *****************************/
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public View getConvertView() {
        return mConvertView;
    }

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (null == convertView) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;

            return holder;
        }
    }


    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }


    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (null != text) {
            tv.setText(text);
        }
        return this;
    }

    public ViewHolder setMaxLines(int viewId, int lines) {
        TextView tv = getView(viewId);
        tv.setMaxLines(lines);
        return this;
    }


    public ViewHolder setImage(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ViewHolder setBackGround(int viewId, Drawable drawable) {
        View iv = getView(viewId);
        iv.setBackground(drawable);
        return this;
    }

    public ViewHolder setBackGroundColor(int viewId, int color) {
        View iv = getView(viewId);
        iv.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        TextView iv = getView(viewId);
        iv.setTextColor(color);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean flag) {
        CheckBox cb = getView(viewId);
        cb.setChecked(flag);
        return this;
    }

    public ViewHolder setVisible(int viewId, int visible) {
        View v = getView(viewId);
        v.setVisibility(visible);
        return this;
    }

    public ViewHolder setClickListener(int viewId, boolean isUrl, final Context context, final String url) {
        final boolean flag = isUrl;
        View v = getView(viewId);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    // 原文链接
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(it);
                }
            }
        });
        return this;
    }
}
