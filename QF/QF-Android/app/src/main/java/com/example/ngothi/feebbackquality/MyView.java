package com.example.ngothi.feebbackquality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ngothi on 10/31/2016.
 */

public class MyView extends View implements View.OnTouchListener {

    String pathImage;
    Bitmap originalbitmap;
    float x = 0, y = 0;

    Matrix matrix = new Matrix();
    RectF rectSrc;
    RectF rectDst;

    Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyView(Context context) {
        super(context);
        setWillNotDraw(false);

        setOnTouchListener(this);
    }

    public MyView(Context context, AttributeSet attr) {
        super(context, attr);
        setWillNotDraw(false);
        setOnTouchListener(this);
    }

    public void setOriginalBitmap(String path) {
        this.pathImage = path;
    }

    public void setBitmap(Bitmap bitmap) {
        this.originalbitmap = bitmap;
        rectSrc = new RectF(0, 0, originalbitmap.getWidth(), originalbitmap.getHeight());
    }

    public Bitmap saveBitmap(int sampleSize, int radius, int strokeWidth) {
        // sampleSize: 1 ==> radius: 200
        // sampleSize: 2 ==> radius: 180
        // sampleSize: 4 ==> radius: 80

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
            BitmapFactory.decodeFile(pathImage, options); //Đọc thông tin ảnh
            options.inSampleSize = sampleSize; //Scale bitmap xuống 1 lần
            options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
            Bitmap originalSizeBitmap = BitmapFactory.decodeFile(pathImage, options);

            Bitmap mutableBitmap = originalSizeBitmap.copy(Bitmap.Config.ARGB_8888, true);
            RectF originalRect =
                new RectF(0, 0, mutableBitmap.getWidth(), mutableBitmap.getHeight());

            originalSizeBitmap.recycle();
            originalSizeBitmap = null;
            System.gc();

            Canvas canvas = new Canvas(mutableBitmap);

            float[] originXy = getOriginalPoint(originalRect);
            circlePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(originXy[0], originXy[1], radius, circlePaint);
            return mutableBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "can not draw original bitmap", Toast.LENGTH_SHORT)
                .show();
            return getBitmapScreenShot();
        }
    }

    private float[] getOriginalPoint(RectF originalRect) {
        float percentX = x / rectDst.width();
        float percentY = y / rectDst.height();
        float originX = percentX * originalRect.width();
        float originY = percentY * originalRect.height();

        return new float[] {originX, originY};
    }

    public Bitmap getBitmapScreenShot() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

    public Bitmap getBitmap(String pathImage) {
        setOriginalBitmap(pathImage);
        //return saveBitmap(4, 80, 8);
        return saveBitmap(2, 180, 16);
        //return saveBitmap(1, 200, 30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectDst = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        matrix.setRectToRect(rectSrc, rectDst, Matrix.ScaleToFit.CENTER);

        canvas.drawBitmap(originalbitmap, matrix, new Paint());

        if (x + y > 0) {
            circlePaint.setColor(Color.RED);
            circlePaint.setStrokeWidth(8);
            circlePaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x, y, 80, circlePaint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        //Log.e("TAG","x:"+x+" - y"+y);
        invalidate();
        return true;
    }
}
