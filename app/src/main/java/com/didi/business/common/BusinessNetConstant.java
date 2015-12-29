package com.didi.business.common;

/**
 * 网络标记常量
 */
public class BusinessNetConstant {

    /**
     * 错误码
     */
    public static final String ERROR_CODE = "errno";
    /**
     * 错误描述
     */
    public static final String ERROR_MSG = "errmsg";
    /**
     * 下载失败或网络异常
     */
    public static final int MSG_ERROR = -1;
    /**
     * 表示服务器无响应
     */
    public static final int SOCKE_TTIMEOUT = -2;
    /**
     * 表示链接服务器超时
     */
    public static final int CONNECT_TIMEOUT = -3;
    /**
     * 表示访问地址不存在或者网已断
     */
    public static final int HTTP_HOST_CONNECT = -4;
    /**
     * 网络链接失败
     */
    public static final int ERROR_NETWORK_FAIL = -5;
    /**
     * 连接网络超时时间
     */
    public static final int CONNECT_TIME_OUT = 10 * 1000;
    /**
     * 数据返回正常
     */
    public final static int OK = 0;
    /**
     * 无数据
     */
    public final static int NO_DATA = -800;
    /**
     * 数据格式错误
     */
    public static final int ERROR_INVALID_DATA_FORMAT = -900;
}
