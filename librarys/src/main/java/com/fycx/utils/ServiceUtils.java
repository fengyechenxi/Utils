package com.fycx.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * service处理的工具类
 */
public class ServiceUtils {

    private ServiceUtils(){
        throw new AssertionError();
    }

    /**
     * 判断名字为serviceName的Service是否正在运行
     * @param context
     * @param serviceName 需要判断的Service的完全路径包名
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
            if (runningServiceInfos.size() <= 0) {
                return false;
            }
            for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
                if (serviceInfo.service.getClassName().equals(serviceName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


}
