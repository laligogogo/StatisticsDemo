package com.didi.business.helper;

import com.didi.business.model.BusinessBaseLog;
import com.third.lidroid.xutils.DbUtils;
import com.third.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by huhuajun on 2015/10/15.
 * 本地数据和内存数据管理类
 */
public class BusinessLogDataHelper {

    //--------------singleton--------------//

    private static BusinessLogDataHelper sInstance;

    public static BusinessLogDataHelper getInstance() {
        if (sInstance == null) {
            synchronized (BusinessLogDataHelper.class) {
                if (sInstance == null) {
                    sInstance = new BusinessLogDataHelper();
                }
            }
        }
        return sInstance;
    }

    private BusinessLogDataHelper() {
        initDataHelper();
    }

    //------------------------数据变化监听------------------------//
    public static interface BusinessLogDataObserver {
        void onLogDataChange(int logType, int count);
    }

    public static class BusinessLogDataObservable {
        public static void register(BusinessLogDataObserver observer) {
            sObservers.add(observer);
        }

        public static void unregister(BusinessLogDataObserver observer) {
            sObservers.remove(observer);
        }

        public static void notifyLogDataChange(int logType, int count) {
            for (BusinessLogDataObserver observer : sObservers) {
                if (observer == null) {
                    continue;
                }
                observer.onLogDataChange(logType, count);
            }
        }
    }

    private static List<BusinessLogDataObserver> sObservers = new LinkedList<BusinessLogDataObserver>();

    //--------------------------业务操作-------------------------//

    /**
     * 日志广告存储
     * 做个观察者监听数据变化:
     * 现在只做广告
     */
    private Map<Integer, ConcurrentLinkedQueue<BusinessBaseLog>> mLogMapCache = new ConcurrentHashMap<Integer, ConcurrentLinkedQueue<BusinessBaseLog>>();

    private boolean mFirstInit = true;

    private void initDataHelper() {
        if (mFirstInit) {
            mFirstInit = false;
        }

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

    public void addBusinessLog(int logType, BusinessBaseLog log) {
        if (log == null)
            return;
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null) {
            logListCache = new ConcurrentLinkedQueue<BusinessBaseLog>();
            mLogMapCache.put(logType, logListCache);
        }
        logListCache.add(log);
        BusinessLogDataObservable.notifyLogDataChange(logType, logListCache.size());
    }

    public void addBusinessLog(int logType, List<? extends BusinessBaseLog> logList) {
        if (logList == null || logList.size() == 0)
            return;
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null) {
            logListCache = new ConcurrentLinkedQueue<BusinessBaseLog>();
            mLogMapCache.put(logType, logListCache);
        }
        logListCache.addAll(logList);
        BusinessLogDataObservable.notifyLogDataChange(logType, logListCache.size());
    }

    public void removeBusinessLog(int logType, BusinessBaseLog log) {
        if (log == null)
            return;
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null || logListCache.size() == 0)
            return;
        logListCache.remove(log);
    }

    public void removeBusinessLog(int logType, List<? extends BusinessBaseLog> logList) {
        if (logList == null || logList.size() == 0)
            return;
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null || logListCache.size() == 0)
            return;
        logListCache.removeAll(logList);
    }

    public void removeBusinessLogWithType(int logType) {
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null || logListCache.size() == 0)
            return;
        logListCache.clear();
    }

    public void removeAllBusinessLog() {
        if (mLogMapCache == null || mLogMapCache.size() == 0)
            return;
        mLogMapCache.clear();
    }


    /**
     * 对外提供的数据
     * 某一类型的日志
     *
     * @param logType
     * @return
     */
    public <T> ArrayList<T> getLogList(int logType) throws ClassCastException {
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        ArrayList<T> logProviderList = new ArrayList<T>();
        if (logListCache != null) {
            for (BusinessBaseLog log : logListCache) {
                T tmpLog = (T) log.clone();
                if (tmpLog != null) {
                    logProviderList.add(tmpLog);
                }
            }
        }
        return logProviderList;
    }

    /**
     * 获取特定日志类型大小
     *
     * @param logType
     * @return
     */
    public int getLogSize(int logType) {
        ConcurrentLinkedQueue<BusinessBaseLog> logListCache = mLogMapCache.get(logType);
        if (logListCache == null) {
            return 0;
        } else {
            return logListCache.size();
        }
    }

    /**
     * 获取有多少种日志类型
     *
     * @return
     */
    public int getLogTypeSize() {
        return mLogMapCache.size();
    }


}




