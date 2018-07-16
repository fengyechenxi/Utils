package com.fycx.utils;


import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;

/**
 * 网络工具类，包含网络的判断、跳转到设置页面
 */
public class NetWorkUtil {

    private NetWorkUtil(){
        throw new AssertionError();
    }

    /**
     * 判断当前是否有网络连接
     *
     * @param context
     * @return 有网络返回true；无网络返回false
     */
    @SuppressWarnings("null")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetWorkEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null || networkInfo.isConnected()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否为wifi
     *
     * @param context
     * @return 如果为wifi返回true；否则返回false
     */
    @SuppressWarnings("static-access")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.getType() == manager.TYPE_WIFI ? true : false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isMobileDataEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        isMobileDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }

    /**
     * 判断wifi 是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiDataEnable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = manager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    /**
     * 跳转到网络设置页面
     *
     * @param activity
     */
    public static void GoSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
