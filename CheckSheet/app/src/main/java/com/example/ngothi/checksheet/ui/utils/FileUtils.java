package com.example.ngothi.checksheet.ui.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eo_cuong on 3/13/17.
 */

public class FileUtils {
    public static String getDirectoryImageCapturePath() {
        String directoryName = "toyato-capture";

        File file = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        File noMediaFile = new File(file.getAbsolutePath(), ".nomedia");
        if (!noMediaFile.exists()) {
            try {
                noMediaFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    public static String getCaptureImageName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return dateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
    }

    public static boolean saveBimapToSdCard(Bitmap bitmap, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            bitmap.recycle();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String saveBimapToSdCard(Bitmap bitmap) {
        try {
            File file = new File(getDirectoryImageCapturePath(), getCaptureImageName());
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createBitmapFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        return saveBimapToSdCard(bitmap);
    }
}
