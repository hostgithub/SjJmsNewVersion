package com.gdtc.sjjms.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.gdtc.sjjms.Config;
import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.WeiXinActivity;
import com.gdtc.sjjms.adapter.CommentListAdapter;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.Collect;
import com.gdtc.sjjms.bean.Comment;
import com.gdtc.sjjms.bean.CommentList;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.service.Api;
import com.gdtc.sjjms.utils.RetrofitUtils;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearSellerActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.seller_image)
    ImageView seller_image;
    @BindView(R.id.iv_tuijian)
    ImageView iv_tuijian;
    @BindView(R.id.iv_coll)//收藏
    ImageView iv_coll;
    @BindView(R.id.al_comment)//点评
     AutoRelativeLayout al_comment;
    private PopupWindow mPopWindow;

    @BindView(R.id.seller_name)
    TextView seller_name;
    @BindView(R.id.seller_price)
    TextView seller_price;
    @BindView(R.id.seller_kind)
    TextView seller_kind;
    @BindView(R.id.seller_location)
    TextView seller_location;
    @BindView(R.id.tv_service_time)
    TextView tv_service_time;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_tel_phone)
    TextView tv_tel_phone;

    @BindView(R.id.seller_translate)
    TextView seller_translate;
    @BindView(R.id.seller_stroy)
    TextView seller_stroy;
    @BindView(R.id.zhaopai_1)
    TextView zhaopai_1;
    @BindView(R.id.zhaopai_2)
    TextView zhaopai_2;
    @BindView(R.id.al_tuijian)
    AutoRelativeLayout al_tuijian;

    private String type_star;
    private SharePreferenceTools sp;
    private NearbySellerDetailBean.ResultsBean nearbySellerDetailBean;


    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CommentList.ResultsBean> list;
    private CommentListAdapter nearbyAdapter;
    private int pages=1;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_near_seller;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        sp=new SharePreferenceTools(getApplicationContext());

        Intent intent=getIntent();
        nearbySellerDetailBean= (NearbySellerDetailBean.ResultsBean) intent.getSerializableExtra(Config.NEWS);


        if(nearbySellerDetailBean.getType().equals("0")){
            iv_coll.setImageResource(R.mipmap.basecs_uncollect);
        }else {
            iv_coll.setImageResource(R.mipmap.basecs_collected);
        }
        Glide.with(NearSellerActivity.this).load(nearbySellerDetailBean.getBusinessTitleImage()).into(seller_image);
        Log.e("--getBusinessTitleImage",nearbySellerDetailBean.getBusinessTitleImage());
        Log.e("----getBusinessInfoId--",nearbySellerDetailBean.getBusinessInfoId());
        seller_name.setText(nearbySellerDetailBean.getBusinessName());
        seller_price.setText("人均:￥"+nearbySellerDetailBean.getConsumption()+"/人");
        seller_kind.setText(nearbySellerDetailBean.getCategory().substring(0,nearbySellerDetailBean.getCategory().length() - 1));
        tv_service_time.setText("营业时间 "+nearbySellerDetailBean.getStartHours()+"--"+nearbySellerDetailBean.getEndHours());
        tv_address.setText(nearbySellerDetailBean.getBusinessAddress());

        tv_tel_phone.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        tv_tel_phone.setText(nearbySellerDetailBean.getBusinessPhone());

        seller_translate.setText(nearbySellerDetailBean.getBusinessIntroduction());
        seller_stroy.setText(nearbySellerDetailBean.getBusinessStory());

        type_star=nearbySellerDetailBean.getType();



        list=new ArrayList();
        initCommentListData(1,nearbySellerDetailBean.getBusinessInfoId());
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setHasFixedSize(true);
        xrecyclerview.setNestedScrollingEnabled(false);
        linearLayoutManager.setAutoMeasureEnabled(true);


        nearbyAdapter=new CommentListAdapter(this,list);

        xrecyclerview.setAdapter(nearbyAdapter);

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                list.clear();
                nearbyAdapter.notifyDataSetChanged();
                initCommentListData(1,nearbySellerDetailBean.getBusinessInfoId());
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                pages++;
                initCommentListData(pages,nearbySellerDetailBean.getBusinessInfoId());
                xrecyclerview.loadMoreComplete();
            }
        });

    }



    private void initCommentListData(int pages,String id) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(RetrofitUtils.getInstance().addTimeOut(30).addHttpLog().build())  //构建自己的OkHttpClient
                .build();
        Api api =retrofit.create(Api.class);
        Call<CommentList> call=api.getCommentListData(pages,id);
        call.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {

                if(response.body().getResults().size()==0){
                    tip.setVisibility(View.VISIBLE);
                }else{
                    tip.setVisibility(View.GONE);
                    list.clear();
                    list.addAll(response.body().getResults());
                    Log.e("xxxxxx",response.body().toString());
                    nearbyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                Toast.makeText(NearSellerActivity.this,R.string.failure_tip,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({ R.id.tv_back,R.id.al_tuijian,R.id.tv_tel_phone,R.id.iv_coll,R.id.al_comment})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                NearSellerActivity.this.overridePendingTransition(0, R.anim.activity_close);
                break;
            case R.id.al_tuijian://点击进入推荐菜列表
                Intent intent=new Intent(NearSellerActivity.this,TuijianListActivity.class);
                intent.putExtra(Config.NEWS,nearbySellerDetailBean.getBusinessInfoId());
                startActivity(intent);
                break;
            case R.id.tv_tel_phone://点击进入推荐菜列表
                callPhone(tv_tel_phone.getText().toString());
                break;
            case R.id.iv_coll://
                if(sp.getString(ConstantValue.WEIXIN_OPENID)!=null){
                    if(type_star.equals("0")){
                        getCollect(nearbySellerDetailBean.getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID),
                                sp.getString(ConstantValue.WEIXIN_NICKNAME),"0");
                    } else{
                        getCollect(nearbySellerDetailBean.getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID),
                                sp.getString(ConstantValue.WEIXIN_NICKNAME),"1");
                    }
                } else {
                    startActivity(new Intent(NearSellerActivity.this, WeiXinActivity.class));
                }
                break;
            case R.id.al_comment://
                if(sp.getString(ConstantValue.WEIXIN_OPENID)!=null){
                    showPopupWindow();
                } else {
                    startActivity(new Intent(NearSellerActivity.this, WeiXinActivity.class));
                }
//                showPopupWindow();
                break;
            default:
                break;
        }
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    /**
     * 收藏
     * @param openId
     */
    private void getCollect(String businessId, String openId, String name, final String type) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Collect> call=api.getCollectData(businessId,openId,name,type);
        call.enqueue(new Callback<Collect>() {
            @Override
            public void onResponse(Call<Collect> call, Response<Collect> response) {
                Log.e("------------------",response.body().toString());
               if(response.body().getInformation().equals("1")){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           iv_coll.setImageResource(R.mipmap.basecs_collected);
                           type_star="1";
                           Toast.makeText(NearSellerActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                       }
                   });
               }else {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           iv_coll.setImageResource(R.mipmap.basecs_uncollect);
                           type_star="0";
                           Toast.makeText(NearSellerActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                       }
                   });
               }
            }

            @Override
            public void onFailure(Call<Collect> call, Throwable t) {
                Toast.makeText(NearSellerActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(NearSellerActivity.this).inflate(R.layout.popup_comment, null);
//        mPopWindow = new PopupWindow(contentView,
//                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, 100, true);
        mPopWindow.setContentView(contentView);
        //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        //添加pop窗口关闭事件
        mPopWindow.setOnDismissListener(new poponDismissListener());
        //设置软键盘弹出
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);//这里给它设置了弹出的时间
        //设置各个控件的点击响应
        final EditText editText = contentView.findViewById(R.id.pop_editText);
        TextView btn = contentView.findViewById(R.id.pop_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String inputString = editText.getText().toString();
                if(!EmptyUtils.isEmpty(editText.getText().toString().trim())){
                    getComment(nearbySellerDetailBean.getBusinessInfoId(),sp.getString(ConstantValue.WEIXIN_OPENID),
                            sp.getString(ConstantValue.WEIXIN_NICKNAME),editText.getText().toString().trim());
//                    getComment(nearbySellerDetailBean.getBusinessInfoId(),"222222222",
//                            "22222555555",editText.getText().toString().trim());
                }else {
                    Toast.makeText(NearSellerActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //是否具有获取焦点的能力
        mPopWindow.setFocusable(true);
        //显示PopupWindow
        //View rootview = LayoutInflater.from(NearSellerActivity.this).inflate(R.layout.activity_near_seller, null);
        mPopWindow.showAtLocation(al_comment, Gravity.BOTTOM, 0, 0);
    }



    /**
     * 收藏
     * @param openId
     */
    private void getComment(String businessId, String openId, String name, String content) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Comment> call=api.getCommentData(businessId,openId,name,content);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.body().getSuccess().equals("true")){
                    initCommentListData(1,nearbySellerDetailBean.getBusinessInfoId());
                    mPopWindow.dismiss();//让PopupWindow消失
                    Toast.makeText(NearSellerActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(NearSellerActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(xrecyclerview != null){
            xrecyclerview.destroy(); // this will totally release XR's memory
            xrecyclerview = null;
        }
    }
}
