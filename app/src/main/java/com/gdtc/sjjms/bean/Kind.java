package com.gdtc.sjjms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class Kind implements Serializable
{


    /**
     * results : [{"Parentid":"0","ProductName":"-","Imageurl":"http://192.168.0.111:10020/html/file/product/images/product_admin_1_15332629781700.png"},{"Parentid":"f4fab57a-f512-4111-a25e-a94535070637","ProductName":"分类3","Imageurl":"http://192.168.0.111:10020/html/file/product/images/product_admin_1_15314696775030.txt"},{"Parentid":"ad714a49-94a5-4711-ba69-7d4052ef92eb","ProductName":"分类4","Imageurl":"http://192.168.0.111:10020/html/file/product/images/product_admin_1_15314685717450.jpg"},{"Parentid":"ad714a49-94a5-4711-ba69-7d4052ef92eb","ProductName":"分类4","Imageurl":"http://192.168.0.111:10020/html/file/product/images/product_admin_1_15314700667060.jpg"}]
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
         * Parentid : 0
         * ProductName : -
         * Imageurl : http://192.168.0.111:10020/html/file/product/images/product_admin_1_15332629781700.png
         */

        private String Parentid;
        private String ProductName;
        private String Imageurl;

        public String getParentid() {
            return Parentid;
        }

        public void setParentid(String Parentid) {
            this.Parentid = Parentid;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getImageurl() {
            return Imageurl;
        }

        public void setImageurl(String Imageurl) {
            this.Imageurl = Imageurl;
        }
    }
}
