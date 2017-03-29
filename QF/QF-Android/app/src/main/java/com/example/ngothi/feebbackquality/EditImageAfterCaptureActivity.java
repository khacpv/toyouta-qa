package com.example.ngothi.feebbackquality;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.io.File;
import java.io.FileOutputStream;

public class EditImageAfterCaptureActivity extends Activity {

    private static final String TAG = EditImageAfterCaptureActivity.class.getSimpleName();

    @BindView(R.id.my_view)
    MyView mMyViewImage;

    Bitmap originalBitmap;
    Bitmap image;
    String photoFileName;
    String selectedImagePath;
    Paint paint;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vaythoi);
        ButterKnife.bind(this);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);

        //        Intent Myintent = this.getIntent();
        //        Bundle packageFromCaller = Myintent.getBundleExtra("GoiTen1");
        //        photoFileName = packageFromCaller.getString("tenfile1");

        Intent Myintent = this.getIntent();
        Bundle packageFromCaller = Myintent.getBundleExtra(MainActivity.PHOTO_FILE_NAME);
        photoFileName = packageFromCaller.getString(MainActivity.PHOTO_FILE_NAME);

        Log.e(TAG, "onCreate: " + photoFileName);

        mMyViewImage.setBitmap(readBitmapAndScale(photoFileName));
    }

    public Bitmap readBitmapAndScale(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
        BitmapFactory.decodeFile(path, options); //Đọc thông tin ảnh
        options.inSampleSize = 4; //Scale bitmap xuống 4 lần
        options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
        return BitmapFactory.decodeFile(path, options);
    }

    @OnClick(R.id.btn_save_image)
    void saveImage() {

        long startTime = System.currentTimeMillis();
        try {
            saveImage(mMyViewImage.getBitmap(photoFileName));
            Log.e("SaveImage",
                "Successful: " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SaveImage", "Error: " + (System.currentTimeMillis() - startTime) + "ms");
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("photoFileName", selectedImagePath);
        intent.putExtra("GoiTen", bundle);
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap =
            Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
        //has background drawable, then draw it on the canvas
        {
            bgDrawable.draw(canvas);
        } else
        //does not have background drawable, then draw white background on the canvas
        {
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    void saveImage(Bitmap bitmap) {
        String nameFile = Utils.nameFileFromCurrentTime() + ".jpg";
        selectedImagePath = "/storage/emulated/0/DCIM/Camera/" + nameFile;
        Log.e(TAG, "saveImage: " + selectedImagePath);
        File file = new File(selectedImagePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nameFile, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}