package com.example.ngothi.feebbackquality;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ngothi on 10/31/2016.
 */

public class MyView extends View implements View.OnTouchListener {

    Bitmap originalbitmap;
    float x = 0, y = 0;

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

    public void setBitmap(Bitmap bitmap) {
        this.originalbitmap = bitmap;
    }


    public Bitmap getBitmap() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
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

        Matrix matrix = new Matrix();
        RectF rectSrc = new RectF(0, 0, originalbitmap.getWidth(), originalbitmap.getHeight());
        RectF rectDst = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());

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
