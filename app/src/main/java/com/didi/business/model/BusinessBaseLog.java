package com.didi.business.model;

import java.io.Serializable;

/**
 * Created by huhuajun on 2015/10/16.
 * 日志基类
 */
public class BusinessBaseLog implements IBusinessDataJsonInterface, Serializable, Cloneable {

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
