package com.maiajam.bankblood.data.retrofit;


import com.maiajam.bankblood.data.model.contactUs.ContactUs;
import com.maiajam.bankblood.data.model.donatesRequest.DonReqDatum;
import com.maiajam.bankblood.data.model.donatesRequest.DonateRequest;
import com.maiajam.bankblood.data.model.donationDet.DonationDet;
import com.maiajam.bankblood.data.model.favList.FavList;
import com.maiajam.bankblood.data.model.listOfCities.ListOfCities;
import com.maiajam.bankblood.data.model.listOfgovernorates.ListOfGovernoratesDatum;
import com.maiajam.bankblood.data.model.listOfgovernorates.ListOfgovernorates;
import com.maiajam.bankblood.data.model.login.Login;
import com.maiajam.bankblood.data.model.newPass.NewPass;
import com.maiajam.bankblood.data.model.newRequest.NewRequest;
import com.maiajam.bankblood.data.model.postDetails.PostDetails;
import com.maiajam.bankblood.data.model.posts.Posts;
import com.maiajam.bankblood.data.model.register.Register;
import com.maiajam.bankblood.data.model.resetPassword.ResetPassWord;
import com.maiajam.bankblood.data.model.toggleFav.ToggleFaveData;
import com.maiajam.bankblood.data.model.toggleFav.TogglePost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<Login> Login(@Field("phone") String email ,
                      @Field("password") String password);

    @POST("register")
    @FormUrlEncoded
    Call<Register> Register(@Field("name") String name ,
                            @Field("email") String email ,
                            @Field("birth_date") String birthdate ,
                            @Field("city_id") int city_id ,
                            @Field("phone") String phone ,
                            @Field("donation_last_date") String  donation_last_date ,
                            @Field("password") String password ,
                            @Field("passwordCon") String passwordCon,
                            @Field("blood_type") String blood_type);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassWord> resetPassword(@Field("phone") String phoneNo );

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<TogglePost> togglePost(@Field("post_id") String post_id ,
                                @Field("api_token") String api_Token);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPass> newPassword(@Field("password") String pass ,
                                @Field("password_confirmation") String password_confirmation ,
                                @Field("pin_code") String pin_code );
    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> conntactUs(@Field("api_token") String apitToken,@Field("title") String title,@Field("message") String content);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<NewRequest> newReqCreat(@Field("api_token") String api_token ,
                                 @Field("patient_name") String patient_name ,
                                 @Field("patient_age") int patient_age ,
                                 @Field("blood_type") String blood_type ,
                                 @Field("bags_num") int bags_num ,
                                 @Field("hospital_name") String hospital_name ,
                                 @Field("hospital_address") String hospital_address ,
                                 @Field("city_id") int city_id ,
                                 @Field("phone") String phone ,
                                 @Field("notes") String notes ,
                                 @Field("latitude") Double latitude ,
                                 @Field("longitude") Double longitude );

    @GET("governorates")
    Call<ListOfgovernorates> ListOfgovernorates();

    @GET("cities")
    Call<ListOfCities> ListOfCities(@Query("governorate_id") int GovernateId);

    @GET("posts")
    Call<Posts> getArticlas(@Query("api_token")String apiToken,
                            @Query("page")int page);
    @GET("donation-requests")
    Call<DonateRequest> getDonatesReq(@Query("api_token")String apitToken);

    @GET("donation-request")
    Call<DonationDet> getReqDetials(@Query("api_token")String apitToken ,
                                    @Query("donation_id")String donation_id);

    @GET("post")
    Call<PostDetails> getPostDetails(@Query("api_token")String apitToken ,
                                     @Query("post_id")Integer post_id);

    @GET("my-favourites")
    Call<FavList> getFavList(@Query("api_token")String apitToken );


   // Call<> getUserInfo(String apitToken);
}
