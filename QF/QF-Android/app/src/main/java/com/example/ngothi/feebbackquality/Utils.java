package com.example.ngothi.feebbackquality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kienht on 3/28/17.
 */

public class Utils {
    public static Uri getPhotoFileUri(Context context, String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CAMERA");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d("CAMERA", "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;

    }

    private static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    @SuppressLint("SimpleDateFormat")
    public static String nameFileFromCurrentTime() {
        return new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date().getTime());
    }

    public static Uri getImageUri(String fileName) {
        // Store image in dcim
        // Here you can change yourinternal storage path to store those images..
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera", fileName);

        return Uri.fromFile(file);
    }
}
