package com.fycx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;

/**
 * Bitmap工具类，获取Bitmap对象
 */
public class BitmapUtils {

    private BitmapUtils() {
        throw new AssertionError();
    }

    public static int[] getImageWidthHeight(String path){
        Options options = new Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }

    public static int[] getImageWidthHeight(Context context,@DrawableRes int drawable){
        Options options = new Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),drawable, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }

    /**
     * 根据资源id获取指定大小的Bitmap对象
     *
     * @param context 应用程序上下文
     * @param id      资源id
     * @param height  高度
     * @param width   宽度
     * @return
     */
    public static Bitmap getBitmapFromResource(Context context, int id, int height, int width) {
        Options options = new Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeResource(context.getResources(), id, options);
        options.inSampleSize = calculateSampleSize(height, width, options);
        options.inJustDecodeBounds = false;//加载到内存中
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
        return bitmap;
    }

    /**
     * 根据文件路径获取指定大小的Bitmap对象
     *
     * @param path   文件路径
     * @param height 高度
     * @param width  宽度
     * @return
     */
    public static Bitmap getBitmapFromFile(String path, int height, int width) {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("参数为空，请检查你选择的路径:" + path);
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateSampleSize(height, width, options);
        options.inJustDecodeBounds = false;//加载到内存中
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 获取指定大小的Bitmap对象
     *
     * @param bitmap Bitmap对象
     * @param height 高度
     * @param width  宽度
     * @return
     */
    public static Bitmap getThumbnailsBitmap(Bitmap bitmap, int height, int width) {
        if (bitmap == null) {
            throw new IllegalArgumentException("图片为空，请检查你的参数");
        }
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    /**
     * 将Bitmap对象转换成Drawable对象
     *
     * @param context 应用程序上下文
     * @param bitmap  Bitmap对象
     * @return 返回转换后的Drawable对象
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        if (context == null || bitmap == null) {
            throw new IllegalArgumentException("参数不合法，请检查你的参数");
        }
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    /**
     * 将Drawable对象转换成Bitmap对象
     *
     * @param drawable Drawable对象
     * @return 返回转换后的Bitmap对象
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable为空，请检查你的参数");
        }
        Bitmap bitmap =
                Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Bitmap对象转换为byte[]数组
     *
     * @param bitmap Bitmap对象
     * @return 返回转换后的数组
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap为空，请检查你的参数");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 计算所需图片的缩放比例
     *
     * @param height  高度
     * @param width   宽度
     * @param options options选项
     * @return
     */
    private static int calculateSampleSize(int height, int width, Options options) {
        int realHeight = options.outHeight;
        int realWidth = options.outWidth;
        int heigthScale = realHeight / height;
        int widthScale = realWidth / width;
        if (widthScale > heigthScale) {
            return widthScale;
        } else {
            return heigthScale;
        }
    }



    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }



    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }


    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

}