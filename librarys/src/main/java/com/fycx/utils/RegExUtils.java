package com.fycx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * Created by Administrator on 2017/9/28 0028.
 */

public class RegExUtils {

    private RegExUtils(){
        throw new AssertionError();
    }

    /**
     * email
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
     * @param n
     * @param digits
     * @return
     */
    public static boolean isPureDigit(int n,String digits) {
        Pattern p = Pattern.compile("^\\d{"+n+"}");
        Matcher m = p.matcher(digits);
        return m.matches();
    }


    /**
     * from位数，到to位数的数字和字母组合
     * @param from
     * @param to
     * @param text
     * @return
     */
    public static boolean isNumberAndLetterCombine(int from,int to,String text) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{"+from+","+to+"}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     *6-20位字母与数字组合
     * @param password
     * @return
     */
    public static boolean is6To20NumberAndLetterPassword(String password) {
        return isNumberAndLetterCombine(6,20,password);
    }


    /**
     * 小数
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
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?//d+(\\.\\d*)?|\\.\\d+$");
        return pattern.matcher(str).matches();
    }


    /**
     * 手机号码
     * @param mPhone
     * @return
     */
    public static boolean isMobileNumber(String mPhone) {
        Pattern p = Pattern.compile("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
        Matcher m = p.matcher(mPhone);
        return m.matches();
    }


    /**
     * 身份证判断
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
     * @param amt
     * @return
     */
    public static boolean isAmount(String amt) {
        // 判断小数点后一位的数字的正则表达式
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match=pattern.matcher(amt);
        return match.matches();
    }


    /**
     * 有效的银行卡号位数判断
     * @param bankNo
     * @return
     */
    public static boolean isValidBankCardNoLength(String bankNo) {
        Pattern p = Pattern.compile("^\\d{14,19}");
        Matcher m = p.matcher(bankNo);
        return m.matches();
    }


}
