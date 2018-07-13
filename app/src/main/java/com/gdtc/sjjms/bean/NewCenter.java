package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2017-9-21.
 */

public class NewCenter implements Serializable{

    private boolean success;
    public List<ResultsBean> results;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class ResultsBean implements Serializable{

        public String title;
        public String _id;
        public String picName;
        public String addTime;
        public String text;
        private int height;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }
}
