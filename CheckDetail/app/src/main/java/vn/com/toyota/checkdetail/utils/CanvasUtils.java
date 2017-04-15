package vn.com.toyota.checkdetail.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.DrawEntityPath;
import vn.com.toyota.checkdetail.model.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class CanvasUtils {

    public static String createImage(String inputFile, String outputFile, List<Path> paths,
            Paint paint, Size viewSize) {

        Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        Bitmap bitmap = BitmapFactory.decodeFile(inputFile);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);
        for (Path path : paths) {
            Path mPath = new Path();
            mPath.addPath(path, matrix);
            canvas.drawBitmap(mutableBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(Context context, int resouce, String outputFile,
            List<Path> paths, Paint paint, Size viewSize) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resouce);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);
        for (Path path : paths) {
            Path mPath = new Path();
            mPath.addPath(path, matrix);
            canvas.drawBitmap(mutableBitmap, 0, 0, null);
            canvas.drawPath(mPath, mPaint);
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(String inputFile, List<Path> paths, Paint paint,
            Size viewSize) {
        String outputFile = inputFile;
       /* outputFile = outputFile.replace(".jpg", "_edited.jpg");
        outputFile = outputFile.replace(".JPG", "_edited.jpg");
        outputFile = outputFile.replace(".png", "_edited.png");*/
        return createImage(inputFile, outputFile, paths, paint, viewSize);
    }

    public static String createImage(String inputFile, String outputFile,
            List<DrawEntityPath> mDrawEntityPaths, Paint paint, Size viewSize, int quanlity) {
        Bitmap bitmap = BitmapFactory.decodeFile(inputFile);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();

        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);

        Path myPath = new Path();
        for (DrawEntityPath entityPath : mDrawEntityPaths) {
            DrawEntityPath drawEntityPath = new DrawEntityPath.Builder().action(entityPath.action)
                    .data(entityPath.data)
                    .build();
            drawEntityPath.setScale(ratio);
            switch (drawEntityPath.action) {
                case ACTION_MOVE_TO:
                    String[] dataMove = drawEntityPath.data.split(",");
                    myPath.moveTo(Float.parseFloat(dataMove[0]), Float.parseFloat(dataMove[1]));
                    break;

                case ACTION_LINE_TO:
                    String[] dataLine = drawEntityPath.data.split(",");
                    myPath.lineTo(Float.parseFloat(dataLine[0]), Float.parseFloat(dataLine[1]));
                    break;
                case ACTION_QUAD_TO:
                    String[] dataQuad = drawEntityPath.data.split(",");
                    myPath.quadTo(Float.parseFloat(dataQuad[0]), Float.parseFloat(dataQuad[1]),
                            Float.parseFloat(dataQuad[2]), Float.parseFloat(dataQuad[3]));
                    break;
                case ACTION_RESET:
                    myPath.reset();
                    break;
                case ACTION_DRAW:
                    canvas.drawPath(myPath, mPaint);
                    break;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quanlity, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(Context context, int resouceId, String outputFile,
            List<DrawEntityPath> mDrawEntityPaths, Paint paint, Size viewSize, int quanlity) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resouceId);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        float ratio = (float) bitmap.getWidth() / viewSize.getWidth();

        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);

        Path myPath = new Path();
        for (DrawEntityPath entityPath : mDrawEntityPaths) {
            DrawEntityPath drawEntityPath = new DrawEntityPath.Builder().action(entityPath.action)
                    .data(entityPath.data)
                    .build();
            drawEntityPath.setScale(ratio);
            switch (drawEntityPath.action) {
                case ACTION_MOVE_TO:
                    String[] dataMove = drawEntityPath.data.split(",");
                    myPath.moveTo(Float.parseFloat(dataMove[0]), Float.parseFloat(dataMove[1]));
                    break;

                case ACTION_LINE_TO:
                    String[] dataLine = drawEntityPath.data.split(",");
                    myPath.lineTo(Float.parseFloat(dataLine[0]), Float.parseFloat(dataLine[1]));
                    break;
                case ACTION_QUAD_TO:
                    String[] dataQuad = drawEntityPath.data.split(",");
                    myPath.quadTo(Float.parseFloat(dataQuad[0]), Float.parseFloat(dataQuad[1]),
                            Float.parseFloat(dataQuad[2]), Float.parseFloat(dataQuad[3]));
                    break;
                case ACTION_RESET:
                    myPath.reset();
                    break;
                case ACTION_DRAW:
                    canvas.drawPath(myPath, mPaint);
                    break;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, quanlity, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImageSmall(String inputFile, String outputFile,
            List<DrawEntityPath> mDrawEntityPaths, Paint paint, Size viewSize) {

        Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeFile(inputFile);

        float ratioImage = (float) viewSize.getWidth() / viewSize.getHeight();
        int maxWidth = 300;
        int maxHeight = (int) (maxWidth / ratioImage);

        //Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap mutableBitmap = Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, false);
        float ratio = (float) mutableBitmap.getWidth() / viewSize.getWidth();
        Canvas canvas = new Canvas(mutableBitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);

        Path myPath = new Path();
        for (DrawEntityPath entityPath : mDrawEntityPaths) {
            DrawEntityPath drawEntityPath = new DrawEntityPath.Builder().action(entityPath.action)
                    .data(entityPath.data)
                    .build();
            drawEntityPath.setScale(ratio);
            switch (drawEntityPath.action) {
                case ACTION_MOVE_TO:
                    String[] dataMove = drawEntityPath.data.split(",");
                    myPath.moveTo(Float.parseFloat(dataMove[0]), Float.parseFloat(dataMove[1]));
                    break;

                case ACTION_LINE_TO:
                    String[] dataLine = drawEntityPath.data.split(",");
                    myPath.lineTo(Float.parseFloat(dataLine[0]), Float.parseFloat(dataLine[1]));
                    break;
                case ACTION_QUAD_TO:
                    String[] dataQuad = drawEntityPath.data.split(",");
                    myPath.quadTo(Float.parseFloat(dataQuad[0]), Float.parseFloat(dataQuad[1]),
                            Float.parseFloat(dataQuad[2]), Float.parseFloat(dataQuad[3]));
                    break;
                case ACTION_RESET:
                    myPath.reset();
                    break;
                case ACTION_DRAW:
                    canvas.drawPath(myPath, mPaint);
                    break;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImageSmall(Context context, int resouce, String outputFile,
            List<DrawEntityPath> mDrawEntityPaths, Paint paint, Size viewSize) {

        Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resouce);

        float ratioImage = (float) viewSize.getWidth() / viewSize.getHeight();
        int maxWidth = 300;
        int maxHeight = (int) (maxWidth / ratioImage);

        //Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap mutableBitmap = Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, false);
        float ratio = (float) mutableBitmap.getWidth() / viewSize.getWidth();
        Canvas canvas = new Canvas(mutableBitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(ratio, ratio);
        Paint mPaint = new Paint(paint);
        mPaint.setStrokeWidth(paint.getStrokeWidth() * ratio);

        Path myPath = new Path();
        for (DrawEntityPath entityPath : mDrawEntityPaths) {
            DrawEntityPath drawEntityPath = new DrawEntityPath.Builder().action(entityPath.action)
                    .data(entityPath.data)
                    .build();
            drawEntityPath.setScale(ratio);
            switch (drawEntityPath.action) {
                case ACTION_MOVE_TO:
                    String[] dataMove = drawEntityPath.data.split(",");
                    myPath.moveTo(Float.parseFloat(dataMove[0]), Float.parseFloat(dataMove[1]));
                    break;

                case ACTION_LINE_TO:
                    String[] dataLine = drawEntityPath.data.split(",");
                    myPath.lineTo(Float.parseFloat(dataLine[0]), Float.parseFloat(dataLine[1]));
                    break;
                case ACTION_QUAD_TO:
                    String[] dataQuad = drawEntityPath.data.split(",");
                    myPath.quadTo(Float.parseFloat(dataQuad[0]), Float.parseFloat(dataQuad[1]),
                            Float.parseFloat(dataQuad[2]), Float.parseFloat(dataQuad[3]));
                    break;
                case ACTION_RESET:
                    myPath.reset();
                    break;
                case ACTION_DRAW:
                    canvas.drawPath(myPath, mPaint);
                    break;
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            mutableBitmap.recycle();
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createImage(String inputFile, List<DrawEntityPath> paths, Paint paint,
            Size viewSize, int quantity) {
        String outputFile = inputFile;
        outputFile = FileUtils.getFileNameWithExtAdded(outputFile, AppConfig.SAVED_IMAGE_EXT);
        return createImage(inputFile, outputFile, paths, paint, viewSize, quantity);
    }



    public static String createImageThumb(String inputFile, List<DrawEntityPath> paths, Paint paint,
            Size viewSize) {
        String outputFile = inputFile;
        outputFile = FileUtils.getFileNameWithExtAdded(outputFile, AppConfig.THUMB_IMAGE_EXT);
        return createImageSmall(inputFile, outputFile, paths, paint, viewSize);
    }

    public static String createImageThumb(Context context, int resourceId, String outputFile,
            List<DrawEntityPath> paths, Paint paint, Size viewSize) throws IOException {

        if (outputFile == null) {
            outputFile = new File(FileUtils.getDirectory(),
                    FileUtils.getCaptureImageName()).getAbsolutePath();
            outputFile = outputFile.replace(".jpg", "_thumb.jpg");
            outputFile = outputFile.replace(".JPG", "_thumb.jpg");
            outputFile = outputFile.replace(".png", "_thumb.png");
        }

        return createImageSmall(context, resourceId, outputFile, paths, paint, viewSize);
    }
}
