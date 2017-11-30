package com.congp.app.network;

/**
 * Created by congp on 11/29/2017.
 */

public class Utils {
    private static final String BASE_URL="https://appid-apkk.xx-app.com/frontApi/";

    public Utils() {
    }

    public static Service getIapiService() {
        return ClientRetrofit.getClient(BASE_URL).create(Service.class);
    }
}
