package com.didi.business.helper;

import android.content.Context;
import android.text.TextUtils;

import com.didi.business.model.BusinessAdLog;
import com.didi.business.model.BusinessRuntimeInfo;
import com.didi.business.util.BusinessDeviceUtils;
import com.didi.business.util.BusinessUtils;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by huhuajun on 2015/10/15.
 * 负责将外部日志格式统一转换成自己内部识别的日志格式
 * 或者反转
 */
public class BusinessLogFormatHelper {

    /**
     * 根据必要数据生成一个完成的ad日志对象
     *
     * @param uid
     * @param adid
     * @param posid
     * @param event
     * @param lat
     * @param lng
     * @return
     */
    public static BusinessAdLog formatBusinessAdLog(String uid, String adid, String posid, String event, String lat, String lng) {
        Context c = BusinessRuntimeInfo.getInstance().getAppContext();
        BusinessAdLog adLog = new BusinessAdLog();
        adLog.appid = BusinessRuntimeInfo.getInstance().getProductChannel().value() + "";
        adLog.posid = posid;
        adLog.timestamp = System.currentTimeMillis();
        adLog.event = event;
        adLog.adid = adid;
        try {
            adLog.lat = TextUtils.isEmpty(lat) ? 0.0 : Double.valueOf(lat);
            adLog.lng = TextUtils.isEmpty(lng) ? 0.0 : Double.valueOf(lng);
        } catch (Exception e) {
        }
        adLog.uid = uid;
        adLog.version = BusinessUtils.getCurrentVersion(c);
        return adLog;
    }

    /**
     * {
     * "imp": [
     * {
     * "id": "1",
     * "stats": [
     * {
     * "ts": 1443079792,
     * "event": "show",
     * "adid": "123"
     * },
     * {
     * "ts": 1443079793,
     * "event": "show",
     * "adid": "123"
     * },
     * {
     * "ts": 1443079795,
     * "event": "click",
     * "adid": "123"
     * }
     * ]
     * },
     * {
     * "id": "2",
     * "stats": [
     * {
     * "ts": 1443079815,
     * "event": "show",
     * "adid": "128"
     * },
     * {
     * "ts": 1443078795,
     * "event": "click",
     * "adid": "128"
     * }
     * ]
     * }
     * ],
     * "device": {
     * "app": "cn.com.didichuxing.passenger",
     * "lat": 29.123,
     * "lng": 116.2,
     * "os": "android",
     * "idfa": "xxxxx",
     * "openuuid": "xxxxx",
     * "mac": "xxxxx",
     * "androidid": "xxxxx",
     * "imei": "xxxxxx",
     * "aaid": "xxxxx",
     * "model": "HUAWEI P7-L07",
     * "ip": "xxxx",
     * "network": "xxxx",
     * "version": "xxx"
     * },
     * "user": {
     * "id": "xxxxx"
     * }
     * }
     * 直接有外层传入必须的数据来构建广告日志
     *
     * @return
     */
    public static String buildAdLogMsg(List<BusinessAdLog> adLogList) {
        if (adLogList == null || adLogList.size() <= 0)
            return "";
        try {
            JSONStringer js = new JSONStringer();
            js.object();
            js.key("imp");
            js.array();
            for (BusinessAdLog adLog : adLogList) {
                js.object();
                js.key("id");
                js.value(adLog.posid);

                js.key("stats");
                js.array();
                js.object();
                js.key("ts");
                js.value(adLog.timestamp);
                js.key("event");
                js.value(adLog.event);
                js.key("adid");
                js.value(adLog.adid);
                js.endObject();
                js.endArray();

                js.endObject();

            }
            js.endArray();

            //其他信息以第一条为准：服务器需求
            Context c = BusinessRuntimeInfo.getInstance().getAppContext();
            BusinessAdLog adLog = adLogList.get(0);
            //设备信息
            js.key("device");
            js.object();
            js.key("app");
            js.value(adLog.appid);
            js.key("lat");
            js.value(adLog.lat);
            js.key("lng");
            js.value(adLog.lng);
            js.key("os");
            js.value("android");
            js.key("idfa");
            js.value("");
            js.key("mac");
            js.value(BusinessDeviceUtils.getMacAddress(c));
            js.key("imei");
            js.value(BusinessDeviceUtils.getImei(c));
            js.key("model");
            js.value(BusinessDeviceUtils.getMobileModel());
            js.key("ip");
            js.value(BusinessDeviceUtils.getMobileIP(c));
            js.key("network");
            js.value(BusinessDeviceUtils.getNetworkType(c));
            js.key("version");
            js.value(adLog.version);
            js.endObject();

            //用户信息
            js.key("user");
            js.object();
            js.key("id");
            js.value(adLog.uid);
            js.endObject();

            js.endObject();
            return js.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String buildCommonLogMsg() {
        return null;
    }


}
