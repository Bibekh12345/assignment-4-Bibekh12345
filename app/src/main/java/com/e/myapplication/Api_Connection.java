package com.e.myapplication;

import com.e.myapplication.INTERFACE_API.USER_INTERFACE;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Connection {
    public  static final String API_URL = "http://10.0.2.2:3000/";
    private   final Retrofit retrofit;
    private  final USER_INTERFACE user_interface;

    public Api_Connection() {
        retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        user_interface = retrofit.create(USER_INTERFACE.class);
    }
    public Retrofit getRetrofit(){
        return  retrofit;
    }
    public USER_INTERFACE getUser_interface(){
        return user_interface;
    }
}
