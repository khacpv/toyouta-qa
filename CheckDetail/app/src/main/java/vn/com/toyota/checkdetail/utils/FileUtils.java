package vn.com.toyota.checkdetail.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eo_cuong on 3/13/17.
 */

public class FileUtils {

    public static String getDirectory() throws IOException {
        String directoryName = "toyota";
        File file = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        File noMediaFile = new File(file.getAbsolutePath(), ".nomedia");
        if (!noMediaFile.exists()) {

            noMediaFile.createNewFile();
        }
        return file.getAbsolutePath();
    }

    public static String getCaptureImageName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return dateFormat.format(new Date(System.currentTimeMillis())) + ".jpg";
    }

    public static boolean saveBimapToSdCard(Bitmap bitmap, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        bitmap.recycle();
        fos.close();
        return true;
    }

    public static String saveBimapToSdCard(Bitmap bitmap) throws IOException {
        File file = new File(getDirectory(), getCaptureImageName());
        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        return file.getAbsolutePath();
    }

    public static String createBitmapFromImageView(ImageView imageView) throws IOException {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        return saveBimapToSdCard(bitmap);
    }

    public static String getFileNameWithExtAdded(String fileName, String ext) {
        String result = fileName.replace(".jpg", ext + ".jpg");
        result = result.replace(".JPG", ext + ".jpg");
        result = result.replace(".png", ext + ".png");
        return result;
    }
}
