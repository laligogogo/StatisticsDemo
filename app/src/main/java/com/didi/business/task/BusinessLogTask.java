package com.didi.business.task;

import com.didi.business.log.BusinessLogManager;
import com.didi.business.strategy.IBusinessLogStrategy;

/**
 * Created by huhuajun on 2015/10/15.
 */
public class BusinessLogTask implements Runnable {

    //上传日志
    private int mLogType;

    public BusinessLogTask(int logType) {
        mLogType = logType;
    }

    @Override
    public void run() {
        //TODO 根据不同的数据类型做不同的日志处理
        IBusinessLogStrategy logStrategy = BusinessLogManager.getInstance().getLogStrategy(mLogType);
        if (logStrategy != null) {
            logStrategy.uploadLog();
        }
    }


}
