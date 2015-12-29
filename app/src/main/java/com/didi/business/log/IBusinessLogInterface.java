package com.didi.business.log;

/**
 * Created by huhuajun on 2015/10/15.
 */
public interface IBusinessLogInterface {

    /**
     *
     * @param logType 日志类型
     * @param action 触发条件
     */
    public void onHandleLog(int logType,int action);

}
