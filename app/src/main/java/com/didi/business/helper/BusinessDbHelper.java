package com.didi.business.helper;

import android.content.Context;

import com.didi.business.model.BusinessRuntimeInfo;
import com.third.lidroid.xutils.DbUtils;

public class BusinessDbHelper {

    private static final String DB_NAME = "business_log.db";

    private DbUtils sDbUtils;
    private static BusinessDbHelper sInstance;
    private Context mContext;

    private BusinessDbHelper(Context context) {
        mContext = context;
        createDbUtils();
    }

    public static BusinessDbHelper getInstance() {
        if (sInstance == null) {
            synchronized (BusinessDbHelper.class) {
                if (sInstance == null) {
                    sInstance = new BusinessDbHelper(BusinessRuntimeInfo.getInstance().getAppContext());
                }
            }
        }
        return sInstance;
    }

    private void createDbUtils() {
        sDbUtils = DbUtils.create(defaultDaoConfig());
        sDbUtils.configAllowTransaction(true);
    }

    private DbUtils.DaoConfig defaultDaoConfig(){
        DbUtils.DaoConfig daoConfig = new DbUtils.DaoConfig(mContext);
        daoConfig.setDbName(DB_NAME);
        daoConfig.setDbVersion(1);
        return daoConfig;
    }

    public DbUtils getDbUtils() {
        return sDbUtils;
    }


}
