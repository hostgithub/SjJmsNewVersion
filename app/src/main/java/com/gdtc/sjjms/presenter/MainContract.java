package com.gdtc.sjjms.presenter;


import com.gdtc.sjjms.base.BasePresenter;
import com.gdtc.sjjms.base.BaseView;
import com.gdtc.sjjms.bean.ApplicationEntity;

/**
 * Created by wangjiawei on 2017/7/18.
 */

public interface MainContract {
    interface View extends BaseView {
        void retureResult(String result);
        void retureUpdateResult(ApplicationEntity entity);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
        void update(ApplicationEntity entity);
    }
}
