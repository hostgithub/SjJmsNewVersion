package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-7-17.
 */

public class AreaTwoBean implements Serializable {


    /**
     * results : [{"regionalId":"e2540d1f-b437-4053-a8a7-e5b59d12ceac","regionalStreet":"东风区"},{"regionalId":"0725df3b-d7b0-4ee5-883b-860fbdc203c0","regionalStreet":"前进区"},{"regionalId":"a36d4e09-85f1-46b4-a1ae-8077b112755d","regionalStreet":"郊区"},{"regionalId":"36bf23c7-e675-4d1e-98a2-ff2d3838e71b","regionalStreet":"向阳区"}]
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

    public static class ResultsBean implements Serializable{
        /**
         * regionalId : e2540d1f-b437-4053-a8a7-e5b59d12ceac
         * regionalStreet : 东风区
         */

        private String regionalId;
        private String regionalStreet;

        public String getRegionalId() {
            return regionalId;
        }

        public void setRegionalId(String regionalId) {
            this.regionalId = regionalId;
        }

        public String getRegionalStreet() {
            return regionalStreet;
        }

        public void setRegionalStreet(String regionalStreet) {
            this.regionalStreet = regionalStreet;
        }
    }
}
