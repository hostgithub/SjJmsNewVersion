package com.gdtc.sjjms.fragment;

import android.os.Bundle;
import android.view.View;

import com.gdtc.sjjms.MyApplication;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseFragment;
import com.gdtc.sjjms.impl.ActionBarClickListener;
import com.gdtc.sjjms.utils.SharePreferenceTools;
import com.gdtc.sjjms.widget.TranslucentActionBar;
import com.gdtc.sjjms.widget.TranslucentScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        sp = new SharePreferenceTools(MyApplication.getContext());
        init();
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
        translucentScrollView.setTransColor(getResources().getColor(R.color.snow));

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

//    @OnClick({R.id.updataPasswordRlt,R.id.clear,R.id.logOutRlt})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.updataPasswordRlt:
//               Toast.makeText(getContext(),"已是最新版本",Toast.LENGTH_SHORT).show();
//                break;
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
//            default:
//                break;
//        }
//    }
}
