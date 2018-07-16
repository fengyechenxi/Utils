package com.fycx.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

/**
 * 界面切换工具类
 */
public class PageSwitchUtils {

    private PageSwitchUtils(){
        throw new AssertionError();
    }

    public static void startActivity(Activity context, Class<?> cls,@AnimRes int open_in,@AnimRes int open_out){
        Intent intent = new Intent(context,cls);
        startActivity(context,intent,open_in,open_out);
    }

    public static void startActivity(Activity context, Intent intent,@AnimRes int open_in,@AnimRes int open_out){
        context.startActivity(intent);
        openTransition(context,open_in,open_out);
    }

    /**
     * 启动Activity，然后关闭当前Activity
     * @param context
     * @param intent
     */
    public static void startActivityAndFinishSelf(Activity context, Intent intent,@AnimRes int open_in,@AnimRes int open_out){
        context.startActivity(intent);
        context.finish();
        openTransition(context,open_in,open_out);
    }


    public static void startActivityAndFinishSelf(Activity context, Class<?> cls,@AnimRes int open_in,@AnimRes int open_out){
        Intent intent = new Intent(context,cls);
        startActivityAndFinishSelf(context,intent,open_in,open_out);
    }


    public static void startActivityForResult(Activity context, Intent intent, int requestCode,@AnimRes int open_in,@AnimRes int open_out){
        context.startActivityForResult(intent,requestCode);
        openTransition(context,open_in,open_out);
    }

    public static void startActivityForResult(Activity context, Class<?> cls, int requestCode,@AnimRes int open_in,@AnimRes int open_out){
        Intent intent = new Intent(context,cls);
        context.startActivityForResult(intent,requestCode);
        openTransition(context,open_in,open_out);
    }

    /**
     * 给fragment添加跳转动画
     * @param transaction
     * @return
     */
    public static FragmentTransaction setFragmentTransitionAnimations(FragmentTransaction transaction,@AnimRes int open_in,@AnimRes int open_out,@AnimRes int exit_in,@AnimRes int exit_out){
        return transaction.setCustomAnimations(open_in,open_out,exit_in,exit_out);
    }

    private static void openTransition(Activity context, @AnimRes int open_in,@AnimRes int open_out){
        context.overridePendingTransition(open_in,open_out);
    }

    public static void exitTransition(Activity context,@AnimRes int exit_in,@AnimRes int exit_out){
        context.overridePendingTransition(exit_in,exit_out);
    }



}
