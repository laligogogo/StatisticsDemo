package com.didi.business.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class BusinessDeviceUtils {

    public static String getMobileModel() {
        try {
            String mobileModel = android.os.Build.MODEL;
            return mobileModel;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static String getMobileOs() {
        try {
            String sdkversion = android.os.Build.VERSION.SDK;
            return sdkversion;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCpuType() {
        try {
            String cpu = android.os.Build.CPU_ABI;
            return cpu;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSimSerialNumber(Context context) {
        String simSerialNumber = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simSerialNumber = tm.getSimSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simSerialNumber;
    }

    public static String getSimOperatorName(Context context) {
        String simOperatorName = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simOperatorName = tm.getSimOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simOperatorName;
    }

    public static int getPhoneType(Context context) {
        int phoneType = 0;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            phoneType = tm.getPhoneType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneType;
    }

    public static String getNetworkOperatorName(Context context) {
        String networkOperatorName = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            networkOperatorName = tm.getNetworkOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networkOperatorName;
    }

    public static String getDeviceId(Context context) {
        String deviceId = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    public static String getImsi(Context context) {
        String imsi = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imsi;
    }

    public static String getMobileIP(Context ctx) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
        // 实例化mActiveNetInfo对象
        NetworkInfo mActiveNetInfo = mConnectivityManager.getActiveNetworkInfo();// 获取网络连接的信息
        if (mActiveNetInfo == null) {
            return null;
        } else {
            return getIp(mActiveNetInfo);
        }
    }

    // 获取本地IP函数
    private static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration.hasMoreElements();) {
                NetworkInterface intf = mEnumeration.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    // 如果不是回环地址
                    if (!inetAddress.isLoopbackAddress()) {
                        // 直接返回本地IP地址
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // 显示IP信息
    private static String getIp(NetworkInfo mActiveNetInfo) {
        // 如果是WIFI网络
        if (mActiveNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return getLocalIPAddress();
        }
        // 如果是手机网络
        else if (mActiveNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return getLocalIPAddress();
        } else {
            return null;
        }

    }

    /**
     * 获取网络类型
     * 
     * @return
     */
    public static String getNetworkType(Context c) {
        String name = "UNKNOWN";

        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return "WIFI";
            }
        }

        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return name;
        }

        int type = tm.getNetworkType();
        switch (type) {
        case TelephonyManager.NETWORK_TYPE_GPRS:
        case TelephonyManager.NETWORK_TYPE_EDGE:
        case TelephonyManager.NETWORK_TYPE_CDMA:
        case TelephonyManager.NETWORK_TYPE_1xRTT:
        case TelephonyManager.NETWORK_TYPE_IDEN:
            name = "2G";
            break;
        case TelephonyManager.NETWORK_TYPE_UMTS:
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
        case TelephonyManager.NETWORK_TYPE_HSDPA:
        case TelephonyManager.NETWORK_TYPE_HSUPA:
        case TelephonyManager.NETWORK_TYPE_HSPA:
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
        case TelephonyManager.NETWORK_TYPE_EHRPD:
        case TelephonyManager.NETWORK_TYPE_HSPAP:
            name = "3G";
            break;
        case TelephonyManager.NETWORK_TYPE_LTE:
            name = "4G";
            break;
        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            name = "UNKNOWN";
            break;
        default:
            name = "UNKNOWN";
            break;
        }
        return name;
    }

    /**
     * 获取网络类型
     * 1:2g；2:3g； 3: 4g； 4:wifi
     * @return
     */
    public static int getNetworkType_Int(Context c) {
        int name = -1;

        ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                return 4;
            }
        }

        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return name;
        }

        int type = tm.getNetworkType();
        switch (type) {
        case TelephonyManager.NETWORK_TYPE_GPRS:
        case TelephonyManager.NETWORK_TYPE_EDGE:
        case TelephonyManager.NETWORK_TYPE_CDMA:
        case TelephonyManager.NETWORK_TYPE_1xRTT:
        case TelephonyManager.NETWORK_TYPE_IDEN:
            name = 1;
            break;
        case TelephonyManager.NETWORK_TYPE_UMTS:
        case TelephonyManager.NETWORK_TYPE_EVDO_0:
        case TelephonyManager.NETWORK_TYPE_EVDO_A:
        case TelephonyManager.NETWORK_TYPE_HSDPA:
        case TelephonyManager.NETWORK_TYPE_HSUPA:
        case TelephonyManager.NETWORK_TYPE_HSPA:
        case TelephonyManager.NETWORK_TYPE_EVDO_B:
        case TelephonyManager.NETWORK_TYPE_EHRPD:
        case TelephonyManager.NETWORK_TYPE_HSPAP:
            name = 2;
            break;
        case TelephonyManager.NETWORK_TYPE_LTE:
            name = 3;
            break;
        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            name = -1;
            break;
        default:
            name = -1;
            break;
        }
        return name;
    }

    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService("wifi");

            WifiInfo info = (null == wifi ? null : wifi.getConnectionInfo());
            String mac = info.getMacAddress();
            if (null != info) {
                mac = info.getMacAddress();
            }
            return mac;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到手机的IMEI号
     *
     * @return
     */
    public static String getImei(Context c) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyMgr.getDeviceId() == null) {
            return "";
        } else
            return mTelephonyMgr.getDeviceId();
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            if (context != null) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
                if (cm != null) {
                    try {
                        if ((cm.getActiveNetworkInfo() != null) && (cm.getActiveNetworkInfo().isAvailable()))
                            return true;
                    } catch (Exception e) {
                        return false;
                    }
                }

                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    /**
     * 获取屏幕宽度，单位像素
     * 
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (sScreenWidth > 0) {
            return sScreenWidth;
        }

        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        // 窗口的宽度
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenWidth;
    }

    /**
     * 获取屏幕高度，单位像素
     * 
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (sScreenHeight > 0) {
            return sScreenHeight;
        }

        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
        return sScreenHeight;
    }

}
