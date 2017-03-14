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
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import java.io.IOException;

import static android.R.attr.data;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceCamera;
    String filePath;
    boolean back = false;
    SurfaceHolder surfaceHolder;
    PictureCallback jpegCallback;
    private CameraOrientationListener mOrientationListener;
    private int mCameraID = 0;
    KProgressHUD dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        dialog = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.saving_image))
                .setWindowColor(getResources().getColor(R.color.colorPrimary))
                .setAnimationSpeed(2);

        surfaceCamera = (SurfaceView) findViewById(R.id.surfaceCamera);
        surfaceHolder = surfaceCamera.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        jpegCallback = new PictureCallback() {// code lưu file ?nh

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                new SaveImageTask().execute(data);
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
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.autoFocus(null);
            int w = 0, h = 0;
            for (Camera.Size s : parameters.getSupportedPictureSizes()) {
                //if s.width meets whatever criteria you want set it to your w
                //and s.height meets whatever criteria you want for your h
                w = s.width;
                h = s.height;
                parameters.setPictureSize(w, h);
                camera.setParameters(parameters);

                Display display = getWindowManager().getDefaultDisplay();
                int screenwidth = display.getWidth();
                int screenHeight = (int) (screenwidth / ((float) h / w));

                surfaceCamera.getLayoutParams().width = screenwidth;
                surfaceCamera.getLayoutParams().height = screenHeight;
                surfaceCamera.invalidate();

                break;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("down", "focusing now");

            camera.autoFocus(null);
        }

        return true;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getPhotoRotation() {
        int rotation;
        int orientation = mOrientationListener.getRememberedNormalOrientation();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraID, info);
        if (info.canDisableShutterSound) {
            camera.enableShutterSound(false);
        }

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

    class SaveImageTask extends AsyncTask<byte[], Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected String doInBackground(byte[]... bytes) {
            try {
                byte[] data = bytes[0];
                int rotation = getPhotoRotation();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPurgeable = true;
                options.inSampleSize = 1;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                int maxWidth = image.getWidth();//8mp
                float ratio = (float) image.getWidth() / maxWidth;
                int width = (int) (image.getWidth() / ratio);
                int height = (int) (image.getHeight() / ratio);
                image = Bitmap.createScaledBitmap(image, width, height, false);
                Log.e("TAG", "w:" + width + " h:" + height);

                Bitmap oldBitmap = image;
                Matrix matrix = new Matrix();
                matrix.postRotate(rotation);
                image = Bitmap.createBitmap(oldBitmap, 0, 0, (int) width, (int) height, matrix,
                        false);
                filePath = FileUtils.saveBimapToSdCard(image);
                oldBitmap.recycle();
                image.recycle();
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

                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s == null) {
                Toast.makeText(CameraActivity.this,
                        getResources().getString(R.string.capture_error), Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("data", s);
            setResult(SheetActivity.RESULT_OK, intent);
            finish();
        }
    }
}
