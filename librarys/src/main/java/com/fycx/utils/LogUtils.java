package com.fycx.utils;

import android.util.Log;

/**
 * Log日志工具类
 */
public class LogUtils {

    public static boolean Debug = true;

    private LogUtils() {
        throw new AssertionError();
    }

    public static void setDebug(boolean debug) {
        Debug = debug;
    }

    /**
     * 打印information日志
     *
     * @param tag 标签
     * @param msg 日志信息
     */
    public static void i(String tag, String msg) {
        if(!Debug)return;
        Log.i(tag, msg);
    }

    /**
     * 打印information日志
     *
     * @param tag       标签
     * @param msg       日志信息
     * @param throwable 异常
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if(!Debug)return;
        Log.i(tag, msg, throwable);
    }

    /**
     * 打印verbose日志
     *
     * @param tag 标签
     * @param msg 日志信息
     */
    public static void v(String tag, String msg) {
        if(!Debug)return;
        Log.v(tag, msg);
    }

    /**
     * 打印verbose日志
     *
     * @param tag       标签
     * @param msg       日志信息
     * @param throwable 异常
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if(!Debug)return;
        Log.v(tag, msg, throwable);
    }

    /**
     * 打印debug信息
     *
     * @param tag 标签信息
     * @param msg 日志信息
     */
    public static void d(String tag, String msg) {
        if(!Debug)return;
        Log.d(tag, msg);
    }

    /**
     * 打印debug日志
     *
     * @param tag       标签信息
     * @param msg       日志信息
     * @param throwable 异常
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if(!Debug)return;
        Log.d(tag, msg, throwable);
    }

    /**
     * 打印warn日志
     *
     * @param tag 标签信息
     * @param msg 日志信息
     */
    public static void w(String tag, String msg) {
        if(!Debug)return;
        Log.w(tag, msg);
    }

    /**
     * 打印warn日志
     *
     * @param tag       标签信息
     * @param msg       日志信息
     * @param throwable 异常
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if(!Debug)return;
        Log.w(tag, msg, throwable);
    }

    /**
     * 打印error日志
     *
     * @param tag 标签
     * @param msg 日志信息
     */
    public static void e(String tag, String msg) {
        if(!Debug)return;
        Log.e(tag, msg);
    }

    /**
     * 打印error日志
     *
     * @param tag       标签
     * @param msg       日志信息
     * @param throwable 异常
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if(!Debug)return;
        Log.e(tag, msg, throwable);
    }
}