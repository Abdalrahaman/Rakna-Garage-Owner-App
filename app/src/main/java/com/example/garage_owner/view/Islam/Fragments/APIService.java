package com.example.garage_owner.view.Islam.Fragments;

import com.example.garage_owner.view.Islam.Notifications.MyResponse;
import com.example.garage_owner.view.Islam.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAKPZl5R8:APA91bGmsjFof-wGYZ1pxF65CKK4qC_APQ3uBiZzzRhnruXjk0EWqJP5JcHRsjNM-_Kcv9j40CiamTu10AxjztZDDfrcxvk3gkRJAw69JKjngbMoUasJtwzeutcwyUOOVzeKdruxVX3W"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
