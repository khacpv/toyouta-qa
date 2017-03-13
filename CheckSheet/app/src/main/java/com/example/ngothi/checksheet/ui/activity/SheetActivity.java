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
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class SheetActivity extends AppCompatActivity {
    Dialog dialog;
    int number = 1;
    ImageView image;
    ImageDrawing image12;
    String seq, c1, c2, c3;
    int REQUEST_ID_IMAGE_CAPTURE = 1000;
    RelativeLayout lyImage;
    int widthImageCapture;
    int heightImageCapture;

    int maxHeightImageCapture;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheetctivity);
        image12 = (ImageDrawing) findViewById(R.id.image12);
        lyImage = (RelativeLayout) findViewById(R.id.lyImage);
        Intent Myintent = this.getIntent();
        Bundle packageFromCaller = Myintent.getBundleExtra("GoiTen1");
        seq = packageFromCaller.getString("Seq");

        lyImage.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        lyImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        maxHeightImageCapture = lyImage.getHeight(); //height is ready
                    }
                });
    }

    public void okClick(View v) {
    }

    public void notGoodClick(View v) {
        FileUtils.createBitmapFromImageView(image12);
    }

    public void captureClick(View v) {
        AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        Intent intent = new Intent(SheetActivity.this, CameraActivity.class);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
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

                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    myBitmap1 = rotateImage(myBitmap1, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    myBitmap1 = rotateImage(myBitmap1, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    myBitmap1 = rotateImage(myBitmap1, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:

                                default:
                                    break;
                            }

                            image12.clearDraw();

                            widthImageCapture = myBitmap1.getWidth();
                            heightImageCapture = myBitmap1.getHeight();

                            Display display = ((WindowManager) getSystemService(
                                    Context.WINDOW_SERVICE)).getDefaultDisplay();
                            int screenWidth = display.getWidth();

                            final float ratio = (float) widthImageCapture / heightImageCapture;
                            if (widthImageCapture > heightImageCapture) {
                                widthImageCapture = screenWidth;
                                heightImageCapture = (int) (widthImageCapture / ratio);
                            } else {
                                heightImageCapture = maxHeightImageCapture;
                                widthImageCapture = (int) (heightImageCapture * ratio);

                            }
                            image12.getLayoutParams().height = heightImageCapture;
                            image12.getLayoutParams().width = widthImageCapture;
                            image12.requestLayout();
                            image12.setImageBitmap(myBitmap1);
                        }
                    }
                });
            }
        }
    }
}