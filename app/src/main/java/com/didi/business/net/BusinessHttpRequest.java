package com.didi.business.net;

import com.didi.business.common.BusinessNetConstant;
import com.didi.business.model.BusinessBaseObject;
import com.third.lidroid.xutils.HttpUtils;
import com.third.lidroid.xutils.exception.HttpException;
import com.third.lidroid.xutils.http.RequestParams;
import com.third.lidroid.xutils.http.ResponseInfo;
import com.third.lidroid.xutils.http.callback.RequestCallBack;
import com.third.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * HTTP请求
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BusinessHttpRequest<T extends BusinessBaseObject> extends RequestCallBack<String> {
    private T t;
    private BusinessResponseListener listener;

    /**
     * 发起GET请求
     *
     * @param url      请求URL
     * @param listener 请求响应监听
     * @param t        请求数据模型对象
     */
    public void get(String url, BusinessResponseListener<T> listener, T t) {
        this.listener = listener;
        this.t = t;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, url, this);
    }

    /**
     * 发起POST请求
     *
     * @param url      请求URL
     * @param params   URL参数列表
     * @param listener 请求响应监听
     * @param t        请求数据模型对象
     */
    public void post(String url, RequestParams params, BusinessResponseListener<T> listener, T t) {
        this.listener = listener;
        this.t = t;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.POST, url, params, this);
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        t.parse(responseInfo.result);

        /* 自动校验结果 */
        if (listener == null) {
            return;
        }
        /* 非法数据 */
        if (!t.isAvailable()) {
            listener.onError(t);
            listener.onFinish(t);
            return;
        }
        /* 合法数据 */
        listener.onSuccess(t);
        listener.onFinish(t);
    }

    @Override
    public void onFailure(HttpException error, String msg) {
        t.setErrorCode(BusinessNetConstant.MSG_ERROR);
        t.setErrorMsg(msg);

        if (listener == null) {
            return;
        }
        listener.onFail(t);
        listener.onFinish(t);
    }

}
