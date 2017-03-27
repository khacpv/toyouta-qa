package com.example.ngothi.feebbackquality;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class edit_vaythoi extends Activity {
    Bitmap originalBitmap,image;
//    ImageView iv_ttx;
    Button back1;
    Button btn_save_img;
    String tenfile,selectedImagePath;
    Paint paint;
    boolean back= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vaythoi);
//image view
//        iv_ttx = (ImageView) findViewById(R.id.iv_ttx);
//to get screen width and hight

//dimentions x,y of device to create a scaled bitmap having similar dimentions to screen size

//paint object to define paint properties
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);
//loading bitmap from drawable
        Intent Myintent = this.getIntent();
        Bundle packageFromCaller= Myintent.getBundleExtra("GoiTen1");
        tenfile= packageFromCaller.getString("tenfile1");

        //originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_wall);
        originalBitmap = BitmapFactory.decodeFile(tenfile);
        File file = new File(tenfile);
        if(file.delete()){};
        Display display = ((WindowManager) edit_vaythoi.this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        float width = originalBitmap.getWidth()/4;
        float height = originalBitmap.getHeight()/4;
        Bitmap resized = Bitmap.createScaledBitmap(originalBitmap, (int)width, (int)height, true);
        Matrix matrix = new Matrix();
      //  matrix.postRotate(90);
        originalBitmap = Bitmap.createBitmap(resized , 0, 0, resized.getWidth(), resized.getHeight(), matrix, true);

        image = originalBitmap.copy(Bitmap.Config.RGB_565, true);

         btn_save_img = (Button) findViewById(R.id.btn_save_image);
       // back1= (Button) findViewById(R.id.et_txt) ;
        image = originalBitmap.copy(Bitmap.Config.RGB_565, true);
//        iv_ttx.setImageBitmap(image);

        btn_save_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
//funtion save image is called with bitmap image as parameter
                saveImage(((MyView)findViewById(R.id.my_view)).saveBitmap());
                Intent data = new Intent();
                Bundle ten_image = new Bundle();
                ten_image.putString("tenfile", selectedImagePath);
                data.putExtra("GoiTen", ten_image);
                setResult(MainActivity.RESULT_OK, data);
                finish();
            }
        });

        ((MyView)findViewById(R.id.my_view)).setBitmap(originalBitmap);

//        findViewById(R.id.container).setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                // TODO Auto-generated method stub
//
//                float scr_x=arg1.getX();
//                float scr_y=arg1.getY();
//
//                //    Toast.makeText(edit_vaythoi.this,scr_x+"  "+scr_y,Toast.LENGTH_LONG).show();
//                xoa();
//                createImage(scr_x,scr_y);
//                return true;
//            }
//        });
    }


    void saveImage(Bitmap img) {
        Date thoiGian = new Date();

        //Khai bao dinh dang ngay thang
        SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("yyyyMMdd_hhmmss");

        //parse ngay thang sang dinh dang va chuyen thanh string.
        selectedImagePath="/storage/emulated/0/DCIM/Camera/"+dinhDangThoiGian.format(thoiGian.getTime())+".jpg";

        //String fname = "Image-"+ n +".jpg";
        File file = new File ( selectedImagePath);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);

            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Toast.makeText(edit_vaythoi.this, "ảnh đã lưu ",Toast.LENGTH_LONG).show();
    }

       /* @Override
    public void finish()
    {
        if(back== false) {
            Intent data = new Intent();
            Bundle ten_image = new Bundle();
            ten_image.putString("tenfile", selectedImagePath);
            data.putExtra("GoiTen", ten_image);
            this.setResult(MainActivity.RESULT_OK, data);
        }
        super.finish();
    }*/
}