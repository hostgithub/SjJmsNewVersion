package com.gdtc.sjjms.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdtc.sjjms.ConstantValue;
import com.gdtc.sjjms.MyApplication;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.WeiXinActivity;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.event.MessageEvent;
import com.gdtc.sjjms.impl.ActionBarClickListener;
import com.gdtc.sjjms.ui.MineCollectActivity;
import com.gdtc.sjjms.ui.MineZujiActivity;
import com.gdtc.sjjms.ui.SystemSettingActivity;
import com.gdtc.sjjms.utils.MyBitmapUtils;
import com.gdtc.sjjms.utils.MyLogUtils;
import com.gdtc.sjjms.utils.MyToastUtils;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.gdtc.sjjms.widget.GlideCircleTransform;
import com.gdtc.sjjms.widget.NiceImageView;
import com.gdtc.sjjms.widget.TranslucentActionBar;
import com.gdtc.sjjms.widget.TranslucentScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wangjiawei on 2017-11-13.
 */

public class MineFragmentTest extends BaseFragment implements ActionBarClickListener, TranslucentScrollView.TranslucentChangedListener{

    private Unbinder mUnbinder;

    private SharePreferenceTools sp;
    @BindView(R.id.pullzoom_scrollview)
     TranslucentScrollView translucentScrollView;
    @BindView(R.id.actionbar)
     TranslucentActionBar actionBar;
    @BindView(R.id.lay_header)
     View zoomView;

    @BindView(R.id.img_avatar)
    NiceImageView img_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_zuji)
    LinearLayout ll_zuji;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;

    public static final int PHOTOZOOM = 0;
    public static final int IMAGE_COMPLETE = 2; // 结果


    public static final int WEIXIN_RESULT_CODE = 5; // 结果

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        sp = new SharePreferenceTools(MyApplication.getContext());
        init();

        if(sp.getString(ConstantValue.WEIXIN_NICKNAME)==null){
            tv_name.setText("用户登录");
            img_avatar.setImageResource(R.drawable.unlogin);
        }else {
            tv_name.setText(sp.getString(ConstantValue.WEIXIN_NICKNAME));
            Glide.with(getActivity()).load(sp.getString(ConstantValue.WEIXIN_HEADURL)).into(img_avatar);
        }
    }


    private void init() {
//        actionBar = (TranslucentActionBar) findViewById(R.id.actionbar);
        //初始actionBar
        actionBar.setData("我的", 0, null, 0, null, null);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(20);

//        translucentScrollView = (TranslucentScrollView) findViewById(R.id.pullzoom_scrollview);
        //设置透明度变化监听
        translucentScrollView.setTranslucentChangedListener(this);
        //关联需要渐变的视图
        translucentScrollView.setTransView(actionBar);
        //设置ActionBar键渐变颜色
//        translucentScrollView.setTransColor(getResources().getColor(R.color.orange_dft));
        translucentScrollView.setTransColor(getResources().getColor(R.color.white));

//        zoomView = findViewById(R.id.lay_header);
        //关联伸缩的视图
        translucentScrollView.setPullZoomView(zoomView);
    }


    @Override
    public void initLoadData() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onTranslucentChanged(int transAlpha) {
        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.img_avatar,R.id.tv_name,R.id.ll_collect,R.id.ll_setting,R.id.ll_zuji})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_avatar:
                if(sp.getString(ConstantValue.WEIXIN_OPENID)==null){
                    Intent weixin=new Intent(getContext(), WeiXinActivity.class);
                    startActivityForResult(weixin,WEIXIN_RESULT_CODE);
                }else {
                    //headIconDialog();
                }
                break;
            case R.id.tv_name:

                break;
            case R.id.ll_collect:
                if(sp.getString(ConstantValue.WEIXIN_OPENID)==null){
                    Intent weixin=new Intent(getContext(), WeiXinActivity.class);
                    startActivityForResult(weixin,WEIXIN_RESULT_CODE);
                }else{
                   startActivity(new Intent(getContext(), MineCollectActivity.class));
                }
                break;
            case R.id.ll_zuji:
                if(sp.getString(ConstantValue.WEIXIN_OPENID)==null){
                    Intent weixin=new Intent(getContext(), WeiXinActivity.class);
                    startActivityForResult(weixin,WEIXIN_RESULT_CODE);
                }else{
                    startActivity(new Intent(getContext(), MineZujiActivity.class));
                }
                break;
            case R.id.ll_setting:
                startActivity(new Intent(getContext(), SystemSettingActivity.class));
                break;
