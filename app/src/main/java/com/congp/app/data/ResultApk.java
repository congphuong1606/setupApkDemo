package com.congp.app.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResultApk
{

    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("appname")
    @Expose
    private String appname;
    @SerializedName("isshowwap")
    @Expose
    private String isshowwap;
    @SerializedName("wapurl")
    @Expose
    private String wapurl;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("desc")
    @Expose
    private String desc;


    /**
     * No args constructor for use in serialization
     *
     */
    public ResultApk() {
    }

    /**
     *
     * @param desc
     * @param status
     * @param appname
     * @param appid
     * @param isshowwap
     * @param wapurl
     */
    public ResultApk(String appid, String appname, String isshowwap, String wapurl, Integer status, String desc) {
        super();
        this.appid = appid;
        this.appname = appname;
        this.isshowwap = isshowwap;
        this.wapurl = wapurl;
        this.status = status;
        this.desc = desc;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getIsshowwap() {
        return isshowwap;
    }

    public void setIsshowwap(String isshowwap) {
        this.isshowwap = isshowwap;
    }

    public String getWapurl() {
        return wapurl;
    }

    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}


