package vn.com.toyota.checkdetail.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import vn.com.toyota.checkdetail.Common;
import vn.com.toyota.checkdetail.model.ImageCapture;
import vn.com.toyota.checkdetail.utils.CanvasUtils;
import vn.com.toyota.checkdetail.utils.GsonUtils;

/**
 * Created by eo_cuong on 3/29/17.
 */

public class SaveImageService extends IntentService {

    private Queue<ImageCapture> mImageCaptureQueue;

    private boolean isExecute;

    private Paint mPaint;

    public SaveImageService() {
        super("SaveImageService");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        String list = bundle.getString(Common.BundleConstant.LIST_IMAGE_CAPTURE);
        List<ImageCapture> mImageCaptures = GsonUtils.String2ListObject(list, ImageCapture[].class);
        if (mImageCaptures == null) {
            return;
        }
        addToQueue(mImageCaptures);
        if (!isExecute) {
            executeSaveImage();
        }
    }

    private void addToQueue(List<ImageCapture> imageCaptures) {

        if (mImageCaptureQueue == null) {
            mImageCaptureQueue = new LinkedList<>();
        }

        mImageCaptureQueue.addAll(imageCaptures);
    }

    private void executeSaveImage() {
        ImageCapture imageCapture = mImageCaptureQueue.poll();
        if (imageCapture == null) {
            stopSelf();
            return;
        }
        new SaveImageTask().execute(imageCapture);
    }

    class SaveImageTask extends AsyncTask<ImageCapture, Void, String> {

        ImageCapture myImage;

        @Override
        protected String doInBackground(ImageCapture... imageCaptures) {
            ImageCapture imageCapture = imageCaptures[0];
            myImage = imageCapture;
            if (imageCapture.isFromFile()) {
                return CanvasUtils.createImage(imageCapture.getFilepath(),
                        imageCapture.getDrawEntityPaths(), mPaint, imageCapture.getViewSize(), 100);
            } else {
                if (imageCapture.isEditted()) {
                    return CanvasUtils.createImage(getApplicationContext(),
                            imageCapture.getResourceId(), imageCapture.getFilepath(),
                            imageCapture.getDrawEntityPaths(), mPaint, imageCapture.getViewSize(),
                            100);
                } else {
                    return "";
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                mImageCaptureQueue.add(myImage);
            }
            Toast.makeText(getApplicationContext(),
                    s == null ? "save fail" : "save successfully : " + s, Toast.LENGTH_SHORT)
                    .show();
            executeSaveImage();
        }
    }
}
