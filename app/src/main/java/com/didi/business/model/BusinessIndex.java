package com.didi.business.model;

/**
 * Created by huhuajun on 2015/10/15.
 */
public enum BusinessIndex {

    Business_Common(1, "公共"),
    Business_Taxi(2, "出租车"),
    Business_Car(3, "专车"),
    Business_Flier(4, "快车"),
    Business_Bts(5, "顺风车"),
    Business_DDriver(6, "代驾"),
    Business_Bus(7, "大巴"),
    Business_Nova(8, "试驾"),
    Business_Unknown(-1, "未知");

    private String mTypeName;
    private int mTypeValue;

    BusinessIndex(int typeValue, String typeName) {
        mTypeValue = typeValue;
        mTypeName = typeName;
    }

    public int value() {
        return mTypeValue;
    }

    public String businessName(){
        return mTypeName;
    }

    public static BusinessIndex valueOf(int businessIndex) {
        switch (businessIndex){
            case 1:
                return Business_Common;
            case 2:
                return Business_Taxi;
            case 3:
                return Business_Car;
            case 4:
                return Business_Flier;
            case 5:
                return Business_Bts;
            case 6:
                return Business_DDriver;
            case 7:
                return Business_Bus;
            case 8:
                return Business_Nova;
            default:
                return Business_Unknown;

        }
    }


}
