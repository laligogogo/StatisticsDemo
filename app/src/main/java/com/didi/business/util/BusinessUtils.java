package com.didi.business.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by huhuajun on 2015/10/15.
 */
public final class BusinessUtils {

    /**
     * 获取当前版本号
     */
    public static String getCurrentVersion(Context c) {
        String pkgName = c.getPackageName();
        try {
            PackageInfo pinfo = c.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionName;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 得到手机当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean getNetworkEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        boolean flag = false;
        if (activeNetInfo != null) {
            flag = connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

}
