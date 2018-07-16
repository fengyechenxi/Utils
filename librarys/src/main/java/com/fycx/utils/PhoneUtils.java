package com.fycx.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.view.ViewConfiguration;

import java.util.Locale;

/**
 * 手机信息获取工具
 * Created by Administrator on 2017/9/28 0028.
 */

public class PhoneUtils {

    private PhoneUtils(){
        throw new AssertionError();
    }

    /**
     * 判断是否为平板设备
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 是否有相机功能
     * @param context
     * @return
     */
    public static boolean hasCamera(Context context) {
        PackageManager pckMgr = context.getPackageManager();
        boolean flag = pckMgr
                .hasSystemFeature("android.hardware.camera.front");
        boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
        boolean flag2;
        flag2 = flag || flag1;
        return flag2;
    }


    /**
     * 是否有硬件menu
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag;
        if (Build.VERSION.SDK_INT < 11)
            flag = true;
        else if (Build.VERSION.SDK_INT >= 14) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else
            flag = false;
        return flag;
    }

    /**
     * 是否有网络功能
     */
    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /**
     * 判断是否为中文环境
     */
    public static boolean isZhCN(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        String lang = sysLocale.getCountry();
        return lang.equalsIgnoreCase("CN");
    }


    /**
     * 判断 SD Card 是否 ready
     */
    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 获取当前国家的语言
     */
    public static String getCurCountryLan(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            //noinspection deprecation
            sysLocale = config.locale;
        }
        return sysLocale.getLanguage()
                + "-"
                + sysLocale.getCountry();
    }

    /**
     * 获取IMEI
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getImei(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
    }


    /**
     * (修订版本列表)
     * @return
     */
    public static String getID(){
        return Build.ID;
    }
    /**
     * (设备参数)
     * @return
     */
    public static String getDeviceParams(){
        return Build.DEVICE;
    }

    /**
     * (android的sdk版本号)
     * @return
     */
    public static int getSdkInt(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * (编译时间)
     * @return
     */
    public static long getCompileTime(){
       return Build.TIME;
    }
    /**
     * （android系统定制商）
     * 品牌
     * The consumer-visible brand with which the product/hardware
     * @return
     */
    public static String getBrand(){
        return Build.BRAND;

    }

    /**
     *整个产品的名称
     * @return
     */
    public static String getProduct(){
        return Build.PRODUCT;

    }

    /**
     * 显示屏参数
     * @return
     */
    public static String getDisplay(){
        return Build.DISPLAY;

    }

    /**
     * 硬件制造商
     * @return
     */
    public static String getManufacturer(){
        return Build.MANUFACTURER;

    }

    /**
     * （boos 版本）
     * The system bootloader version number.
     * @return
     */
    public static String getBootLoader(){
        return Build.BOOTLOADER;
    }

    /**
     *手机型号
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     *系统版本
     * @return
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     *SDK版本
     * @return
     */
    public static String getSdk() {
        return Build.VERSION.SDK;
    }

    /**
     * (主板名称)
     * @return
     */
   public static String getBoard(){
       return Build.BOARD;
   }



}
