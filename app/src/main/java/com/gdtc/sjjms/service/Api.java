package com.gdtc.sjjms.service;

import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.bean.Banners;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.bean.NewCenter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangjiawei on 2017-11-14.
 */

public interface Api {


    @POST("app/corNewsListImg.do")
    Call<Banners> getBannerData();

    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id, @Query("pages") int pages);

    @GET("client/android/getRegionalList.action")//一级列表
    Call<AreaOneBean> getAreaOneBeanData();

    @GET("client/android/getUpRegionalList.action")//一级列表
    Call<AreaTwoBean> getAreaTwoBeanData(@Query("regionalId") String regionalId);


    @GET("/client/android/getBusinessInfoPage.action")
    Call<NearbySellerBean> getNearbySellerData(@Query("paging") int paging,@Query("businessRegional") String businessRegional);


    @GET("/client/android/getBusinessInfoById.action")
    Call<NearbySellerDetailBean> getNearbySellerDetailData( @Query("BusinessInfoId") String BusinessInfoId);
}
