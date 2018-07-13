package com.gdtc.sjjms.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.adapter.RecGoodsAdapter;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.bean.RecGoodsBean;
import com.gdtc.sjjms.widget.CustomScrollView;
import com.gdtc.sjjms.widget.MyListView;
import com.gdtc.sjjms.widget.ScrollChangedListener;
import com.qbw.customview.RefreshLoadMoreLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Test1Fragment extends BaseFragment implements  RefreshLoadMoreLayout.CallBack {
    private static final String TAG = Test1Fragment.class.getSimpleName();

    /**
     * 请求码
     */
    private static final int REQUEST_CODE = 0;
    /**
     * 所需的全部权限
     */
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_CODE_SCAN = 1;

    private static final int REQUEST_LOCATION = 2;
    /**
     * 权限检测器
     */
    //private PermissionsChecker mPermissionsChecker;
    @BindView(R.id.refreshloadmore)
    RefreshLoadMoreLayout mRefreshloadmore;
    protected Handler mHandler = new Handler();

    @BindView(R.id.sv_home)
    CustomScrollView sv_home;

    @BindView(R.id.tv_address)
     TextView tv_address;
    @BindView(R.id.tv_search)
     TextView tv_search;
    @BindView(R.id.iv_qr_code)
     ImageView iv_qr_code;
    @BindView(R.id.lv_rec_goods)
    MyListView lv_rec_goods;
    private RecGoodsAdapter mRecGoodsAdapter;

    private TextView tv_shops_rec, tv_lianqu_store, tv_play_game, tv_live,
            tv_eat, tv_drink, tv_play, tv_happy;

    @BindView(R.id.ll_star)
     LinearLayout ll_star;
    @BindView(R.id.tv_grade)
     TextView tv_grade;
    @BindView(R.id.btn_enter_shops)
     Button btn_enter_shops;

    private ArrayList<RecGoodsBean> goodsDatas;
    /**
     * 轮播图切换线程池
     */
    private ScheduledExecutorService scheduledExecutorService;
    @BindView(R.id.fl_banner)
     FrameLayout fl_banner;
    private ViewGroup.LayoutParams para;
    @BindView(R.id.viewPager)
     ViewPager viewPager;
    private ArrayList<Integer> picList;
    /**
     * 当前图片的索引号
     */
    private int currentItem = 0;
    private ArrayList<ImageView> imageViewList;
    @BindView(R.id.ll_points)
     LinearLayout ll_points;
    private List<View> points;
    private Unbinder mUnbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test1;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initLoadData() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState)  {

        mUnbinder = ButterKnife.bind(this, view);
        //mPermissionsChecker = new PermissionsChecker(getActivity());

        //mRefreshloadmore = findView(R.id.refreshloadmore);
        mRefreshloadmore.init(new RefreshLoadMoreLayout.Config(this).showLastRefreshTime(getClass()).autoLoadMore());

        //sv_home = findView(R.id.sv_home);
        //ScrollView固定在顶部
        sv_home.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sv_home.post(new Runnable() {
                    @Override
                    public void run() {
                        sv_home.fullScroll(View.FOCUS_UP);
                    }
                });
            }
        });

//        tv_address = findView(R.id.tv_address);
//        tv_search = findView(R.id.tv_search);
//        iv_qr_code = findView(R.id.iv_qr_code);
//
//        tv_shops_rec = findView(R.id.tv_shops_rec);
//        tv_lianqu_store = findView(R.id.tv_lianqu_store);
//        tv_play_game = findView(R.id.tv_play_game);
//        tv_live = findView(R.id.tv_live);
//        tv_eat = findView(R.id.tv_eat);
//        tv_drink = findView(R.id.tv_drink);
//        tv_play = findView(R.id.tv_play);
//        tv_happy = findView(R.id.tv_happy);
//
//        ll_star = findView(R.id.ll_star);
//        tv_grade = findView(R.id.tv_grade);
//        btn_enter_shops = findView(R.id.btn_enter_shops);

//        if (YiyeApplication.locationListBean != null) {
//            tv_address.setText(YiyeApplication.locationListBean.getCity());
//        } else {
//            tv_address.setText("定位中..");
//        }

//        tv_address.setOnClickListener(this);
//        tv_search.setOnClickListener(this);
//        iv_qr_code.setOnClickListener(this);
//        tv_shops_rec.setOnClickListener(this);
//        tv_lianqu_store.setOnClickListener(this);
//        tv_play_game.setOnClickListener(this);
//        tv_live.setOnClickListener(this);
//        tv_eat.setOnClickListener(this);
//        tv_drink.setOnClickListener(this);
//        tv_play.setOnClickListener(this);
//        tv_happy.setOnClickListener(this);
//
//        btn_enter_shops.setOnClickListener(this);

        /**轮播图*/
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        para = fl_banner.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 1 / 2;
        fl_banner.setLayoutParams(para);

        imageViewList = new ArrayList<ImageView>();
        picList = new ArrayList<>();
        picList.add(R.drawable.img_banner1);
        picList.add(R.drawable.img_banner1);
        picList.add(R.drawable.img_banner1);
        for (int i = 0; i < picList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageViewList.add(imageView);
            //TODO 为imageView加载网络图片
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable(getResources().getDrawable(picList.get(i)));
        }
        points = new ArrayList<View>();
        for (int i = 0; i < picList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 8, 8, 8);
            imageView.setLayoutParams(layoutParams);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.point_select);
            } else {
                imageView.setBackgroundResource(R.drawable.point_select);
            }
            ll_points.addView(imageView);
            points.add(imageView);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());

        //监听ScrollView下滑高度
        sv_home.setOnScrollChangedListener(new ScrollChangedListener() {
            @Override
            public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
                int height = para.height;
                if (t < height) {

                } else {

                }
            }
        });

        /**附近商家*/
        goodsDatas = new ArrayList<>();
        mRecGoodsAdapter = new RecGoodsAdapter(getActivity(), R.layout.item_rec_goods);
        lv_rec_goods.setAdapter(mRecGoodsAdapter);
        lv_rec_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
