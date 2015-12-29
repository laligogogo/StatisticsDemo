package com.didi.business.net;

import com.didi.business.model.BusinessBaseObject;

/**
 * 网络请求响应类
 */
abstract public class BusinessResponseListener<T extends BusinessBaseObject> {

    /**
     * 请求成功且返回合法数据
     * 
     * @param t
     */
    public void onSuccess(T t) {
    }

    /**
     * 
     * 请求返回非法错误码,指服务器返回的错误码 ，即errno=0、errno=101等
     * 
     * @param t
     */
    public void onError(T t) {
    }

    /**
     * 
     * 请求网络失败，是指ResponseCode，即 200、404、502等
     * 
     * @param t
     */
    public void onFail(T t) {
    }

    /**
     * 
     * 网络请求处理完成<br/>
     * 在onSuccess、onError、onFail执行完成之后被调用,里面的错误码包含服务器返回的代码和ResponseCode
     * 
     * @param t
     */
    public void onFinish(T t) {

    }

}
