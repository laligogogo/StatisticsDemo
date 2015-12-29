package com.didi.business.net;

import com.didi.business.model.BusinessBaseObject;
import com.third.lidroid.xutils.HttpUtils;
import com.third.lidroid.xutils.http.RequestParams;
import com.third.lidroid.xutils.http.callback.RequestCallBack;
import com.third.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by huhuajun on 2015/10/14.
 */
public class BusinessRequest {

    private static final String BASE_URL = "http://ad.diditaxi.com.cn";

//    private static final String TEST_URL = "http://10.10.8.103:8602/v1/adevent";

    public static void uploadBusinessAdLog(String log, BusinessResponseListener<BusinessBaseObject> listener) {
        try {
            RequestParams params = new RequestParams();
            params.setContentType("application/json");
            params.setBodyEntity(new StringEntity(log, "UTF-8"));
            String url = BASE_URL + "/v1/adevent";
            new BusinessHttpRequest<BusinessBaseObject>().post(url, params, listener, new BusinessBaseObject());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void uploadBusinessAdLog(String log, RequestCallBack<String> callback) {
        try {
            RequestParams params = new RequestParams();
            params.setContentType("application/json");
            params.setBodyEntity(new StringEntity(log, "UTF-8"));
            String url = BASE_URL + "/v1/adevent";
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
