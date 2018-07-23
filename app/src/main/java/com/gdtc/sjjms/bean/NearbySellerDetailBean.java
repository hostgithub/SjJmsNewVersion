package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-7-18.
 */

public class NearbySellerDetailBean implements Serializable {


    /**
     * results : [{"BusinessInfoId":"234321a7-b578-4e9a-8f84-7977caf7bd7f","BusinessName":"青藤小院烧烤","Score":"null","BusinessTitleImage":"/html/file/businessinfo/images/businessinfo_admin_1_15318169045030.png","BusinessAbbreviation":"商家简称","BusinessIntroduction":"商家介绍","BusinessStory":"品牌故事","BusinessService":"商家所提供的服务","StartHours":"2018-07-17 00:00:00","EndHours":"2018-07-17 00:00:00","BusinessAddress":"1-222","BusinessPhone":"13988776655","BusinessPeople":"比某某","IsCharacteristic":"0","BusinessNotice":"12","BusinessActivity":"12","Category":"烤鱿鱼,"}]
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
         * BusinessInfoId : 234321a7-b578-4e9a-8f84-7977caf7bd7f
         * BusinessName : 青藤小院烧烤
         * Score : null
         * BusinessTitleImage : /html/file/businessinfo/images/businessinfo_admin_1_15318169045030.png
         * BusinessAbbreviation : 商家简称
         * BusinessIntroduction : 商家介绍
         * BusinessStory : 品牌故事
         * BusinessService : 商家所提供的服务
         * StartHours : 2018-07-17 00:00:00
         * EndHours : 2018-07-17 00:00:00
         * BusinessAddress : 1-222
         * BusinessPhone : 13988776655
         * BusinessPeople : 比某某
         * IsCharacteristic : 0
         * BusinessNotice : 12
         * BusinessActivity : 12
         * Category : 烤鱿鱼,
         */

        private String BusinessInfoId;
        private String BusinessName;
        private String Score;
        private String Consumption;
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
        private String type;

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

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
        }

        public String getConsumption() {
            return Consumption;
        }

        public void setConsumption(String consumption) {
            Consumption = consumption;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
