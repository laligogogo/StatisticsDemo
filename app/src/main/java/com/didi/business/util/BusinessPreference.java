package com.didi.business.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.didi.business.model.BusinessRuntimeInfo;

/**
 * Preference for didi sdk
 */
public class BusinessPreference {

    // private static final File PREFERENCE_FILE = new File("/data/system/didi_sdk_preference.xml");
    /**
     * ****************** single Instance **********************
     */

    private static BusinessPreference sInstance;

    /**
     * This is singleton , pass an application context to avoid memory leak
     *
     * @return
     */
    public static BusinessPreference getInstance() {

        if (sInstance == null) {
            synchronized (BusinessPreference.class) {
                if (sInstance == null) {
                    sInstance = new BusinessPreference(BusinessRuntimeInfo.getInstance().getAppContext());
                }
            }
        }
        return sInstance;
    }

    private BusinessPreference(Context context) {
        mContext = context.getApplicationContext();
        ensureSdkPreference();
    }

    /**
     * ****************** single Instance **********************
     */

    private static final String SP_NAME = "business_preference";

    private Context mContext;
    private SharedPreferences mSp;

    private void ensureSdkPreference() {
        if (mSp == null) {
            mSp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            // mSp = new SharedPreferencesImpl(PREFERENCE_FILE, Context.MODE_PRIVATE);
        }
    }

}
