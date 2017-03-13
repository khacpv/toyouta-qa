package com.example.ngothi.checksheet.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceCamera;
    String filePath;
    boolean back = false;

    SurfaceHolder surfaceHolder;
    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;
    private CameraOrientationListener mOrientationListener;

    private int mCameraID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        surfaceCamera = (SurfaceView) findViewById(R.id.surfaceCamera);
        surfaceHolder = surfaceCamera.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        jpegCallback = new PictureCallback() {// code lưu file ?nh

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                Date thoiGian = new Date();

                int rotation = getPhotoRotation();

                Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
                float ratio = 3 / 4;
                int width = image.getWidth() / 4;
                int height = image.getHeight() / 4;

                image = Bitmap.createScaledBitmap(image, width, height, false);

                Log.e("TAG", "w:" + width + " h:" + height);

                Bitmap oldBitmap = image;
                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);

                image = Bitmap.createBitmap(oldBitmap, 0, 0, (int) width, (int) height, matrix,
                        false);

                oldBitmap.recycle();

                filePath = FileUtils.saveBimapToSdCard(image);

                try {

                    ExifInterface newExif = new ExifInterface(filePath);
                    switch (rotation) {
                        case Surface.ROTATION_0:
                            newExif.setAttribute(ExifInterface.TAG_ORIENTATION,
                                    String.valueOf(ExifInterface.ORIENTATION_NORMAL));
                            break;
                        case Surface.ROTATION_90:
                            newExif.setAttribute(ExifInterface.TAG_ORIENTATION,
                                    String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
                            break;
                        case Surface.ROTATION_180:
                            newExif.setAttribute(ExifInterface.TAG_ORIENTATION,
                                    String.valueOf(ExifInterface.ORIENTATION_ROTATE_180));
                            break;
                        case Surface.ROTATION_270:
                            newExif.setAttribute(ExifInterface.TAG_ORIENTATION,
                                    String.valueOf(ExifInterface.ORIENTATION_ROTATE_270));
                            break;
                    }
                    newExif.saveAttributes();
                } catch (Exception e) {

                }

                Intent data1 = new Intent();
                Bundle ten_image = new Bundle();
                ten_image.putString("tenfile", filePath);
                data1.putExtra("GoiTen", ten_image);
                setResult(SheetActivity.RESULT_OK, data1);
                finish();

                ;
            }
        };
    }

    public void back(View v) {
        back = true;
        finish();
    }

    public void captureClick(View v) throws IOException {
        mOrientationListener.rememberOrientation();
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            Camera.Parameters parameters = camera.getParameters();
            int w = 0, h = 0;
            for (Camera.Size s : parameters.getSupportedPictureSizes()) {
                //if s.width meets whatever criteria you want set it to your w
                //and s.height meets whatever criteria you want for your h
                w = s.width;
                h = s.height;
                break;
            }

            parameters.setPictureSize(w, h);

            camera.setParameters(parameters);
        } catch (Exception e) {
        }
    }

    public void changeOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(0);
        } else {
            camera.setDisplayOrientation(90);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {

            camera = Camera.open();
            changeOrientation();
        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        // param.setPreviewSize(352, 288);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mOrientationListener.disable();
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private int getPhotoRotation() {
        int rotation;
        int orientation = mOrientationListener.getRememberedNormalOrientation();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, info);

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {
            rotation = (info.orientation + orientation) % 360;
        }
        return rotation;
    }

    private static class CameraOrientationListener extends OrientationEventListener {

        private int mCurrentNormalizedOrientation;
        private int mRememberedNormalOrientation;

        public CameraOrientationListener(Context context) {
            super(context, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation != ORIENTATION_UNKNOWN) {
                mCurrentNormalizedOrientation = normalize(orientation);
            }
        }

        /**
         * @param degrees Amount of clockwise rotation from the device's natural position
         * @return Normalized degrees to just 0, 90, 180, 270
         */
        private int normalize(int degrees) {
            if (degrees > 315 || degrees <= 45) {
                return 0;
            }

            if (degrees > 45 && degrees <= 135) {
                return 90;
            }

            if (degrees > 135 && degrees <= 225) {
                return 180;
            }

            return 270;
        }

        public void rememberOrientation() {
            mRememberedNormalOrientation = mCurrentNormalizedOrientation;
        }

        public int getRememberedNormalOrientation() {
            rememberOrientation();
            return mRememberedNormalOrientation;
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mOrientationListener = new CameraOrientationListener(getApplicationContext());
        mOrientationListener.enable();
    }
}
