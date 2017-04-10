package vn.com.toyota.checkdetail.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.activity.view.TouchImageView;

public class HomeActivity extends AppCompatActivity
        implements TouchImageView.TouchImageViewListener {

    private static final String IMAGE_VIEW_TAG = "car_thumbnail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initViews();
        drawGridLineIntoImage();
    }

    private int _layoutWidth;
    private int _layoutHeight;

    private void initLayoutSize() {
        _layoutWidth = rootLayout.getMeasuredWidth();
        _layoutHeight = rootLayout.getMeasuredHeight();
        Log.i("CALCULATE", "_layoutWidth: " + _layoutWidth + " | _layoutHeight: " + _layoutHeight);
    }

    @BindView(R.id.iv_car_part)
    TouchImageView ivCarPart;
    @BindView(R.id.iv_thumbnail)
    ImageView ivThumbnail;
    @BindView(R.id.cv_thumbnail)
    View cvThumbnail;

    private void initViews() {
        ivCarPart.setOnTouchImageViewListener(this);
        cvThumbnail.setOnTouchListener(new DragNDropTouchListener());

        rootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initLayoutSize();

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivThumbnail.getLayoutParams();
                params.height = _layoutHeight / 2;
                ivThumbnail.setLayoutParams(params);

                displayThumbnail();
                calculateMargins();
            }
        }, 100);
    }

    private static final int PAINT_COLOR = Color.RED;
    private static final int NUMBER_OF_ROWS = 3;
    private static final int NUMBER_OF_COLUMNS = 3;
    private int mBitmapWidth, mBitmapHeight;

    private void drawGridLineIntoImage() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.toyota_innova);
        Bitmap result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(PAINT_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);

        canvas.drawBitmap(bmp, 0f, 0f, paint);

        int width = result.getWidth();
        int height = result.getHeight();
        // Vertical lines
        for (int i = 1; i < NUMBER_OF_COLUMNS; i++) {
            canvas.drawLine(width * i / NUMBER_OF_COLUMNS, 0, width * i / NUMBER_OF_COLUMNS, height, paint);
        }

        // Horizontal lines
        for (int i = 1; i < NUMBER_OF_ROWS; i++) {
            canvas.drawLine(0, height * i / NUMBER_OF_ROWS, width, height * i / NUMBER_OF_ROWS, paint);
        }

        this.mBitmapWidth = width;
        this.mBitmapHeight = height;

        ivCarPart.setImageBitmap(result);
    }

    @Override
    public void onActionUp(Matrix matrix, MotionEvent event) {
//        Matrix matrix = ivCarPart.getMatrix();
        // Get the values of the matrix
        float[] values = new float[9];
        matrix.getValues(values);

        // values[2] and values[5] are the x,y coordinates of the top left corner of the drawable image, regardless of the zoom factor.
        // values[0] and values[4] are the zoom factors for the image's width and height respectively. If you zoom at the same factor, these should both be the same value.

        Log.i("TOUCH", "values[2]: " + values[2] + " | values[0]: " + values[0]);
        Log.i("TOUCH", "values[5]: " + values[5] + " | values[4]: " + values[4]);
        // event is the touch event for MotionEvent.ACTION_UP
        float relativeX = (event.getX() - values[2]) / values[0];
        float relativeY = (event.getY() - values[5]) / values[4];
        Log.i("TOUCH", "relativeX: " + relativeX + " | relativeY: " + relativeY);

        Log.i("TOUCH", "mBitmapWidth: " + mBitmapWidth + " | mBitmapHeight: " + mBitmapHeight);
        int posX = 0;
        int posY = 0;
        Rect rect = new Rect(posX, posY, posX + mBitmapWidth, posY + mBitmapHeight);
        if (!rect.contains((int) relativeX, (int) relativeY)) {
            return;
        }

        int x = 0;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            float start = mBitmapWidth / NUMBER_OF_ROWS * i;
            float end = mBitmapWidth / NUMBER_OF_ROWS * (i + 1);
            Log.i("TOUCH", "[row]" + "[" + i + "]start: " + start + " | end: " + end);

            if (relativeX >= start && relativeX < end) {
                x = (i + 1);
                break;
            }
        }

        int y = 0;
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            float start = mBitmapHeight / NUMBER_OF_COLUMNS * i;
            float end = mBitmapHeight / NUMBER_OF_COLUMNS * (i + 1);
            Log.i("TOUCH", "[col]" + "[" + i + "]start: " + start + " | end: " + end);

            if (relativeY >= start && relativeY < end) {
                y = (i + 1);
                break;
            }
        }
        Log.i("TOUCH", "x: " + x + " | y: " + y);
        Toast.makeText(this, "zone(" + x + ", " + y + ")", Toast.LENGTH_SHORT).show();
    }

    private void displayThumbnail() {
        Glide.with(this)
                .load(R.drawable.toyota_innova_2015)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivThumbnail);
    }

    private int _maxLeftMargin;
    private int _maxTopMargin;

    private void calculateMargins() {
        ivThumbnail.postDelayed(new Runnable() {
            @Override
            public void run() {
                _maxLeftMargin = _layoutWidth - ivThumbnail.getMeasuredWidth();
                _maxTopMargin = _layoutHeight - ivThumbnail.getMeasuredHeight();
                Log.i("CALCULATE", "_maxLeftMargin: " + _maxLeftMargin + " | _maxTopMargin: " + _maxTopMargin);
            }
        }, 100);
    }

    @BindView(R.id.view_root)
    ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;

    private final class DragNDropTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    int leftMargin = X - _xDelta;
                    leftMargin = leftMargin < 0 ? 0 : leftMargin;
                    leftMargin = leftMargin > _maxLeftMargin ? _maxLeftMargin : leftMargin;

                    int topMargin = Y - _yDelta;
                    Log.i("ACTION_MOVE", "topMargin: " + topMargin);
                    topMargin = topMargin < 0 ? 0 : topMargin;
                    topMargin = topMargin > _maxTopMargin ? _maxTopMargin : topMargin;
                    Log.i("ACTION_MOVE", "topMargin 222: " + topMargin);

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = leftMargin;
                    layoutParams.topMargin = topMargin;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }
}
