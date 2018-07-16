package com.fycx.utils;

import android.content.Context;
import android.os.Environment;

import java.math.BigDecimal;

public class CacheUtils {

    private CacheUtils(){
        throw new AssertionError();
    }

    private static final int BYTE = 1024;

    /**
     * Formatting unit
     *
     * @param size files size
     * @return Format files size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / BYTE;
        if (kiloByte < 1) {
            return Double.toString(size) + "B";
        }

        double megaByte = kiloByte / BYTE;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / BYTE;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / BYTE;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    /**
     * Gets the cache file size
     *
     * @param context context
     * @return Cache file size
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = FileUtils.getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += FileUtils.getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * Clear the application cache file
     *
     * @param context context
     */
    public static void clearAllCache(Context context) {
        FileUtils.deleteFile(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteFile(context.getExternalCacheDir());
        }
    }

}
