package com.gdtc.sjjms.service;

import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.bean.Banners;
import com.gdtc.sjjms.bean.Collect;
import com.gdtc.sjjms.bean.Comment;
import com.gdtc.sjjms.bean.CommentList;
import com.gdtc.sjjms.bean.HomeTuijian;
import com.gdtc.sjjms.bean.Kind;
import com.gdtc.sjjms.bean.NearbySellerBean;
import com.gdtc.sjjms.bean.NearbySellerDetailBean;
import com.gdtc.sjjms.bean.NewCenter;
import com.gdtc.sjjms.bean.TuijianList;
import com.gdtc.sjjms.bean.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangjiawei on 2017-11-14.
 */

public interface Api {


    @POST("client/android/getSowingMap.action")//轮播
    Call<Banners> getBannerData();


    @POST("client/android/getProduct.action")//分类
    Call<Kind> getFenlei(@Query("parentid") int parentid);

    @POST("client/android/getProduct.action")//全部分类
    Call<Kind> getAllKind();

    @GET("client/android/insertMember.action")//上传个人信息
    Call<UserInfo> uploadInfo(@Query("openId") String openId, @Query("image") String image, @Query("name") String name);


    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
    @GET("app/corTabloid.do")
    Call<NewCenter> getNewCenterData(@Query("id") String id, @Query("pages") int pages);

    @GET("client/android/getRegionalList.action")//一级列表
    Call<AreaOneBean> getAreaOneBeanData();

    @GET("client/android/getUpRegionalList.action")//二级列表
    Call<AreaTwoBean> getAreaTwoBeanData(@Query("regionalId") String regionalId);


    @GET("client/android/getBusinessInfoPage.action")//商家列表
    Call<NearbySellerBean> getNearbySellerData(@Query("paging") int paging,@Query("businessRegional") String businessRegional);

    @GET("client/android/getCollectionList.action")//收藏商家列表
    Call<NearbySellerBean> getMineCollectData(@Query("openId") String openId,@Query("paging") int paging);

    @GET("client/android/getMarkList.action")//我的足迹列表
    Call<NearbySellerBean> getMineZujiData(@Query("openId") String openId,@Query("paging") int paging);

    @GET("client/android/getSearchList.action")//搜索列表
    Call<NearbySellerBean> getSearchList(@Query("paging") int paging,@Query("businessName") String openId);


    @GET("client/android/getBusinessInfoById.action")//商家详情
    Call<NearbySellerDetailBean> getNearbySellerDetailData( @Query("BusinessInfoId") String BusinessInfoId,@Query("openId") String openId);

    @GET("client/android/getBusinessFoodInfo.action")//推荐菜列表
    Call<TuijianList> getTuijianData(@Query("businessId") String businessId, @Query("paging") int paging);


    @GET("client/android/insertEvaluate.action")//评论
    Call<Comment> getCommentData(@Query("businessId") String businessId, @Query("openId") String openId,
                                 @Query("name") String name, @Query("evaluate") String evaluate);



    @GET("client/android/getEvaluateList.action")//评论列表
    Call<CommentList> getCommentListData(@Query("paging") int paging,@Query("businessId") String businessId);

    @GET("client/android/insertCollection.action")//收藏与取消收藏
    Call<Collect> getCollectData(@Query("businessId") String businessId, @Query("openId") String openId,
                                 @Query("name") String name, @Query("type") String type);

    @GET("client/android/getSectionPage.action")//经纬度查询商家列表
    Call<NearbySellerBean> getNearbySellerListData(@Query("paging") int paging,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("regional") String regional);


    @GET("client/android/getHotList.action")//热门商家列表
    Call<NearbySellerBean> getHotListData(@Query("paging") int paging);

    @GET("client/android/getRecommendList.action")//美食推荐列表
    Call<HomeTuijian> getHomeTuijianListData(@Query("paging") int paging);

    @GET("client/android/getSectionPage.action")//分类列表
    Call<NearbySellerBean> getSimpleKind(@Query("paging") int paging,@Query("product") String product);

}
