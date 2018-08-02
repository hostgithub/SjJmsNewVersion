package com.gdtc.sjjms.fragment;

import android.os.Bundle;
import android.view.View;

import com.gdtc.sjjms.R;
import com.gdtc.sjjms.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeTestFragment extends BaseFragment {
    private static final String TAG = HomeTestFragment.class.getSimpleName();

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

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
