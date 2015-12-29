package com.didi.business.model;

import android.content.Context;

/**
 * Created by huhuajun on 2015/10/15.
 */
public class BusinessRuntimeInfo {

    private static BusinessRuntimeInfo sInstance;

    public static BusinessRuntimeInfo getInstance() {
        if (sInstance == null) {
            sInstance = new BusinessRuntimeInfo();
        }
        return sInstance;
    }

    private BusinessRuntimeInfo() {
    }

    /**
     * application context
     */
    private Context mContext;

    private BusinessProductChannel mProductIndex = BusinessProductChannel.UNKNOWN;

    public void setAppContext(Context c) {
        mContext = c;
    }

    public Context getAppContext() {
        return mContext;
    }

    public void setProductChannel(BusinessProductChannel channel) {
        mProductIndex = channel;
    }

    public BusinessProductChannel getProductChannel() {
        return mProductIndex;
    }

}