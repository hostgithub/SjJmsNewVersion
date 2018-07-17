package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-7-17.
 */

public class NearbySellerBean implements Serializable {


    /**
     * results : [{"BusinessInfoId":"c0a1818d-d856-4229-aa03-787b797b65f1","BusinessName":"青藤小院烧烤","BusinessTitleImage":"/html/file/businessinfo/images/businessinfo_admin_1_15317034938370.jpg","BusinessAbbreviation":"特色烤鱿鱼，望京小腰，铁板皮皮虾","BusinessIntroduction":"特色烧烤，进口啤酒","BusinessStory":"","BusinessService":"吸烟区，宝宝椅","StartHours":"2018-07-16 00:00:00","EndHours":"2018-07-16 00:00:00","BusinessAddress":"向阳区一道街1-1号","BusinessPhone":"13888889999","BusinessPeople":"郭某人","IsCharacteristic":"1","BusinessNotice":"提供特价菜品","BusinessActivity":"9.8折","Category":"烤鱿鱼,"},{"BusinessInfoId":"0bf5f26a-3e3d-44a4-963c-6506f85111e5","BusinessName":"测试","BusinessTitleImage":"/html/file/businessinfo/images/businessinfo_admin_1_15317324945170.png","BusinessAbbreviation":"测试","BusinessIntroduction":"测试","BusinessStory":"测试","BusinessService":"测试","StartHours":"2018-07-16 00:00:00","EndHours":"2018-07-16 00:00:00","BusinessAddress":"测试","BusinessPhone":"111111111111","BusinessPeople":"测试","IsCharacteristic":"1","BusinessNotice":"测试","BusinessActivity":"测试","Category":"佳木斯土特产,佳木斯木耳,"}]
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
         * BusinessInfoId : c0a1818d-d856-4229-aa03-787b797b65f1
         * BusinessName : 青藤小院烧烤
         * BusinessTitleImage : /html/file/businessinfo/images/businessinfo_admin_1_15317034938370.jpg
         * BusinessAbbreviation : 特色烤鱿鱼，望京小腰，铁板皮皮虾
         * BusinessIntroduction : 特色烧烤，进口啤酒
         * BusinessStory :
         * BusinessService : 吸烟区，宝宝椅
         * StartHours : 2018-07-16 00:00:00
         * EndHours : 2018-07-16 00:00:00
         * BusinessAddress : 向阳区一道街1-1号
         * BusinessPhone : 13888889999
         * BusinessPeople : 郭某人
         * IsCharacteristic : 1
         * BusinessNotice : 提供特价菜品
         * BusinessActivity : 9.8折
         * Category : 烤鱿鱼,
         */

        private String BusinessInfoId;
        private String BusinessName;
        private String BusinessTitleImage;
        private String BusinessAbbreviation;
        private String BusinessIntroduction;
        private String BusinessStory;
        private String BusinessService;
        private String StartHours;
        private String EndHours;
        private String BusinessAddress;
        private String BusinessPhone;
        private String BusinessPeople;
        private String IsCharacteristic;
        private String BusinessNotice;
        private String BusinessActivity;
        private String Category;

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

        public String getBusinessAbbreviation() {
            return BusinessAbbreviation;
        }

        public void setBusinessAbbreviation(String BusinessAbbreviation) {
            this.BusinessAbbreviation = BusinessAbbreviation;
        }

        public String getBusinessIntroduction() {
            return BusinessIntroduction;
        }

        public void setBusinessIntroduction(String BusinessIntroduction) {
            this.BusinessIntroduction = BusinessIntroduction;
        }

        public String getBusinessStory() {
            return BusinessStory;
        }

        public void setBusinessStory(String BusinessStory) {
            this.BusinessStory = BusinessStory;
        }

        public String getBusinessService() {
            return BusinessService;
        }

        public void setBusinessService(String BusinessService) {
            this.BusinessService = BusinessService;
        }

        public String getStartHours() {
            return StartHours;
        }

        public void setStartHours(String StartHours) {
            this.StartHours = StartHours;
        }

        public String getEndHours() {
            return EndHours;
        }

        public void setEndHours(String EndHours) {
            this.EndHours = EndHours;
        }

        public String getBusinessAddress() {
            return BusinessAddress;
        }

        public void setBusinessAddress(String BusinessAddress) {
            this.BusinessAddress = BusinessAddress;
        }

        public String getBusinessPhone() {
            return BusinessPhone;
        }

        public void setBusinessPhone(String BusinessPhone) {
            this.BusinessPhone = BusinessPhone;
        }

        public String getBusinessPeople() {
            return BusinessPeople;
        }

        public void setBusinessPeople(String BusinessPeople) {
            this.BusinessPeople = BusinessPeople;
        }

        public String getIsCharacteristic() {
            return IsCharacteristic;
        }

        public void setIsCharacteristic(String IsCharacteristic) {
            this.IsCharacteristic = IsCharacteristic;
        }

        public String getBusinessNotice() {
            return BusinessNotice;
        }

        public void setBusinessNotice(String BusinessNotice) {
            this.BusinessNotice = BusinessNotice;
        }

        public String getBusinessActivity() {
            return BusinessActivity;
        }

        public void setBusinessActivity(String BusinessActivity) {
            this.BusinessActivity = BusinessActivity;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }
    }
}
