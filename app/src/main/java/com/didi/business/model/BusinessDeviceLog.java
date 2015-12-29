package com.didi.business.model;

/**
 * Created by huhuajun on 2015/10/18.
 */
public class BusinessDeviceLog extends BusinessBaseLog {

    //---------------------设备相关----------------------//

    /**
     * iphone 需要的字段
     */
    public String idfa = "";

    public String os;

    public String mac;

    public String androidid;

    public String imei;

    public String aaid;

    public String model;

    public String ip;

    public String network;

    @Override
    public String toJsonString() {
        return super.toJsonString();
    }
}