//                intent.putExtra("goods_data", goodsDatas.get(i));
//                startActivity(intent);
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopRefresh();
            }
        }, 1000);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                setGrade(3.9);

                long time = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                    RecGoodsBean bean = new RecGoodsBean("", "订单：" + i, TimeUtils.millis2String(time), (i + 100) + "", (i + 100) + "");
                    goodsDatas.add(bean);
                }
//                mRecGoodsAdapter.setData(goodsDatas);
//                mRecGoodsAdapter.notifyDataSetChanged();
//                mRefreshloadmore.stopRefresh();
            }
        }, 1000);
    }

    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 每五秒钟切换一次轮播图图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 10, 10, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 当页面不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopRefresh();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshloadmore.stopLoadMore();
            }
        }, 1000);
    }

    private class ScrollTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % picList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    /**
     * 设置商店评分等级
     *
     * @param grade
     */
    public void setGrade(Double grade) {
        for (int i = 0; i < 5; i++) {
            ImageView view = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 10, 0);
            view.setLayoutParams(layoutParams);
            if (i < (int) Math.floor(grade)) {
                view.setImageResource(R.drawable.icon_star_full);
            } else {
                view.setImageResource(R.drawable.icon_star_empty);
            }
            ll_star.addView(view);
        }
        tv_grade.setText(grade + "分");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_address:
////                if (YiyeApplication.locationListBean != null) {
////                    startActivityForResult(new Intent(getActivity(), LocationListActivity.class), REQUEST_LOCATION);
////                } else {
////                    Toast.makeText(getActivity(), "请先打开定位权限", Toast.LENGTH_LONG).show();
////                }
//                break;
//            case R.id.tv_search:
////                startActivity(new Intent(getActivity(), SearchActivity.class));
////                getActivity().overridePendingTransition(R.anim.activity_open, 0);
//                break;
//            case R.id.tv_shops_rec:
////                startActivity(new Intent(getActivity(), ShopsRecActivity.class));
//                Toast.makeText(getActivity(), "商家推荐", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_lianqu_store:
//                Toast.makeText(getActivity(), "连趣商城", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_play_game:
//                Toast.makeText(getActivity(), "玩玩游戏", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_live:
//                Toast.makeText(getActivity(), "看看直播", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_eat:
//                Toast.makeText(getActivity(), "美食", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_drink:
//                Toast.makeText(getActivity(), "饮品", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_play:
//                Toast.makeText(getActivity(), "玩味", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_happy:
//                Toast.makeText(getActivity(), "娱乐", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onLocationState(LocationStateEvent locationStateEvent) {
//        switch (locationStateEvent.getState()) {
//            case LocationStateEvent.SUCCESS:
//                tv_address.setText(locationStateEvent.getLocationListBean().getCity());
//                break;
//            case LocationStateEvent.FAIL:
//                tv_address.setText("定位中..");
//                break;
//            default:
//                break;
//        }
//    }


//    private void startPermissionsActivity() {
//        PermissionsActivity.startActivityForResult(getActivity(), REQUEST_CODE, PERMISSIONS);
//        getActivity().overridePendingTransition(R.anim.activity_open, 0);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
//        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
//            Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
//        } else if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
//            if (data != null) {
//                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                Toast.makeText(getActivity(), "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == REQUEST_LOCATION && data != null) {
//            LocationListBean locationListBean = (LocationListBean) data.getSerializableExtra("LocationListBean");
//            tv_address.setText(locationListBean.getCity());
//            Toast.makeText(getActivity(), locationListBean.getCity(), Toast.LENGTH_LONG).show();
//        }
//    }

    /**
     * 自定义pageradapter  适配viewpager
     */
    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        /**
         * 根据指定的下标 创建viewpager中展示的item  返回当前page中的view对象
         * 第一个参数表示 当前管理page的视图组
         * 第二个参数表示 指定下标
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ((ViewPager) container).addView(imageViewList.get(position), 0);
            return imageViewList.get(position);

        }

        /**
         * 根据指定的下标移除视图组中的view对象
         * 第一个参数表示 视图组对象
         * 第二个参数表示 当前移除的视图的下标
         * 第三个参数表示 instantiateItem 返回的object对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewList.get(position));
        }

        /**
         * 表示判断viewpager中展示的view对象与instantiateItem对象是否时同一个对象
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author xieqinghua
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            setImageBackground(position);
        }

        /**
         * 设置选中的圆点的背景
         *
         * @param selectItems
         */
        private void setImageBackground(int selectItems) {
            for (int i = 0; i < points.size(); i++) {
                if (i == selectItems) {
                    points.get(i).setBackgroundResource(R.drawable.point_select);
                } else {
                    points.get(i).setBackgroundResource(R.drawable.point_normal);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }
}
