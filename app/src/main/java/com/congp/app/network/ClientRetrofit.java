package com.congp.app.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by congp on 11/29/2017.
 */

public class ClientRetrofit {
    private static retrofit2.Retrofit retrofit=null;
    public static retrofit2.Retrofit getClient(String baseUrl){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit=new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
