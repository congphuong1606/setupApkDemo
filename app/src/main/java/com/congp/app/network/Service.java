package com.congp.app.network;




import com.congp.app.data.ResultApk;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by congp on 11/29/2017.
 */

public interface Service {
    @GET("getAboutUs")
    Observable<ResultApk> provider(@Query("appid") String action);
}
