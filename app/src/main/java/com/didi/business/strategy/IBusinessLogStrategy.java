package com.didi.business.strategy;

/**
 * Created by huhuajun on 2015/10/15.
 */
public interface IBusinessLogStrategy {

    /**
     * 顶层策略类
     * ---------广告策略类
     * ---------其他策略:暂无
     */

    /**
     * 检查是否满足log上传的策略
     *
     * @param action :触发方式
     * @return
     */
    public boolean onCheckLog(int action);

    /**
     * 用于上传日志
     * 不用的日志上传不一样如：文件，json。。。
     */
    public void uploadLog();

    /**
     * 程序退出时候调用
     * 保存在本地
     */
    public void saveLog();

    /**
     * 程序启动时用于读取本地的日志
     */
    public void loadLog();

}
