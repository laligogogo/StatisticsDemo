package com.didi.business.strategy;

import android.text.TextUtils;

import com.didi.business.common.BusinessConstant;
import com.didi.business.helper.BusinessDbHelper;
import com.didi.business.helper.BusinessLogDataHelper;
import com.didi.business.helper.BusinessLogFormatHelper;
import com.didi.business.model.BusinessAdLog;
import com.didi.business.model.BusinessRuntimeInfo;
import com.didi.business.net.BusinessRequest;
import com.didi.business.util.BusinessUtils;
import com.third.lidroid.xutils.DbUtils;
import com.third.lidroid.xutils.exception.DbException;
import com.third.lidroid.xutils.exception.HttpException;
import com.third.lidroid.xutils.http.ResponseInfo;
import com.third.lidroid.xutils.http.callback.RequestCallBack;
import com.third.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huhuajun on 2015/10/15.
 * 广告日志策略类
 */
public class BusinessAdLogStrategy extends BusinessAbsStrategy {

    private static final int AD_CACHE_MAX_COUNT = 5;

    protected BusinessLogDataHelper mLogDataHelper;
    protected BusinessLogStrategyConfig mLogStrategyConfig;
    private boolean mIsFirstInit = true;

    public BusinessAdLogStrategy(int strategyType) {
        super(strategyType);
        mLogDataHelper = BusinessLogDataHelper.getInstance();
        mLogStrategyConfig = new BusinessLogStrategyConfig.Builder().count(AD_CACHE_MAX_COUNT).build();
    }

    @Override
    public boolean onCheckLog(int action) {
        int size = mLogDataHelper.getLogSize(mStrategyType);
        if (mIsFirstInit && size > 0) {
            mIsFirstInit = false;
            return true;
        }

        //轮训的方式直接给过
        if (action == BusinessConstant.LOG_POLLING_ACTION && size > 0) {
            return true;
        }

        if (size >= mLogStrategyConfig.getCount())
            return true;
        return false;
    }

    @Override
    public void uploadLog() {
        if (!BusinessUtils.getNetworkEnable(BusinessRuntimeInfo.getInstance().getAppContext()))
            return;
        //TODO 生成日志数据
        HashMap<String, List<BusinessAdLog>> logMap = recombineAdLog();
        submitAdLogToServer(logMap);
    }

    @Override
    public void loadLog() {
        // 广告日志
        List<BusinessAdLog> adLogList = loadLocalBusinessLog(BusinessAdLog.class);
        //清除数据库表自增id好让下次存入时，不用更新
        if (adLogList != null){
            for (BusinessAdLog adLog : adLogList) {
                adLog.id = 0;
            }
        }
        removeLocalBusinessLog(BusinessAdLog.class);
        mLogDataHelper.addBusinessLog(mStrategyType, adLogList);

    }

    @Override
    public void saveLog() {
        DbUtils dbUtils = BusinessDbHelper.getInstance().getDbUtils();
        List<BusinessAdLog> logList = mLogDataHelper.getLogList(mStrategyType);
        try {
            dbUtils.saveOrUpdateAll(logList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLogDataHelper.removeBusinessLogWithType(mStrategyType);
    }

    private <T> List<T> loadLocalBusinessLog(Class<T> entityType) {
        DbUtils dbUtils = BusinessDbHelper.getInstance().getDbUtils();
        try {
            return dbUtils.findAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void removeLocalBusinessLog(Class<T> entityType) {
        DbUtils dbUtils = BusinessDbHelper.getInstance().getDbUtils();
        try {
            dbUtils.deleteAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    private void submitAdLogToServer(HashMap<String, List<BusinessAdLog>> logMap) {
        if (logMap == null || logMap.size() == 0)
            return;
        Iterator<Map.Entry<String, List<BusinessAdLog>>> it = logMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<BusinessAdLog>> entry = it.next();
            final List<BusinessAdLog> tmpList = entry.getValue();
            String logMsg = BusinessLogFormatHelper.buildAdLogMsg(tmpList);
            if (!TextUtils.isEmpty(logMsg)) {
                BusinessRequest.uploadBusinessAdLog(logMsg, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.i("---->uploadBusinessAdLog onSuccess:广告统计上传成功");
                        mLogDataHelper.removeBusinessLog(mStrategyType, tmpList);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.i("---->uploadBusinessAdLog onFailure:广告统计上传失败" + msg);
                    }
                });
            }
        }
    }


    /**
     * 将多个日志对象合并成他们需要的格式
     * 并且根据不同uid区分；
     */
    private HashMap<String, List<BusinessAdLog>> recombineAdLog() {
        List<BusinessAdLog> adLogList = mLogDataHelper.getLogList(mStrategyType);
        HashMap<String, List<BusinessAdLog>> adLogMap = new HashMap<String, List<BusinessAdLog>>();

        if (adLogList != null && adLogList.size() > 0) {
            for (BusinessAdLog adLog : adLogList) {
                List<BusinessAdLog> tmpLogList = adLogMap.get(adLog.uid);
                if (tmpLogList == null) {
                    tmpLogList = new ArrayList<BusinessAdLog>();
                    if (adLog.uid == null)
                        adLog.uid = "";
                    adLogMap.put(adLog.uid, tmpLogList);
                }
                tmpLogList.add(adLog);
            }
        }
        return adLogMap;
    }

    //---------get/set----------//

}
