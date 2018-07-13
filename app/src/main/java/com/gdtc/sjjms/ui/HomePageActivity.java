package com.gdtc.sjjms.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gdtc.sjjms.App;
import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseActivity;
import com.gdtc.sjjms.bean.ApplicationEntity;
import com.gdtc.sjjms.fragment.HomeFragment;
import com.gdtc.sjjms.fragment.MineFragmentTest;
import com.gdtc.sjjms.fragment.RemenFragment;
import com.gdtc.sjjms.presenter.MainContract;
import com.gdtc.sjjms.presenter.MainPresenter;
import com.gdtc.sjjms.utils.AppInfoUtil;
import com.gdtc.sjjms.utils.LogUtil;
import com.gdtc.sjjms.utils.StatusBarUtil;
import com.gdtc.sjjms.utils.UpdateDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageActivity extends BaseActivity implements MainContract.View{

    @BindView(R.id.rg_menu)
    RadioGroup rg_menu;
    @BindView(R.id.btn_first)
    RadioButton btn_first;
    @BindView(R.id.btn_second)
    RadioButton btn_second;
    @BindView(R.id.btn_third)
    RadioButton btn_third;
    @BindView(R.id.btn_four)
    RadioButton btn_four;

    private MainContract.Presenter mPresenter;

    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private FragmentManager fragmentManager;
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isExit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //setStatusBar();
        mPresenter = new MainPresenter(this);


        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));
            fragments.add(fragmentManager.findFragmentByTag(3+""));
            //恢复fragment页面
            restoreFragment();

        }else{      //正常启动时调用
            btn_first.setSelected(true);
            fragments.add(new HomeFragment());
            fragments.add(new HomeFragment());
            fragments.add(new RemenFragment());
            fragments.add(new MineFragmentTest());
            showFragment();
        }

        mPresenter.checkUpdate("http://www.jms.gov.cn/app/update.json");
    }

    @OnClick({ R.id.btn_first,R.id.btn_second,R.id.btn_third,R.id.btn_four})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_first:
//                btn_first.setSelected(true);
//                btn_first.setTextColor(Color.parseColor("#ff1b99e6"));
//                btn_second.setSelected(false);
//                btn_second.setTextColor(Color.GRAY);
//                btn_third.setSelected(false);
//                btn_third.setTextColor(Color.GRAY);
//                btn_four.setSelected(false);
//                btn_four.setTextColor(Color.GRAY);
                currentIndex = 0;
                showFragment();
                break;
            case R.id.btn_second:
//                btn_first.setSelected(false);
//                btn_first.setTextColor(Color.GRAY);
//                btn_second.setSelected(true);
//                btn_second.setTextColor(Color.parseColor("#ff1b99e6"));
//                btn_third.setSelected(false);
//                btn_third.setTextColor(Color.GRAY);
//                btn_four.setSelected(false);
//                btn_four.setTextColor(Color.GRAY);
                currentIndex = 1;
                showFragment();
                break;
            case R.id.btn_third://
//                btn_first.setSelected(false);
//                btn_first.setTextColor(Color.GRAY);
//                btn_second.setSelected(false);
//                btn_second.setTextColor(Color.GRAY);
//                btn_third.setSelected(true);
//                btn_third.setTextColor(Color.parseColor("#ff1b99e6"));
//                btn_four.setSelected(false);
//                btn_four.setTextColor(Color.GRAY);
                currentIndex = 2;
                showFragment();
                break;
            case R.id.btn_four://
//                btn_first.setSelected(false);
//                btn_first.setTextColor(Color.GRAY);
//                btn_second.setSelected(false);
//                btn_second.setTextColor(Color.GRAY);
//                btn_third.setSelected(false);
//                btn_third.setTextColor(Color.GRAY);
//                btn_four.setSelected(true);
//                btn_four.setTextColor(Color.parseColor("#ff1b99e6"));
                currentIndex = 3;
                showFragment();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    protected void setStatusBar() {

        StatusBarUtil.setColor(this,
                getResources().getColor(R.color.white_snow), 1);
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.content,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag
        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }
        currentFragment = fragments.get(currentIndex);
        transaction.commit();
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }
        }
        mBeginTreansaction.commit();
        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit=null;
        if(!isExit){
            isExit=true;
            Toast.makeText(HomePageActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit=new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }else{
            //finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void retureResult(String result) {

    }

    @Override
    public void retureUpdateResult(final ApplicationEntity entity) {
        try {
            if (AppInfoUtil.getVersionCode(App.application) < Integer.parseInt(entity.getVersion())) {
                String content = String.format("最新版本：%1$s\napp名字：%2$s\n\n更新内容\n%3$s", entity.getVersionShort(), entity.getName(), entity.getChangelog());
                UpdateDialog.show(HomePageActivity.this, content, new UpdateDialog.OnUpdate() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        mPresenter.update(entity);
                    }
                });
            }
        } catch (Exception e) {
            LogUtil.d("数字转化出错");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
