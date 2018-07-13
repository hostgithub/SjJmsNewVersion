package com.gdtc.sjjms.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.utils.DialogTools;
import com.gdtc.sjjms.utils.ToastUtil;

/**
 * Created by wangjiawei on 2017/7/17.
 */

public abstract class BaseFragment extends Fragment
{


    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据


    public abstract int getLayoutId();

    public abstract void initVariables();

    public abstract void initViews(View view, Bundle savedInstanceState);

    public abstract void initLoadData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View views = inflater.inflate(getLayoutId(), container, false);
        initVariables();
        initViews(views,savedInstanceState);
        initLoadData();
        return views;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lazyFetchDataIfPrepared();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        lazyFetchDataIfPrepared();
    }

    private void lazyFetchDataIfPrepared() {
        if (isViewPrepared && getUserVisibleHint() && !hasFetchData) {
            lazyFetchData();
            hasFetchData = true;
        }
    }


    /**
     * 开启加载效果
     */
    public void startProgressDialog() {
        DialogTools.showWaittingDialog(getActivity());
    }

    /**
     * 关闭加载
     */
    public void stopProgressDialog() {
        DialogTools.closeWaittingDialog();
    }

    /**
     * 显示错误的dialog
     */
    public void showErrorHint(String errorContent) {
        View errorView = LayoutInflater.from(getActivity()).inflate(R.layout.app_error_tip,null);
        TextView tvContent = (TextView)errorView.findViewById(R.id.content);
        tvContent.setText(errorContent);
        new ToastUtil(errorView);
    }

    protected abstract void lazyFetchData();

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
