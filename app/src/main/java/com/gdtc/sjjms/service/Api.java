package com.gdtc.sjjms.service;

import com.gdtc.sjjms.bean.AreaOneBean;
import com.gdtc.sjjms.bean.AreaTwoBean;
import com.gdtc.sjjms.bean.Banners;
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
//    //http://192.168.0.122:8080//app/corGetById.do?id=103558   获取轮播图详情json
//    @GET("app/corGetById.do")
//    Call<Detail> getDetailData(@Query("id") int id);
//
//    //http://192.168.0.124:8080/app/corTabloid.do?id=000100050007&pages=1   获取新闻中心列表json  固定ID
//    @GET("app/corTabloid.do")
//    Call<NewCenter> getNewCenterData(@Query("id") String id, @Query("pages") int pages);
//
//    //http://192.168.0.135:8080/app_phone/appLogin.do?userid=wangtianpeng&user_password=123&dept_properties=topoffice       login返回 personnelId=56736 depetUnit=10021
//    @GET("app_phone/appLogin.do")
//    Call<ResponseBean> getLoginData(@Query("userid") String userid, @Query("user_password") String user_password, @Query("dept_properties") String dept_properties);
//
//    //http://192.168.0.135:8080/app_phone/getUntreatedList.do?sign=56736&deptunit=10021&page=1   发文待办
//    @GET("app_phone/getUntreatedList.do")
//    Call<DispatchWaitDeal> getDispatchWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);
//
//    //http://192.168.0.135:8080/app_phone/dispatchInfodb.do?pathdata=sgy&deptunit=10021&sign=&userid=&file_source_id=&flowsort=               发文待办详情2版本
//    @GET("app_phone/dispatchInfodb.do")
//    Call<DetailDispatchdb> getDispatchdbDetailData(@Query("pathdata") String pathdata, @Query("deptunit") String deptunit,
//                                                   @Query("sign") String sign, @Query("userid") String userid,
//                                                   @Query("file_source_id") String file_source_id, @Query("flowsort") String flowsort);
//
//    //http://192.168.0.135:8080/app_phone/getProcessedHandleList.do?sign=56736&page=1   发文已办
//    @GET("app_phone/getProcessedHandleList.do")
//    Call<DispatchHasDeal> getDispatchHasDealData(@Query("sign") String sign, @Query("page") int page);
//
//    //http://192.168.0.135:8080/app_phone/dispatchInfoyb.do?file_source_id=120620170853057AM56736&deptunit=10021&type=OutfileDetailYiBan&pathdata=sgy 发文已办详情 好像有可能要加载word
//    @GET("app_phone/dispatchInfoyb.do")
//    Call<DispatchHasDealDetail> getDispatchHasDetailData(@Query("file_source_id") String file_source_id, @Query("deptunit") String deptunit
//            , @Query("type") String type, @Query("pathdata") String pathdata);
//
//    //http://192.168.0.135:8080/app_phone/newGetInHandleList.do?sign=56736=&page=1   收文已办
//    @GET("app_phone/newGetInHandleList.do")
//    Call<IncomingHasDeal> getIncomingHasDealData(@Query("sign") String sign, @Query("page") int page);
//
//    //http://192.168.0.135:8080/app_phone/getInfileFlowSingle.do?pathdata=sgy&file_source_id=091520170219028PM56736&deptunit=10021 收文已办详情
//    @GET("app_phone/getInfileFlowSingle.do")
//    Call<DispatchHasDealDetail> getIncomingHasDetailData(@Query("pathdata") String pathdata,
//                                                         @Query("file_source_id") String file_source_id, @Query("deptunit") String deptunit);
//
//
//    //http://192.168.0.135:8080/app_phone/getProcessedList.do?sign=56736&deptunit=10021&page=1   收文待办
//    @GET("app_phone/getProcessedList.do")
//    Call<DispatchWaitDeal> getIncomingWaitDealData(@Query("sign") String sign, @Query("deptunit") String deptunit, @Query("page") int page);
//
//
//    //http://192.168.0.135:8080/app_phone/processedInfo.do?pathdata=sgy&flowsort=120420170424026PM56104&deptunit=10021&file_source_id=120420170423007PM56736&sign=56736    收文待办详情
//    @GET("app_phone/processedInfo.do")
//    Call<ShouWenDbDetail> getIncomingDbData(@Query("pathdata") String pathdata, @Query("flowsort") String flowsort,
//                                            @Query("deptunit") String deptunit,
//                                            @Query("file_source_id") String file_source_id, @Query("sign") String sign);

}
