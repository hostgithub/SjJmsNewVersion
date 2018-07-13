package com.gdtc.sjjms.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by llf on 2017/1/4.
 * 上拉加载接口
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener{
    private LinearLayoutManager manager;


    private static final int SCROLL_DISTANCE = 20;
    private int totalScrollDistance;
    private boolean isShow = true;


    public EndLessOnScrollListener(LinearLayoutManager manager){
        this.manager = manager;
    }

    public EndLessOnScrollListener(GridLayoutManager manager){
        this.manager = manager;
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == RecyclerView.SCROLL_STATE_IDLE){
            int lastVisiblePosition = manager.findLastVisibleItemPosition();
            if(lastVisiblePosition >= manager.getItemCount() - 1){
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisableItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        //当第一个item存在界面上时就不触发隐藏、显示操作
        if(firstVisableItem==0){
            return;
        }
        if ((dy > 0 && isShow) || (dy < 0 && !isShow)) {
            totalScrollDistance += dy;
        }
        if (totalScrollDistance > SCROLL_DISTANCE && isShow) {
            hide();
            isShow = false;
            totalScrollDistance = 0;
        } else if (totalScrollDistance < -SCROLL_DISTANCE && !isShow) {
            show();
            isShow = true;
            totalScrollDistance = 0;
        }
    }

    /**
     * 提供一个抽象方法，在Activity中监听到这个EndLessOnScrollListener
     * 并且实现这个方法
     * */
    public abstract void onLoadMore();


    public abstract void hide();

    public abstract void show();
}
