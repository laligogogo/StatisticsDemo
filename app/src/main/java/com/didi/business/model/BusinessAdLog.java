package com.didi.business.model;

import com.third.lidroid.xutils.db.annotation.Column;
import com.third.lidroid.xutils.db.annotation.Table;
import com.third.lidroid.xutils.db.annotation.Transient;

import java.io.Serializable;

/**
 * Created by huhuajun on 2015/10/15.
 */
@Table(name = "business_ad_log")
public class BusinessAdLog extends BusinessBaseLog {

    /**
     * 表自增id
     */
    @Column(column = "id")
    public int id;
    /**
     * 广告投放的app
     */
    @Column(column = "appid")
    public String appid;
    /**
     * 广告投放的位置id
     */
    @Column(column = "posid")
    public String posid;
    /**
     * 时间戳
     */
    @Column(column = "timestamp")
    public long timestamp;
    /**
     * 广告触发形式
     */
    @Column(column = "event")
    public String event;
    /**
     * 投放的广告id
     */
    @Column(column = "adid")
    public String adid;
    /**
     * 纬度
     */
    @Column(column = "lat")
    public double lat;
    /**
     * 精度
     */
    @Column(column = "lng")
    public double lng;

    /**
     * app versionName
     */
    @Column(column = "version")
    public String version;
    /**
     * App uid
     */
    @Column(column = "uid")
    public String uid;

    @Override
    public String toString() {
        return "[id=" + id + ",ts=" + timestamp + "]";
    }

    @Override
    public String toJsonString() {
        //TODO 转换成json对象
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessAdLog adLog = (BusinessAdLog) o;

        if (timestamp != adLog.timestamp) return false;
        if (appid != null ? !appid.equals(adLog.appid) : adLog.appid != null) return false;
        if (posid != null ? !posid.equals(adLog.posid) : adLog.posid != null) return false;
        if (event != null ? !event.equals(adLog.event) : adLog.event != null) return false;
        if (adid != null ? !adid.equals(adLog.adid) : adLog.adid != null) return false;
        if (version != null ? !version.equals(adLog.version) : adLog.version != null) return false;
        return !(uid != null ? !uid.equals(adLog.uid) : adLog.uid != null);

    }

    @Override
    public int hashCode() {
        int result = appid != null ? appid.hashCode() : 0;
        result = 31 * result + (posid != null ? posid.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (adid != null ? adid.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        return result;
    }
}
