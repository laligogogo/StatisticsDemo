package com.didi.business.log;

import com.didi.business.strategy.IBusinessLogStrategy;
import com.didi.business.task.BusinessLogTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by huhuajun on 2015/10/15.
 */
public class BusinessLogInterfaceImpl implements IBusinessLogInterface {

    private static final String TAG = "BusinessLogInterfaceImpl";
    private static final int EXECUTOR_HANDLE_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;

    private int mAvailableProcessors = 1;
    //线程池
    ExecutorService mExecutorService;

    public BusinessLogInterfaceImpl() {
        initDefaultConfig();
    }

    private void initDefaultConfig() {
        mAvailableProcessors = Runtime.getRuntime().availableProcessors();
    }

    private void checkExecutor() {
        if (mExecutorService == null || mExecutorService.isShutdown()) {
            if (mAvailableProcessors < 0) {
                mAvailableProcessors = 1;
            }
            mExecutorService = Executors.newFixedThreadPool(
                    mAvailableProcessors, new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setPriority(EXECUTOR_HANDLE_THREAD_PRIORITY);
                            t.setName(TAG);
                            return t;
                        }
                    });
        }
    }

    @Override
    public void onHandleLog(int logType, int action) {
        //TODO 日志数据发生变化:用户新增日志 根据策略检查是否上传upload
        IBusinessLogStrategy logStrategy = BusinessLogManager.getInstance().getLogStrategy(logType);
        if (logStrategy != null) {
            if (logStrategy.onCheckLog(action)) {
                submitTask(new BusinessLogTask(logType));
            }
        }

    }

    private void submitTask(final Runnable runnable) {
        checkExecutor();
        mExecutorService.submit(runnable);
    }

}
