package com.example.ngothi.checksheet.ui.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.ngothi.checksheet.ui.model.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 13/03/2017.
 */

public class ImageDrawing extends ImageView {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    Context context;
    private Paint mPaint;
    private boolean clearCanvas = false;
    private Size mSize;
    private String sourcePath;
    private int resourceId = -1;
    List<Path> mPathsLine = new ArrayList<>();
    OnImageDrawListener mOnImageDrawListener;

    public void init(Context context) {
        this.context = context;
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(4f);
        mSize = new Size(0, 0);
    }

    public ImageDrawing(Context context) {
        super(context);
        init(context);
    }

    public ImageDrawing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageDrawing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public List<Path> getPathsLine() {
        return mPathsLine;
    }

    public Size getSize() {
        return mSize;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        resourceId = -1;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setOnImageDrawListener(OnImageDrawListener onImageDrawListener) {
        mOnImageDrawListener = onImageDrawListener;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void drawPath(final List<Path> paths) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mPathsLine.addAll(paths);
                for (Path path : mPathsLine) {
                    mCanvas.drawPath(path, mPaint);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize.setWidth(w);
        mSize.setHeight(h);
        if (w == 0 || h == 0) {
            return;
        }
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (clearCanvas) {  // Choose the colour you want to clear with.
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            clearCanvas = false;
            return;
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        addPath(mPath);
        canvas.drawPath(mPath, mPaint);
    }

    private void addPath(Path path) {
        mPathsLine.add(new Path(path));
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        addPath(mPath);
        mCanvas.drawPath(mPath, mPaint);

        // kill this so we don't double draw
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                if (mOnImageDrawListener != null) {
                    mOnImageDrawListener.onDrawComplete(mPathsLine);
                }

                break;
        }
        return true;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.resourceId = resId;
        setSourcePath(null);
    }

    public void clearDraw() {
        if (mCanvas == null) return;
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mPathsLine.clear();
        invalidate();
    }


    public interface OnImageDrawListener {
        void onDrawComplete(List<Path> paths);
    }
}
