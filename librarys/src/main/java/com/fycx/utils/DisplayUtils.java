package com.fycx.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 分辨率转换工具
 * Created by Administrator on 2017/9/28 0028.
 */

public class DisplayUtils {

    public static final int UNIT_DIP = TypedValue.COMPLEX_UNIT_DIP;
    public static final int UNIT_SP = TypedValue.COMPLEX_UNIT_SP;
    public static final int UNIT_PX = TypedValue.COMPLEX_UNIT_PX;

    /**
     * 是否有摄像头
     */
    private static Boolean sHasCamera = null;
    /**
     * 屏幕密度
     */
    public static float sDensity = 0f;


    private DisplayUtils(){
        throw new AssertionError();
    }


    /**
     * 获取 DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static float getDensity(Context context) {
        if (sDensity == 0f) {
            sDensity = getDisplayMetrics(context).density;
        }
        return sDensity;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }


    /**
     * 单位转换: dp -> px
     */
    public static int dp2px(Context context, int dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    /**
     * 单位转换:px -> dp
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / getDensity(context) + 0.5);
    }

    // 将px值转换为sp值，保证文字大小不变
    public static int px2sp(Context context, float pxValue) {
        float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将sp值转换为px值，保证文字大小不变
    public static int sp2px(Context context, float spValue) {
        float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 判断是否有状态栏
     */
    public static boolean hasStatusBar(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        return true;
    }


    /**
     * 获取ActionBar高度
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 获取状态栏高度
     */
    @SuppressLint("PrivateApi")
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取虚拟菜单的高度,若无则返回0
     */
    public static int getNavMenuHeight(Context context) {
        return getRealScreenSize(context)[1] - getScreenHeight(context);
    }


    /**
     * 获取屏幕的真实宽高
     */
    public static int[] getRealScreenSize(Context context) {
        int[] size = new int[2];
        int widthPixels, heightPixels;
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        try {
            // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
            widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
            heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
        } catch (Exception ignored) {
        }
        try {
            // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        } catch (Exception ignored) {
        }

        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;

    }


    /**
     * 设置全屏
     */
    public static void setFullScreen(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(params);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    /**
     * 取消全屏
     */
    public static void cancelFullScreen(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(params);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 判断是否全屏
     */
    public static boolean isFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }


    public static boolean isElevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

}
