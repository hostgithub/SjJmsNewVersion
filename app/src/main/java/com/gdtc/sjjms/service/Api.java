package com.gdtc.sjjms.service;

import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.bean.Banners;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.bean.NewCenter;
import com.gdtc.sjjms.bean.UserInfo;

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

    @GET("client/android/insertMember.action")//上传个人信息
    Call<UserInfo> uploadInfo(@Query("openId") String openId, @Query("image") String image, @Query("name") String name);


    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id, @Query("pages") int pages);

    @GET("client/android/getRegionalList.action")//一级列表
    Call<AreaOneBean> getAreaOneBeanData();

    @GET("client/android/getUpRegionalList.action")//二级列表
    Call<AreaTwoBean> getAreaTwoBeanData(@Query("regionalId") String regionalId);


    @GET("/client/android/getBusinessInfoPage.action")//商家列表
    Call<NearbySellerBean> getNearbySellerData(@Query("paging") int paging,@Query("businessRegional") String businessRegional);


    @GET("/client/android/getBusinessInfoById.action")//商家详情
    Call<NearbySellerDetailBean> getNearbySellerDetailData( @Query("BusinessInfoId") String BusinessInfoId);

    @GET("client/android/getSectionPage.action")//经纬度查询商家列表
    Call<NearbySellerBean> getNearbySellerListData(@Query("paging") int paging,@Query("longitude") String longitude,@Query("latitude") String latitude);
}
