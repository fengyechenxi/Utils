package com.fycx.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 */

public class ContextUtils {

    private ContextUtils(){
        throw new AssertionError();
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    public static Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) object;
            return fragment.getActivity();
        }
        else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

}
