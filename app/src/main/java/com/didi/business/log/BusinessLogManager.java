package com.didi.business.log;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.didi.business.common.BusinessConstant;
import com.didi.business.helper.BusinessLogDataHelper;
import com.didi.business.model.BusinessRuntimeInfo;
import com.didi.business.polling.BusinessPollingImpl;
import com.didi.business.receiver.BusinessNetworkStateReceiver;
import com.didi.business.strategy.BusinessAdLogStrategy;
import com.didi.business.strategy.IBusinessLogStrategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by huhuajun on 2015/10/15.
 */
public class BusinessLogManager implements BusinessLogDataHelper.BusinessLogDataObserver, BusinessPollingImpl.BusinessPollingObserver {

    private static final String TAG = "BusinessLogManager";
    private static BusinessLogManager sInstance;
    private BusinessLogInterfaceImpl mLogManagerImpl;
    private BusinessPollingImpl mPollingImpl;
    private BusinessNetworkStateReceiver mNetworkReceiver;

    private HashMap<Integer, IBusinessLogStrategy> mLogStrategyMap = new HashMap<Integer, IBusinessLogStrategy>();

    public static BusinessLogManager getInstance() {
        if (sInstance == null) {
            synchronized (BusinessLogManager.class) {
                if (sInstance == null) {
                    sInstance = new BusinessLogManager();
                }
            }
        }
        return sInstance;
    }

    private BusinessLogManager() {
        initDefaultConfig();
    }

    private void initDefaultConfig() {
        loadAllStrategy();
        mLogManagerImpl = new BusinessLogInterfaceImpl();
        mPollingImpl = new BusinessPollingImpl();
        mPollingImpl.startPolling();
    }

    private void loadAllStrategy() {
        mLogStrategyMap.put(BusinessConstant.BUSINESS_AD_TYPE, new BusinessAdLogStrategy(BusinessConstant.BUSINESS_AD_TYPE));
    }

    /**
     * 初始化
     */
    public void initLogManager() {
        BusinessLogDataHelper.BusinessLogDataObservable.register(this);
        BusinessPollingImpl.BusinessPollingObservable.register(this);
        loadLocalLog();
        registerNetworkStateReceiver();
    }

    private void loadLocalLog() {
        Iterator<Map.Entry<Integer, IBusinessLogStrategy>> it = mLogStrategyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, IBusinessLogStrategy> entry = it.next();
            entry.getValue().loadLog();
        }
    }

    public void registerNetworkStateReceiver() {
        Context c = BusinessRuntimeInfo.getInstance().getAppContext();
        if (mNetworkReceiver == null) {
            mNetworkReceiver = new BusinessNetworkStateReceiver();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        try {
            c.registerReceiver(mNetworkReceiver, filter);
        } catch (Exception e) {
            Log.i(TAG, "---->register network state excep:" + e.getMessage());
        }

    }

    /**
     * 退出
     */
    public void exitLogManager() {
        if (mPollingImpl != null)
            mPollingImpl.stopPolling();
        BusinessLogDataHelper.BusinessLogDataObservable.unregister(this);
        BusinessPollingImpl.BusinessPollingObservable.unregister(this);
        unregisterNetworkStateReceiver();
        saveMemoryLog();
        sInstance = null;
    }

    private void saveMemoryLog() {
        Iterator<Map.Entry<Integer, IBusinessLogStrategy>> it = mLogStrategyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, IBusinessLogStrategy> entry = it.next();
            entry.getValue().saveLog();
        }
    }

    public void unregisterNetworkStateReceiver() {
        Context c = BusinessRuntimeInfo.getInstance().getAppContext();
        if (c != null) {
            if (mNetworkReceiver != null) {
                try {
                    c.unregisterReceiver(mNetworkReceiver);
                } catch (Exception e) {
                    Log.i(TAG, "---->unregister network state excep:" + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onLogDataChange(int logType, int count) {
        mLogManagerImpl.onHandleLog(logType, BusinessConstant.LOG_COMMON_ACTION);
    }

    @Override
    public void onPollingBeat() {
        //TODO 轮训时间到达 直接给过
        Iterator<Map.Entry<Integer, IBusinessLogStrategy>> it = mLogStrategyMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, IBusinessLogStrategy> entry = it.next();
            int logType = entry.getKey();
            mLogManagerImpl.onHandleLog(logType, BusinessConstant.LOG_POLLING_ACTION);
        }
    }

    public IBusinessLogStrategy getLogStrategy(int logType) {
        IBusinessLogStrategy logStrategy = mLogStrategyMap.get(logType);
        if (logStrategy == null) {
            //TODO create load
            //no deal
            Log.d(TAG, "--->logType=" + logType + " straregy is null");
        }
        return logStrategy;
    }

}
