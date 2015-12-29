package com.didi.business.model;

public enum BusinessProductChannel {

    /**
     * 滴滴打车乘客端
     */
    PRODUCT_DIDI_CUSTOMER(1, "滴滴打车乘客端"),
    /**
     * 滴滴打车司机端
     */
    PRODUCT_DIDI_DRIVER(2, "滴滴打车司机端"),
    /**
     * 快的打车乘客端
     */
    PRODUCT_KUAIDI_CUSTOMER(3, "快的打车乘客端"),
    /**
     * 快的打车司机端
     */
    PRODUCT_KUAIDI_DRIVER(4, "快的打车司机端"),
    /**
     * 一号专车乘客端
     */
    PRODUCT_KDVIP_CUSTOMER(5, "一号专车乘客端"),
    /**
     * 一号专车司机端
     */
    PRODUCT_KDVIP_DRIVER(6, "一号专车司机端"),

    UNKNOWN(-1, "未知产品");

    private int mProductIndex;
    private String mName;

    BusinessProductChannel(int channel, String name) {
        mProductIndex = channel;
        mName = name;
    }

    public static BusinessProductChannel valueOf(int channel) {
        switch (channel) {
            case 1:
                return PRODUCT_DIDI_CUSTOMER;
            case 2:
                return PRODUCT_DIDI_DRIVER;
            case 3:
                return PRODUCT_KUAIDI_CUSTOMER;
            case 4:
                return PRODUCT_KUAIDI_DRIVER;
            case 5:
                return PRODUCT_KDVIP_CUSTOMER;
            case 6:
                return PRODUCT_KDVIP_DRIVER;
            default:
                return UNKNOWN;
        }
    }

    public int value() {
        return mProductIndex;
    }

    public String getName() {
        return mName;
    }

}
