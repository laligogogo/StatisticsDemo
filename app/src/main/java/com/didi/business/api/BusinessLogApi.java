package com.didi.business.api;

import android.content.Context;

import com.didi.business.common.BusinessConstant;
import com.didi.business.helper.BusinessLogDataHelper;
import com.didi.business.helper.BusinessLogFormatHelper;
import com.didi.business.log.BusinessLogManager;
import com.didi.business.model.BusinessAdLog;
import com.didi.business.model.BusinessProductChannel;
import com.didi.business.model.BusinessRuntimeInfo;
import com.third.lidroid.xutils.util.LogUtils;

/**
 * Created by huhuajun on 2015/10/15.
 */
public class BusinessLogApi {

    /**
     * 程序启动的进行调用，初始化日志统计相关信息
     *
     * @param context
     * @param productChannel
     */
    public static void init(Context context, BusinessProductChannel productChannel) {
        if (context == null)
            throw new NullPointerException("Context object is null .");
        BusinessRuntimeInfo.getInstance().setAppContext(context.getApplicationContext());
        BusinessRuntimeInfo.getInstance().setProductChannel(productChannel);
        BusinessLogManager.getInstance().initLogManager();
    }

    /**
     * 添加ad广告日志统计
     * 定制接口
     *
     * @param uid
     * @param adid  广告id
     * @param posid 广告投放未知
     * @param event 广告触发类型
     * @param lat
     * @param lng
     */
    public static void addBusinessAdLog(String uid, String adid, String posid, String event, String lat, String lng) {
        BusinessAdLog adLog = BusinessLogFormatHelper.formatBusinessAdLog(uid, adid, posid, event, lat, lng);
        BusinessLogDataHelper.getInstance().addBusinessLog(BusinessConstant.BUSINESS_AD_TYPE, adLog);
    }

    /**
     * 添加一般日志
     * 暂未实现:
     */
    public static void addCommonLog(String msg) {

    }

    /**
     * 退出日志统计，一般是程序退出时调用
     */
    public static void exit() {
        BusinessLogManager.getInstance().exitLogManager();
    }

    public static void logEnable(boolean enable) {
        LogUtils.allowD = enable;
        LogUtils.allowE = enable;
        LogUtils.allowI = enable;
        LogUtils.allowW = enable;
        LogUtils.allowV = enable;
    }

}