//            case R.id.clear:
//                try {
//                    DataCleanManagerUtils.clearAllCache(getActivity());
//                    Toast.makeText(getActivity(),"清除缓存成功",Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.logOutRlt://退出登录
//                Toast.makeText(getActivity(),"退出登录",Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//                builder.setTitle("退出登录");//设置对话框的标题
//                builder.setMessage("退出登录将前往登录界面,确定退出登录吗");//设置对话框的内容
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
////                        sp.putBoolean("main",false);
////                        startActivity(new Intent(getActivity(),LoginTestActivity.class));
////                        getActivity().finish();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //这个是设置确定按钮
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                AlertDialog b=builder.create();
//                b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
//                break;
            default:
                break;
        }
    }
    /**
     * 打开系统相册
     */
    private void headIconDialog() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(openAlbumIntent, PHOTOZOOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        Intent intent = null;
        switch (requestCode) {
            case PHOTOZOOM:// 相册
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null) {
                    return;
                }
                uri = data.getData();
                Bitmap userbitmap = MyBitmapUtils.decodeUriAsBitmap(getContext(), uri);
                if (userbitmap == null) {//这里用于校验图片是否有误（可能是破损图）
                    MyToastUtils.showShortToast(getContext(), "图片有误，请重新选择！");
                    return;
                }
                File user_head = MyBitmapUtils.saveBitmap(MyBitmapUtils.zoomImgKeepWH(userbitmap, 400, 400, true), "user_head.jpeg");

                //img_avatar.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + "user_head.jpeg"));
                Glide.with(getContext())
                        .load(user_head)
                        .transform(new GlideCircleTransform(getContext()))
                        .into(img_avatar);

//                intent = new Intent(getContext(), HomePageActivity.class);
//                intent.putExtra("path", Environment.getExternalStorageDirectory() + "/" + "user_head.jpeg");
                MyLogUtils.info("拍照图片地址是：" + Environment.getExternalStorageDirectory() + "/" + "user_head.jpeg");
//                startActivityForResult(intent, IMAGE_COMPLETE);
                break;
            case IMAGE_COMPLETE:// 完成
                if (data != null) {
                    String temppath = data.getStringExtra("path");
//                    toloadfile(temppath);//这里上传头像到后台接口
                    MyLogUtils.info(data + "裁剪完成地址。。。。");
                }

                break;
            case WEIXIN_RESULT_CODE:// 微信登录成功后刷新该界面
                if(sp.getString(ConstantValue.WEIXIN_NICKNAME)==null){
                    tv_name.setText("用户登录");
                    img_avatar.setImageResource(R.drawable.unlogin);
                }else {
                    tv_name.setText(sp.getString(ConstantValue.WEIXIN_NICKNAME));
                    Glide.with(getActivity()).load(sp.getString(ConstantValue.WEIXIN_HEADURL)).into(img_avatar);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event){
        Log.e("onEventBackground收到了消息",event.toString());
        if(sp.getString(ConstantValue.WEIXIN_NICKNAME)==null){
            tv_name.setText("用户登录");
            img_avatar.setImageResource(R.drawable.unlogin);
        }else {
            tv_name.setText(sp.getString(ConstantValue.WEIXIN_NICKNAME));
            Glide.with(getActivity()).load(sp.getString(ConstantValue.WEIXIN_HEADURL)).into(img_avatar);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
