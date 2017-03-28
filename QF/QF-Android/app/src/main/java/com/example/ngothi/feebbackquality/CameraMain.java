package com.example.ngothi.feebbackquality;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.Camera;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class CameraMain extends Activity  implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceCamera;
    Button Quay, Luu, Chup, Xoa;

    String selectedImagePath, PathFile;

    boolean back=false;

    SurfaceHolder surfaceHolder;
    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        Chup = (Button) findViewById(R.id.btnCapture1);
        //Quay = (Button) findViewById(R.id.btnBack1);

        surfaceCamera = (SurfaceView) findViewById(R.id.surfaceCamera);
        surfaceHolder = surfaceCamera.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        jpegCallback = new Camera.PictureCallback() {// code lưu file ?nh

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                Date thoiGian = new Date();

                //Khai bao dinh dang ngay thang
                SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyyMMdd_hhmmss");

                //parse ngay thang sang dinh dang va chuyen thanh string.
                selectedImagePath = dinhDangThoiGian.format(thoiGian.getTime()) + ".jpg";
                PathFile = "/storage/emulated/0/DCIM/Camera/" + selectedImagePath;//duong d?n file anh v?a chup
                try {
                    outStream = new FileOutputStream(String.format(PathFile));
                    outStream.write(data);
                    outStream.close();
                    //   Toast.makeText(getApplicationContext(), "?nh đ? lưu", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }

                //  Toast.makeText(getApplicationContext(), "Ảnh đã lưu", Toast.LENGTH_LONG).show();
                Intent data1 = new Intent();
                Bundle ten_image = new Bundle();
                ten_image.putString("photoFileName", PathFile);
                data1.putExtra("GoiTen", ten_image);
                setResult(MainActivity.RESULT_OK, data1);
                finish();
                   /*
                Intent Myintent = new Intent(CameraMain.this,edit_vaythoi.class);
                Bundle ten_image  = new Bundle();
                ten_image.putString("photoFileName",PathFile);
                Myintent.putExtra("GoiTen",ten_image);
                startActivityForResult(Myintent,IMAGE_EDIT);
                dialogCamera.dismiss();// thoat dialogCamera
                */
            }
        };
    }// ket thuc onCreate

   /* public void QuayLai(View v)
    {
        back=true;
        finish();
    }*/
    public void captureImage1(View v) throws IOException {
        camera.takePicture(null, null, jpegCallback);

    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        }

        catch (Exception e) {
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }
        catch (Exception e) {
        }
    }
    public void changeOrientation()
    {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            camera.setDisplayOrientation(0);
        else
            camera.setDisplayOrientation(90);
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
        }

        catch (RuntimeException e) {
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(352, 288);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }

        catch (Exception e) {
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
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}
