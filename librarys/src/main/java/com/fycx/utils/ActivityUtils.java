package com.fycx.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 关于Activity一些状态判断的工具类
 */
public class ActivityUtils {

    private ActivityUtils(){
        throw new AssertionError();
    }

    /**
     * 判断传入的activity是否处于应用最顶层
     * @param activity
     * @return
     * 需要权限：android.permission.GET_TASKS
     */
    public static boolean isTopActivity(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
            return appTask.size() > 0 && appTask.get(0).topActivity.equals(activity.getComponentName());
        }
        return false;
    }


    /**
     *判断Activity是否处于前台
     * @param context
     * @param className activity的完全路径名
     * @return
     * 需要权限：android.permission.GET_TASKS
     */
    public static boolean  isActivityInForeground(Context context, String activityClassName) {
        if (context == null || TextUtils.isEmpty(activityClassName)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (activityClassName.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Activity是否处于前台
     * @param activity
     * @return
     * 需要权限：android.permission.GET_TASKS
     */
    public static boolean  isActivityInForeground(Activity activity) {
        return isActivityInForeground(activity,activity.getClass().getName());
    }

}
