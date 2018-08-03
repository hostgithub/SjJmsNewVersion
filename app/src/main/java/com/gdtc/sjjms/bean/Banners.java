package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class Banners implements Serializable
{

    /**
     * results : [{"BusinessInfoId":"fe25fffd-83db-4453-af01-48ded1cc1c71","BusinessName":"测试","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331891198460.jpg"},{"BusinessInfoId":"922fb02b-421c-43d0-ab2b-696620f980fe","BusinessName":"汉庭酒店","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331890755240.jpg"},{"BusinessInfoId":"2bf8394c-bcff-41e7-9db5-95357b21827f","BusinessName":"携程","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331891805180.jpg"},{"BusinessInfoId":"6882feff-b990-40d7-a815-c7fad1f04ac3","BusinessName":"七天快捷宾馆","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331890323030.jpg"},{"BusinessInfoId":"10661652-00d1-4285-bd18-6f2accf4cb1d","BusinessName":"扒虾小妹","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331892526030.jpg"}]
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
         * BusinessInfoId : fe25fffd-83db-4453-af01-48ded1cc1c71
         * BusinessName : 测试
         * BusinessTitleImage : http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15331891198460.jpg
         */

        private String BusinessInfoId;
        private String BusinessName;
        private String BusinessTitleImage;

        public String getBusinessInfoId() {
            return BusinessInfoId;
        }

        public void setBusinessInfoId(String BusinessInfoId) {
            this.BusinessInfoId = BusinessInfoId;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String BusinessName) {
            this.BusinessName = BusinessName;
        }

        public String getBusinessTitleImage() {
            return BusinessTitleImage;
        }

        public void setBusinessTitleImage(String BusinessTitleImage) {
            this.BusinessTitleImage = BusinessTitleImage;
        }
    }
}
