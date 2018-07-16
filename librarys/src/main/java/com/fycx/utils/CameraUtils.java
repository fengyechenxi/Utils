package com.fycx.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 操控系统相机和相册的工具类
 */
public class CameraUtils {

    private CameraUtils(){
        throw new AssertionError();
    }

    /**
     * 打开系统录像机
     * @param context
     * @param requestCode
     */
    public static void openSystemVideoCapture(Activity context,int requestCode) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统相册
     * @param context
     * @param requestCode
     */
    public static void openSystemPhotoAlbum(Activity context,int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 处理系统相册选择结果，并返回图片路径
     * @param context
     * @param data
     * @return
     */
    public static String handleSystemPhotoAlbumResult(Context context, Intent data){
        if(Build.VERSION.SDK_INT >= 19){
            return handleImageOnKitKatPath(context,data);
        }
        else {
            return handleImageBeforeKitKatPath(context,data);
        }
    }

    /**
     * 打开系统相机拍照
     * @param context
     * @param temp 拍照后，照片保存的文件
     * @param requestCode
     * 需要权限：android.permission.CAMERA，android.permission.WRITE_EXTERNAL_STORAGE
     */
    public static void openSystemCamera(Activity context, File temp, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            doTakePhotoIn7(context,temp.getAbsolutePath(), requestCode);
        } else {
            takePhoto(context,requestCode,Uri.fromFile(temp));
        }
    }

    /**
     * 打开相机拍照，该方法针对7.0以上系统
     * @param context
     * @param path 拍照后的照片保存文件的路径
     * @param requestCode startActivityForResult的请求码
     */
    private static void doTakePhotoIn7(Activity context, String path, int requestCode) {
        Uri mCameraTempUri;
        try {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            values.put(MediaStore.Images.Media.DATA, path);
            mCameraTempUri = context.getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePhoto(context,requestCode, mCameraTempUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 打开相机拍照，7.0以下系统可以直接使用该方法
     * @param context
     * @param requestCode startActivityForResult的请求码
     * @param uri 拍照后要保存的文件路径封装的Uri对象
     */
    private static void takePhoto(Activity context, int requestCode, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 4.4以下系统使用这个方法处理图片
     * @param context
     * @param data
     * @return
     */
    private static Bitmap handleImageBeforeKitKat(Context context, Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(context, uri, null);
        return getImage(imagePath);
    }

    /**
     * 4.4以下系统使用这个方法处理图片
     * @param context
     * @param data
     * @return
     */
    private static String handleImageBeforeKitKatPath(Context context, Intent data) {
        Uri uri = data.getData();
        return getImagePath(context, uri, null);
    }


    /**
     * 4.4及以上系统使用这个方法处理图片
     * @param context
     * @param data
     * @return
     */
    @TargetApi(19)
    private static String handleImageOnKitKatPath(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果不是document类型的Uri,则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        }
        return imagePath;
    }

    @TargetApi(19)
    private static Bitmap handleImageOnKitKat(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果不是document类型的Uri,则使用普通方式处理
            imagePath = getImagePath(context, uri, null);
        }
        return getImage(imagePath);
    }


    /**
     * 通过条件赛选出图片的路径
     * @param context
     * @param uri 图片路径的Uri值
     * @param selection 赛选条件
     * @return
     */
    public static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 传入图片路径，返回压缩后的bitmap
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        if (TextUtils.isEmpty(srcPath))  //如果图片路径为空 直接返回
            return null;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 对bitmap进行质量压缩
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

}
