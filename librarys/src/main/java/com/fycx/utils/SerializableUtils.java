package com.fycx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具类
 */
public class SerializableUtils {

    private SerializableUtils(){
        throw new AssertionError();
    }

    /**
     * 序列化对象到磁盘
     * @param obj
     * @param serializableFilePath 序列化生成的文件路径
     * @return
     */
    public static boolean serializableObjectToDisk(Object obj,String serializableFilePath){

        ObjectOutputStream os = null;
        try {
            //如果文件不存在就创建文件
            File file = new File(serializableFilePath);
            File parentFile = file.getParentFile();
            if(parentFile.isDirectory() && !parentFile.exists()){
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            //获取输出流
            //这里如果文件不存在会创建文件，这是写文件和读文件不同的地方
            os = new ObjectOutputStream(new FileOutputStream(file));

            //要使用writeObject
            os.writeObject(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (os!=null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 反序列化文件中的对象
     * @param serializableFilePath
     * @return
     */
    public static Object deserializationObjectFromDisk(String serializableFilePath){
        ObjectInputStream is = null;
        try {
            File file = new File(serializableFilePath);
            if(!file.exists())return null;
            is = new ObjectInputStream(new FileInputStream(file));
            Object obj = is.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (is!=null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
