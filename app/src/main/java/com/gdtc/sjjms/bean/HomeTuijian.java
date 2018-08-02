package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-8-1.
 */

public class HomeTuijian implements Serializable{

    /**
     * results : [{"BusinessInfoId":"6882feff-b990-40d7-a815-c7fad1f04ac3","BusinessName":"七天快捷宾馆","Score":"1","BusinessTitleImage":"http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15325900851480.jpg","BusinessAbbreviation":"七天快捷宾馆","BusinessIntroduction":"七天快捷宾馆","BusinessStory":"七天快捷宾馆","BusinessService":"空调，大床","StartHours":"12:00","EndHours":"00:00","BusinessAddress":"佳木斯市客运站前","IsCharacteristic":"0","BusinessNotice":"黄赌毒","BusinessActivity":"黄赌毒","Consumption":"108","Regional":"向阳区","GoodsName":"大床房","GoodsImage":"/html/file/goodsmanager/images/goodsmanageradmin_1_15330889891280.jpg","Category":"酒店,"}]
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
         * BusinessInfoId : 6882feff-b990-40d7-a815-c7fad1f04ac3
         * BusinessName : 七天快捷宾馆
         * Score : 1
         * BusinessTitleImage : http://192.168.0.111:10020/html/file/businessinfo/images/businessinfo_admin_1_15325900851480.jpg
         * BusinessAbbreviation : 七天快捷宾馆
         * BusinessIntroduction : 七天快捷宾馆
         * BusinessStory : 七天快捷宾馆
         * BusinessService : 空调，大床
         * StartHours : 12:00
         * EndHours : 00:00
         * BusinessAddress : 佳木斯市客运站前
         * IsCharacteristic : 0
         * BusinessNotice : 黄赌毒
         * BusinessActivity : 黄赌毒
         * Consumption : 108
         * Regional : 向阳区
         * GoodsName : 大床房
         * GoodsImage : /html/file/goodsmanager/images/goodsmanageradmin_1_15330889891280.jpg
         * Category : 酒店,
         */

        private String BusinessInfoId;
        private String BusinessName;
        private String Score;
        private String BusinessTitleImage;
        private String BusinessAbbreviation;
        private String BusinessIntroduction;
        private String BusinessStory;
        private String BusinessService;
        private String StartHours;
        private String EndHours;
        private String BusinessPhone;
        private String BusinessAddress;
        private String IsCharacteristic;
        private String BusinessNotice;
        private String BusinessActivity;
        private String Consumption;
        private String Regional;
        private String GoodsName;
        private String GoodsImage;
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

        public String getScore() {
            return Score;
        }

        public void setScore(String Score) {
            this.Score = Score;
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

        public String getBusinessPhone() {
            return BusinessPhone;
        }

        public void setBusinessPhone(String businessPhone) {
            BusinessPhone = businessPhone;
        }

        public String getBusinessAddress() {
            return BusinessAddress;
        }

        public void setBusinessAddress(String BusinessAddress) {
            this.BusinessAddress = BusinessAddress;
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

        public String getConsumption() {
            return Consumption;
        }

        public void setConsumption(String Consumption) {
            this.Consumption = Consumption;
        }

        public String getRegional() {
            return Regional;
        }

        public void setRegional(String Regional) {
            this.Regional = Regional;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getGoodsImage() {
            return GoodsImage;
        }

        public void setGoodsImage(String GoodsImage) {
            this.GoodsImage = GoodsImage;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }
    }
}
