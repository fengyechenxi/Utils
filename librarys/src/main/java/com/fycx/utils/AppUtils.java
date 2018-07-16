package com.fycx.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Application处理的工具类
 */
public class AppUtils {

    private AppUtils(){
        throw new AssertionError();
    }
    /**
     * 判断包名为packageName的应用是否正在运行
     * @param context
     * @param packageName 要判断的应用的主包名
     * @return
     * 需要权限：android.permission.GET_TASKS
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
            if (list.size() <= 0) {
                return false;
            }
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.baseActivity.getPackageName().equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    /**
     * 判断应用是否处于后台
     * @param context
     * @return
     * 需要权限：android.permission.GET_TASKS
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                return topActivity != null && !topActivity.getPackageName().equals(context.getPackageName());
            }
        }
        return false;
    }

    /**
     * 重启App，并且杀死之前的进程
     * @param context
     * @return
     */
    public static void restartApp(Context context){
        Intent launch = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launch != null) {
            launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(launch);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    /**
     * 判断App是否已经安装
     * @param context
     * @param packageName App的主包名
     * @return
     */
    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 跳转到应用市场，通常用在评价反馈功能
     * @param context
     */
    public static void goAppMarket(Context context){
        Uri uri = Uri.parse("market://details?id="+context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 获取应用的版本号
     * @param ctx
     * @return
     */
    public static int getAppVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取应用的版本名称
     * @param ctx
     * @return
     */
    public static String getAppVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取所有安装的应用程序,不包含系统应用
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstalledPackages(Context context){
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<PackageInfo> packageInfoList  = new ArrayList<PackageInfo>();
        for(int i=0; i < packageInfos.size();i++){
            if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageInfoList.add(packageInfos.get(i));
            }
        }
        return packageInfoList;
    }


    /**
     * 获取应用程序的icon图标
     * @param context
     * @return
     * 当包名错误时，返回null
     */
    public static Drawable getApplicationIcon(Context context){
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}
