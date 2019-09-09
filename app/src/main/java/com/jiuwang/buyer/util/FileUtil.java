package com.jiuwang.buyer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/*
*
* 作者：Yokey软件工作室
*
* 企鹅：1002285057
*
* 网址：www.yokey.top
*
* 作用：文件工具类
*
* 更新：2016-04-02
*
*/

public class FileUtil {

    public static String downPathString = "Android/Yokey/ShopNc/Down/";
    public static String cachePathString = "Android/Yokey/ShopNc/Cache/";
    public static String imagePathString = "Android/Yokey/ShopNc/Image/";
    public static File updateDir = null;
    public static File updateFile = null;
    /***********
     * 保存升级APK的目录
     ***********/
    public static final String KonkaApplication = "konkaUpdateApplication";
    public static boolean isCreateFileSucess;
    /*
    * 作用：获取根目录路径
    * 更新：2016-04-02
    */
    public static String getRootPath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/";
        }
    }

    /*
    * 作用：获取程序缓存路径
    * 更新：2016-04-02
    */
    public static String getCachePath() {
        if (hasSDCard()) {
            return getRootPath() + cachePathString;
        } else {
            return getRootPath() + cachePathString;
        }
    }

    /*
    * 作用：获取程序图片存放路径
    * 更新：2016-04-02
    */
    public static String getImagePath() {
        if (hasSDCard()) {
            return getRootPath() + imagePathString;
        } else {
            return getRootPath() + imagePathString;
        }
    }

    /*
    * 作用：检查下载文件夹路径
    * 更新：2016-04-02
    */
    public static String getDownPath() {
        if (hasSDCard()) {
            return getRootPath() + downPathString;
        } else {
            return getRootPath() + downPathString;
        }
    }

    /*
    * 作用：判断有没有内存卡
    * 更新：2016-04-02
    */
    public static boolean hasSDCard() {
        String str = Environment.getExternalStorageState();
        return str.equals(Environment.MEDIA_MOUNTED);
    }

    /*
    * 作用：创建图片文件夹
    * 更新：2016-04-02
    */
    public static void createImagePath() {
        File file = new File(getImagePath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建缓存文件夹
    * 更新：2016-04-02
    */
    public static void createCachePath() {
        File file = new File(getCachePath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建下载文件夹
    * 更新：2016-04-02
    */
    public static void createDownPath() {
        File file = new File(getDownPath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    * 作用：创建一个 JPG 文件
    * 更新：2016-04-02
    */
    public static String createJpgByBitmap(String name, Bitmap bitmap) {

        String path = FileUtil.getImagePath() + name + ".jpg";

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream;
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;

    }
    /**
     * 方法描述：createFile方法
     */
    public static void createFile(String app_name) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            isCreateFileSucess = true;
            updateDir = new File(Environment.getExternalStorageDirectory() + "/" + KonkaApplication + "/");
            updateFile = new File(updateDir + "/" + app_name + ".apk");
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    isCreateFileSucess = false;
                    e.printStackTrace();
                }
            }
        } else {
            isCreateFileSucess = false;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private Context mContext;
    /**
     * 手机存储路径
     */
    private String SDPath;

    public String getSDPath() {
        return SDPath;
    }

    public FileUtil(Context context) {
        mContext = context;
        //获得手机外部存储路径
        SDPath = mContext.getFilesDir().getAbsolutePath() + "/";
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(mContext, null);
        if (externalFilesDirs.length > 0) {
            SDPath = externalFilesDirs[0].getAbsolutePath() + "/";
        }
    }

    /**
     * 在SD卡上新建文件
     *
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPath + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName 目录名
     * @return
     */
    public File createSDDir(String dirName) {
        File dir = new File(SDPath + dirName);
        dir.mkdirs();
        System.out.println("---创建目录=》" + dir);
        return dir;
    }

    /**
     * 判断文件在SD卡上是否存在
     *
     * @param fileName 文件名
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPath + fileName);
        return file.exists();
    }

    /**
     * 将input流里面的数据写到指定位置
     *
     * @param path     目标位置
     * @param fileName 文件名
     * @param input    输入流
     * @return
     */
    public File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream out = null;
        System.out.println("-----SD目录=》" + SDPath);
        try {
            //创建目录
            createSDDir(path);
            file = createSDFile(path + fileName);
            out = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                out.write(buffer, 0, temp);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将byte[]的数据写到指定位置
     *
     * @param path
     * @param fileName
     * @param content
     * @return
     */
    public File write2SDFromByte(String path, String fileName, byte[] content) {
        File file = null;
        FileOutputStream fos = null;
        System.out.println("-----SD目录=》" + SDPath);
        try {
            //创建目录
            createSDDir(path);
            file = createSDFile(path + fileName);
            fos = new FileOutputStream(file);
            fos.write(content);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static String getFileName(String url) {
        String filename = "";
        if (!TextUtils.isEmpty(url)) {
            String[] strs = url.split("/");
            filename = strs[strs.length - 1];
        }
        return filename;
    }
    // 生成文件
    private static File makeFilePath(String fileName) {
        File file = null;
        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return file;
    }

    // 将字符串写入到文本文件中
    public static void writeLogToFile(String strcontent, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(fileName);

        String strFilePath = fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);


        }
    }

}
