package com.example.ngothi.checksheet.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import com.example.ngothi.checksheet.ui.activity.view.ImageDrawing;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class CanvasUtils {

    public static String createImage(String inputFile, String outputFile, List<Path> paths,
            Paint paint, ImageDrawing.Size viewSize) {

        Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        Bitmap bitmap = BitmapFactory.decodeFile(inputFile);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);

        for (Path path : paths) {
            Path mPath = new Path();
            mPath.addPath(path, matrix);
            canvas.drawBitmap(mutableBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, paint);
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(Context context, int resouce, String outputFile,
            List<Path> paths, Paint paint, ImageDrawing.Size viewSize) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resouce);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);

        for (Path path : paths) {
            Path mPath = new Path();
            mPath.addPath(path, matrix);
            canvas.drawBitmap(mutableBitmap, 0, 0, null);
            canvas.drawPath(mPath, paint);
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(String inputFile, List<Path> paths, Paint paint,
            ImageDrawing.Size viewSize) {
        String outputFile = inputFile;
       /* outputFile = outputFile.replace(".jpg", "_edited.jpg");
        outputFile = outputFile.replace(".JPG", "_edited.jpg");
        outputFile = outputFile.replace(".png", "_edited.png");*/
        return createImage(inputFile, outputFile, paths, paint, viewSize);
    }
}
