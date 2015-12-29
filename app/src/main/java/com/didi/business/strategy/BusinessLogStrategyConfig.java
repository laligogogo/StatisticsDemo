package com.didi.business.strategy;

/**
 * Created by huhuajun on 2015/10/16.
 */
public class BusinessLogStrategyConfig {

    /**
     * 日志条目数
     */
    private int mCount;

    //-----net work--------//
    private boolean mWifiEnable = true;
    private boolean m2GEnable = true;
    private boolean m3GEnable = true;
    private boolean m4GEnable = true;

    private BusinessLogStrategyConfig(Builder builder) {
        mCount = builder.count;
        mWifiEnable = builder.wifiEnable;
        m2GEnable = builder.enable2G;
        m3GEnable = builder.enable3G;
        m4GEnable = builder.enable4G;
    }

    public int getCount() {
        return mCount;
    }

    public boolean wifiEnable() {
        return mWifiEnable;
    }

    public boolean g2Enable() {
        return m2GEnable;
    }

    public boolean g3Enable() {
        return m3GEnable;
    }

    public boolean g4Enable() {
        return m4GEnable;
    }


    public static class Builder {

        private int count = 5;
        //-----net work--------//
        private boolean wifiEnable = true;
        private boolean enable2G = true;
        private boolean enable3G = true;
        private boolean enable4G = true;

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder wifiEnable(boolean enable) {
            wifiEnable = enable;
            return this;
        }

        public Builder enable2G(boolean enable) {
            enable2G = enable;
            return this;
        }

        public Builder enable3G(boolean enable) {
            enable3G = enable;
            return this;
        }

        public Builder enable4G(boolean enable) {
            enable4G = enable;
            return this;
        }


        public BusinessLogStrategyConfig build() {
            return new BusinessLogStrategyConfig(this);
        }
    }

}
