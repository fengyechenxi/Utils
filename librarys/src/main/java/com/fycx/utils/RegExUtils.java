package com.fycx.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by Administrator on 2017/9/28 0028.
 */

public class RegExUtils {


    /**
     * email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("/^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$/");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * N位纯数字
     *
     * @param n
     * @param digits
     * @return
     */
    public static boolean isPureDigit(int n, String digits) {
        Pattern p = Pattern.compile("^\\d{" + n + "}");
        Matcher m = p.matcher(digits);
        return m.matches();
    }


    /**
     * from位数，到to位数的数字和字母组合
     *
     * @param from
     * @param to
     * @param text
     * @return
     */
    public static boolean isNumberAndLetterCombine(int from, int to, String text) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{" + from + "," + to + "}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * 6-20位字母与数字组合
     *
     * @param password
     * @return
     */
    public static boolean is6To20NumberAndLetterPassword(String password) {
        return isNumberAndLetterCombine(6, 20, password);
    }


    /**
     * 小数
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if (str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * double型的浮点数
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?//d+(\\.\\d*)?|\\.\\d+$");
        return pattern.matcher(str).matches();
    }



    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     * @param str
     * @return 待检测的字符串
     */
    public static boolean isMobileNumber(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 号段：166
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189,198,199
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(166)|(18[0-9])|(17[0,1,3,5,6,7,8])|(19[8-9]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    /**
     * 身份证判断
     *
     * @param idNo
     * @return
     */
    public static boolean isIdentityCardNo(String idNo) {
        Pattern p = Pattern.compile("\\d{15}|\\d{17}[0-9Xx]");
        Matcher m = p.matcher(idNo);
        return m.matches();
    }


    /**
     * 验证金额
     *
     * @param amt
     * @return
     */
    public static boolean isAmount(String amt) {
        // 判断小数点后一位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match = pattern.matcher(amt);
        return match.matches();
    }


    /**
     * 有效的银行卡号位数判断
     *
     * @param bankNo
     * @return
     */
    public static boolean isValidBankCardNoLength(String bankNo) {
        Pattern p = Pattern.compile("^\\d{14,19}");
        Matcher m = p.matcher(bankNo);
        return m.matches();
    }


}
