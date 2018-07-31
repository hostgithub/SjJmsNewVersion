package com.gdtc.sjjms.bean;

import java.util.List;

/**
 * Created by wangjiawei on 2018-7-25.
 */

public class CommentList {


    /**
     * results : [{"Name":"22222555555","Evaluate":"阿伦无聊佛祖","AddTime":"2018-07-25"},{"Name":"22222555555","Evaluate":"阿伦无聊佛祖恩率","AddTime":"2018-07-25"},{"Name":"523s","Evaluate":"为什么有bug","AddTime":"2018-07-25"},{"Name":"523s","Evaluate":"再给你来条士力架","AddTime":"2018-07-25"}]
     * success : true
     */

    private String success;
    private List<ResultsBean> results;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * Name : 22222555555
         * Evaluate : 阿伦无聊佛祖
         * AddTime : 2018-07-25
         */

        private String Name;
        private String Evaluate;
        private String AddTime;
        private String Image;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getEvaluate() {
            return Evaluate;
        }

        public void setEvaluate(String Evaluate) {
            this.Evaluate = Evaluate;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }
    }
}
