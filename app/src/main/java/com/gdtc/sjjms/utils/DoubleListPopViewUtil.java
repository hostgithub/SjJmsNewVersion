package com.gdtc.sjjms.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.event.EventUtil;
import com.gdtc.sjjms.service.Api;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 二级菜单 popWindow
 * Created by Administrator on 2016/9/19.
 */
public abstract class DoubleListPopViewUtil {

    private ListView listView;
    private Context context;
    private View view;//在哪个view的下面
    //数据源
//    private List<String> roots;//根目录的节点
    private List<AreaOneBean.ResultsBean> roots;//根目录的节点
//    private List<List<String>> sub_items;// 子目录节点
    private List<AreaTwoBean.ResultsBean> sub_items=new ArrayList<>();// 子目录节点
    private PopupWindow areaPopupWindow;
    private RootListViewAdapter rootAdapter;
    private SubListViewAdapter subAdapter;

    public abstract void onRootListviewOnClick(View v, int position);
    public abstract void onSubListviewOnClick(View v, int position);

    public DoubleListPopViewUtil(Context context, View view, List<AreaOneBean.ResultsBean> roots) {
        this.context = context;
        this.view = view;
        this.roots = roots;
        //this.sub_items = sub_items;
        init();
    }

    private  void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        final LinearLayout areaPopupLayout = (LinearLayout) inflater.inflate(R.layout.area_popupwindow_layout, null, false);
        final FrameLayout subAreaLayout = (FrameLayout) areaPopupLayout.findViewById(R.id.sub_popupwindow);
        FrameLayout rootAreaLayout = (FrameLayout) areaPopupLayout.findViewById(R.id.root_popupwindow);
        rootAreaLayout.setBackgroundResource(R.color.white);
        ListView rootAreaListView = (ListView) areaPopupLayout.findViewById(R.id.root_listview);
        final ListView subAreaListView = (ListView) areaPopupLayout.findViewById(R.id.sub_listview);

        rootAdapter = new RootListViewAdapter(context);
        rootAdapter.setItems(roots);
        rootAreaListView.setAdapter(rootAdapter);

        subAdapter = new SubListViewAdapter(context, sub_items);
        subAreaListView.setAdapter(subAdapter);
//        subAdapter = new SubListViewAdapter(context, sub_items);
//        subAreaListView.setAdapter(subAdapter);

        subAreaLayout.setVisibility(View.INVISIBLE);//弹出popupwindow时，二级菜单默认隐藏，当点击某项时，二级菜单再弹出
        areaPopupWindow = new PopupWindow(areaPopupLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        areaPopupWindow.setBackgroundDrawable(new BitmapDrawable());//使点击popupwindow以外的区域时popupwindow自动消失须放在showAsDropDown之前
        areaPopupWindow.showAsDropDown(view, -5, 5);// 参数:2：左右的偏移量，正数向左偏移，负数向右偏移，参数3：上下方向的偏移量，正数向下偏移，负数向上偏移
        areaPopupWindow.update();
        rootAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                rootAdapter.setSelectedPosition(position);//选中root某项时改变该ListView item的背景色
                rootAdapter.notifyDataSetInvalidated();
                //subAdapter.setRoot_position(position);
                initAreaTwoData(roots.get(position).getRegionalId());
                onRootListviewOnClick(view,position);
                subAreaLayout.setVisibility(View.VISIBLE);//选中某个根节点时，使显示相应的子目录可见
//                subAdapter = new SubListViewAdapter(context, sub_items);
//                subAdapter.setItems(sub_items);
//                subAreaListView.setAdapter(subAdapter);
//                subAdapter.notifyDataSetChanged();
            }
        });
        subAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                EventBus.getDefault().post(new EventUtil(sub_items.get(position).getRegionalId(),sub_items.get(position).getRegionalStreet()));
               onSubListviewOnClick(view,position);
            }
        });
    }


    private void initAreaTwoData(String id) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AreaTwoBean> call=api.getAreaTwoBeanData(id);
        call.enqueue(new Callback<AreaTwoBean>() {
            @Override
            public void onResponse(Call<AreaTwoBean> call, Response<AreaTwoBean> response) {
                if(sub_items!=null){
                    sub_items.clear();
                }
                sub_items.addAll(response.body().getResults());
                subAdapter.setItems(sub_items);
                subAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<AreaTwoBean> call, Throwable t) {
                Toast.makeText(context,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void notifyDataSetChangedRoots(List<AreaOneBean.ResultsBean> roots){
        this.roots = roots;
        rootAdapter.setItems(roots);
        rootAdapter.notifyDataSetChanged();
    }
    public void notifyDataSetChangedSubItem(List<AreaTwoBean.ResultsBean>  sub_items){
        this.sub_items = sub_items;
        subAdapter.setItems(sub_items);
        subAdapter.notifyDataSetChanged();
    }

    public void show(){
        areaPopupWindow.showAsDropDown(view);
    }
    public void dismiss(){
        if (areaPopupWindow != null && areaPopupWindow.isShowing()){
            areaPopupWindow.dismiss();
        }
    }
}
