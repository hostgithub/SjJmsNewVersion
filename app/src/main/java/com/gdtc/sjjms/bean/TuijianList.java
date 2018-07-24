package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjiawei on 2018-7-24.
 */

public class TuijianList implements Serializable{


    /**
     * results : [{"GoodsName":"可口可乐","GoodsImage":"http://192.168.0.111:10020/html/file/goodsmanager/images/goodsmanageradmin_1_15317266824910.jpg","GoodsMoney":"3","GoodsExplain":"可口可乐","IsNewGoods":"0","IsHotGoods":"0","IsCharacteristic":"null"},{"GoodsName":"美年达","GoodsImage":"http://192.168.0.111:10020/html/file/businessinfo/images/goodsmanageradmin_1_15317251996910.jpg","GoodsMoney":"3","GoodsExplain":"","IsNewGoods":"1","IsHotGoods":"0","IsCharacteristic":"null"},{"GoodsName":"沙拉烤肉拌饭","GoodsImage":"http://192.168.0.111:10020/html/file/goodsmanager/images/goodsmanageradmin_1_15317267665680.jpg","GoodsMoney":"17","GoodsExplain":"烤肉拌饭","IsNewGoods":"0","IsHotGoods":"0","IsCharacteristic":"null"},{"GoodsName":"咖啡奶茶","GoodsImage":"http://192.168.0.111:10020/html/file/goodsmanager/images/goodsmanageradmin_1_15317325838700.png","GoodsMoney":"13","GoodsExplain":"","IsNewGoods":"0","IsHotGoods":"0","IsCharacteristic":"null"}]
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
         * GoodsName : 可口可乐
         * GoodsImage : http://192.168.0.111:10020/html/file/goodsmanager/images/goodsmanageradmin_1_15317266824910.jpg
         * GoodsMoney : 3
         * GoodsExplain : 可口可乐
         * IsNewGoods : 0
         * IsHotGoods : 0
         * IsCharacteristic : null
         */

        private String GoodsName;
        private String GoodsImage;
        private String GoodsMoney;
        private String GoodsExplain;
        private String IsNewGoods;
        private String IsHotGoods;
        private String IsCharacteristic;

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

        public String getGoodsMoney() {
            return GoodsMoney;
        }

        public void setGoodsMoney(String GoodsMoney) {
            this.GoodsMoney = GoodsMoney;
        }

        public String getGoodsExplain() {
            return GoodsExplain;
        }

        public void setGoodsExplain(String GoodsExplain) {
            this.GoodsExplain = GoodsExplain;
        }

        public String getIsNewGoods() {
            return IsNewGoods;
        }

        public void setIsNewGoods(String IsNewGoods) {
            this.IsNewGoods = IsNewGoods;
        }

        public String getIsHotGoods() {
            return IsHotGoods;
        }

        public void setIsHotGoods(String IsHotGoods) {
            this.IsHotGoods = IsHotGoods;
        }

        public String getIsCharacteristic() {
            return IsCharacteristic;
        }

        public void setIsCharacteristic(String IsCharacteristic) {
            this.IsCharacteristic = IsCharacteristic;
        }
    }
}
