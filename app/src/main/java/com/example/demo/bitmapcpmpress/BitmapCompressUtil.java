package com.example.demo.bitmapcpmpress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by guozhk on 16-4-28.
 */
public class BitmapCompressUtil {
    public static int calculatelnSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSize=1;
        if(height>reqHeight||width>reqWidth){
            final int heightRatio = Math.round((float)reqHeight/(float)height);
            final int weidthRatio = Math.round((float)reqWidth/(float)width);
            inSize = Math.min(heightRatio,weidthRatio);
        }
        return inSize;
    }

    public static Bitmap getBitmap(String filePath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds= true;
        options.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/
        BitmapFactory.decodeFile(filePath,options);
        options.inSampleSize = calculatelnSize(options,480,800);
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeFile(filePath,options);
    }


public static String bitmapToString(String filePath){
    Bitmap bitmap = getBitmap(filePath);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);
    byte[] b= baos.toByteArray();
    return Base64.encodeToString(b, Base64.DEFAULT);

}


    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
