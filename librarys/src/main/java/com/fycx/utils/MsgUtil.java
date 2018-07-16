package com.fycx.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 短信的工具类
 */
public class MsgUtil {

    private MsgUtil(){
        throw new AssertionError();
    }
    /**
     * 调用系统发短信界面
     * @param activity    Activity
     * @param phoneNumber 手机号码
     * @param smsContent  短信内容
     */
    public static void sendMessage(Context activity, String phoneNumber, String smsContent) {
        if (smsContent == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", smsContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
