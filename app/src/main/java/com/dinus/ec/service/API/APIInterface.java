package com.dinus.ec.service.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by PHAP on 7/12/2016.
 */
public interface APIInterface {
    @POST("login")
    Call<ResponseBody> login(
            @Query("nik") String nik,
            @Query("password") String password,
            @Query("regid") String regid
    );

    @POST("submit-class")
    Call<ResponseBody> submit(
            @Query("id_user") String id_user,
            @Query("id_class_pagi") String id_class_pagi,
            @Query("id_class_siang") String id_class_siang
    );

    @POST("agenda-list")
    Call<ResponseBody> agendalist(
            @Query("tgl") String tgl
    );

    @POST("agenda-date")
    Call<ResponseBody> agendadate();

    @POST("setting")
    Call<ResponseBody> setting(
            @Query("type") String type
    );

    @POST("gallery-list")
    Call<ResponseBody> gallerylist(
            @Query("id_date") String id_date
    );

    @POST("change-password")
    Call<ResponseBody> changepassword(
            @Query("id_user") String id_user,
            @Query("old_pass") String old_pass,
            @Query("new_pass") String new_pass
    );

    @FormUrlEncoded
    @POST("change-photo-profile")
    Call<ResponseBody> changephotoprofile(
            @Query("id") String id_user,
            @Field("photo") String photo
    );

    @POST("gallery-date")
    Call<ResponseBody> gallerydate();

    @POST("hotel-list")
    Call<ResponseBody> hotelList();

    @POST("top-rank")
    Call<ResponseBody> toprank();

    @POST("profile")
    Call<ResponseBody> profile(
            @Query("id_user") String id_user
    );

    @POST("scan-qrcode")
    Call<ResponseBody> scan(
            @Query("id_user") String id_user,
            @Query("code") String code
    );

    @POST("maps")
    Call<ResponseBody> maps();

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @FormUrlEncoded
    @POST("customercare")
    Call<ResponseBody> customercare(
            @Query("user_secret") String user_secret,
            @Query("time") Long time,
            @Query("type") String app_type,
            @Query("id_user") String id_user,
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("description") String description,
            @Field("image_1") String image_1,
            @Field("image_2") String image_2,
            @Field("image_3") String image_3
    );

}
