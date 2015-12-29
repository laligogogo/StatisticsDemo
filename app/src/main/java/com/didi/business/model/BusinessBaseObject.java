package com.didi.business.model;

import android.text.TextUtils;

import com.didi.business.common.BusinessNetConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 所以数据模型的基类
 */
@SuppressWarnings("serial")
public class BusinessBaseObject implements Serializable, Cloneable {
    protected int errno = BusinessNetConstant.NO_DATA;
    protected String errmsg;

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getErrorCode() {
        return errno;
    }

    /**
     * 更新错误码
     *
     * @param error 错误码
     */
    public void setErrorCode(int error) {
        errno = error;
    }

    /**
     * 数据是否可用
     *
     * @return 数据是否可用
     */
    public boolean isAvailable() {
        return errno == BusinessNetConstant.OK;
    }

    /**
     * 获取错误描述<br/>
     * <p>
     * 该错误描述字符串有可能为空
     *
     * @return 错误描述
     */
    public String getErrorMsg() {
        return errmsg;
    }

    public void setErrorMsg(String msg) {
        errmsg = msg;
    }

    /**
     * 解析json数据
     *
     * @param json
     */
    public void parse(String json) {
        if (TextUtils.isEmpty(json)) {
            setErrorCode(BusinessNetConstant.ERROR_INVALID_DATA_FORMAT);
            return;
        }
        JSONObject obj;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            setErrorCode(BusinessNetConstant.ERROR_INVALID_DATA_FORMAT);
            return;
        }
        if (obj.has(BusinessNetConstant.ERROR_CODE))
            setErrorCode(obj.optInt(BusinessNetConstant.ERROR_CODE));

        if (obj.has(BusinessNetConstant.ERROR_MSG)) {
            setErrorMsg(obj.optString(BusinessNetConstant.ERROR_MSG));
        }
        if (TextUtils.isEmpty(errmsg) || TextUtils.isDigitsOnly(errmsg))
            errmsg = null;

        parse(obj);
    }

    /**
     * 解析数据对象<br/>
     * 由子类实现
     *
     * @param jsonObj
     */
    protected void parse(JSONObject jsonObj) {
        // to do nothing.
    }

    @Override
    public String toString() {
        return "BusinessBaseObject [errno=" + errno + ", errmsg=" + errmsg + "]";
    }

    @Override
    public BusinessBaseObject clone() {
        BusinessBaseObject obj = null;
        try {
            obj = (BusinessBaseObject) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 判断数据模型是否可用
     *
     * @param obj
     * @return
     */
    public static boolean isAvailable(BusinessBaseObject obj) {
        return obj != null && obj.isAvailable();
    }

}