package com.example.ngothi.checksheet.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.activity.view.ImageDrawing;
import java.io.File;
import java.io.IOException;

public class SheetActivity extends AppCompatActivity {
    Dialog dialog;
    int number = 1;
    ImageView image;
    ImageDrawing image12;
    TextView textseq, index;
    String seq, c1, c2, c3;
    int REQUEST_ID_IMAGE_CAPTURE = 1000;
    RelativeLayout lyImage;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheetctivity);
        image = (ImageView) findViewById(R.id.icon_check);
        textseq = (TextView) findViewById(R.id.textSeq);
        index = (TextView) findViewById(R.id.index);
        image12 = (ImageDrawing) findViewById(R.id.image12);
        lyImage = (RelativeLayout) findViewById(R.id.lyImage);
        Intent Myintent = this.getIntent();
        Bundle packageFromCaller = Myintent.getBundleExtra("GoiTen1");
        seq = packageFromCaller.getString("Seq");
        textseq.setText(seq);
    }

    void number_check(int i, int c) {
        switch (i) {
            case 1:
                image.setImageResource(R.drawable.lopoto);
                if (c == 0) {
                    c1 = "OK";
                } else {
                    c1 = "NG";
                }
                number++;
                index.setText("2");
                break;
            case 2:
                image.setImageResource(R.drawable.denpha);
                if (c == 0) {
                    c2 = "OK";
                } else {
                    c2 = "NG";
                }
                number++;
                index.setText("3");
                break;
            case 3:

                if (c == 0) {
                    c3 = "OK";
                } else {
                    c3 = "NG";
                }
                Intent intent = new Intent(SheetActivity.this, paperSheetActivity.class);
                Bundle ten_image = new Bundle();
                ten_image.putString("Seq", seq);
                ten_image.putString("c1", c1);
                ten_image.putString("c2", c2);
                ten_image.putString("c3", c3);
                ten_image.putString("goi", "ghi");
                intent.putExtra("GoiTen2", ten_image);
                startActivity(intent);
                break;
        }
    }

    public void ok1(View v) {
        number_check(number, 0);
    }

    public void NG1(View v) {
        number_check(number, 1);
    }

    public void chup1(View v) {
        AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        Intent intent = new Intent(SheetActivity.this, CameraMain.class);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
    }

    int w;
    int h;


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle packageFromCaller = data.getBundleExtra("GoiTen");
                        String tenFile = packageFromCaller.getString("tenfile");
                        // Toast.makeText(MainActivity.this,tenFile,Toast.LENGTH_LONG).show();
                        File imgFile = new File(tenFile);
                        if (imgFile.exists()) {

                            Bitmap myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            ExifInterface ei = null;
                            try {
                                ei = new ExifInterface(imgFile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            switch(orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    myBitmap1=  rotateImage(myBitmap1, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    myBitmap1= rotateImage(myBitmap1, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    myBitmap1=rotateImage(myBitmap1, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:

                                default:
                                    break;
                            }

                            //image12.clearDraw();

                            w = myBitmap1.getWidth();
                            h = myBitmap1.getHeight();

                            Display display = ((WindowManager) getSystemService(
                                    Context.WINDOW_SERVICE)).getDefaultDisplay();
                            int screenWidth = display.getWidth();

                            final float ratio = (float) w / h;
                            if (w > h) {
                                w = screenWidth;
                                h = (int) (w / ratio);
                            } else {

                                lyImage.getViewTreeObserver()
                                        .addOnGlobalLayoutListener(
                                                new ViewTreeObserver.OnGlobalLayoutListener() {
                                                    @Override
                                                    public void onGlobalLayout() {
                                                        lyImage.getViewTreeObserver()
                                                                .removeOnGlobalLayoutListener(this);
                                                        h = lyImage.getHeight(); //height is ready
                                                        w = (int) (h * ratio);
                                                    }
                                                });
                            }



                            image12.setImageBitmap(myBitmap1);
                           // image12.setRotation(90f);
                            image12.getLayoutParams().height = h;
                            image12.getLayoutParams().width = w;
                            image12.requestLayout();
                        }
                    }
                });
            }
        }
    }
}