package com.jiuwang.buyer.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    /*
    * 作用：获取图片缩略图
    * 更新：2016-04-16
    */
    public static Bitmap getSmall(String path) {

        //获取图片角度
        int degree = 0;

        ExifInterface exif = null;

        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }

        //生成BITMAP对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 800 || width > 480) {
            int heightRatio = Math.round((float) height / (float) 800);
            int widthRatio = Math.round((float) width / (float) 480);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        //根据角度旋转图片
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    /*
    * 作用：获取本机图片
    * 更新：2016-04-16
    */
    public static Bitmap getLocal(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * 作用：String 转成 Bitmap
    * 更新：2016-04-16
    */
    public static Bitmap toBitmap(String str) {
        Bitmap bitmap;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /*
    * 作用：图片转成 String
    * 更新：2016-04-16
    */
    public static String toString(String path) {

        try {
            Bitmap bitmap = getSmall(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] b = stream.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "null";
        }

    }

    public static void saveImageToGallery(Context context, Bitmap bmp,String imageName) {
        // 首先保存图片 创建文件夹
        // 获取内置SD卡路径
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片文件路径
        File file = new File(sdCardPath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            String name = file1.getName();
            if (name.endsWith(imageName)) {
                boolean flag = file1.delete();
                LogUtils.e("1111", "删除 + " + flag);
            }
        }

        //图片文件名称
        String fileName = sdCardPath + "/"+imageName;
        file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            LogUtils.e("111", e.getMessage());
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            LogUtils.e("333", e.getMessage());
            e.printStackTrace();
        }

        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        file.delete();
        MyToastView.showToast("保存成功", context);
    }




}
