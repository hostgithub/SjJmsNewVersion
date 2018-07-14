package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class Banners implements Serializable
{

    /**
     * results : [{"_id":"17753","url":"/html/index/content/2013/04/images/news_113653214520300.jpg","title":"日出"},{"_id":"17499","url":"/html/index/content/2013/03/images/news_113642826610310.jpg","title":"五彩秋色"},{"_id":"17497","url":"/html/index/content/2013/03/images/news_113642824455310.jpg","title":"佳木斯敖其湾风光"},{"_id":"17492","url":"/html/index/content/2013/03/images/news_113642818986250.JPG","title":"佳木斯宾馆"},{"_id":"17489","url":"/html/index/content/2013/03/images/news_113642817385310.jpg","title":"工人文化宫"},{"_id":"17488","url":"/html/index/content/2013/03/images/news_113642816146250.jpg","title":"佳木斯第一中学"}]
     * success : true
     */

    public String success;
    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        /**
         * _id : 17753
         * url : /html/index/content/2013/04/images/news_113653214520300.jpg
         * title : 日出
         */

        private String _id;
        private String url;
        private String title;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
