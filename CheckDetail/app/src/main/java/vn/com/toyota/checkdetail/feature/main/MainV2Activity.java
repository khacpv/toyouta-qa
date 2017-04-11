package vn.com.toyota.checkdetail.feature.main;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.activity.view.TouchImageView;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.model.Product;
import vn.com.toyota.checkdetail.storage.ErrorPositionStorage;
import vn.com.toyota.checkdetail.utils.DataUtils;

public class MainV2Activity extends AppCompatActivity
        implements TouchImageView.TouchImageViewListener {
    private final String TAG = MainV2Activity.class.getName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainV2Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        ButterKnife.bind(this);
        initViews();
        drawGridLineIntoImage();
        showProductInfo();
    }

    @Override
    public void onBackPressed() {

    }

    @BindView(R.id.tv_sequence)
    TextView tvSequence;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_process)
    TextView tvProcess;

    private void showProductInfo() {
        Product product = DataUtils.getProduct();
        String seq = getString(R.string.seq) + ": " + product.getSequence();
        tvSequence.setText(seq);
        String grade = getString(R.string.grade) + ": " + product.getGrade();
        tvGrade.setText(grade);
        ErrorPosition errorPosition = ErrorPositionStorage.getInstance().getErrorPosition();
        if (errorPosition != null) {
            String process = getString(R.string.process) + ": " + errorPosition.getCode();
            tvProcess.setText(process);
        }
    }

    @BindView(R.id.iv_car_part)
    TouchImageView ivCarPart;

    private void initViews() {
        ivCarPart.setOnTouchImageViewListener(this);
    }

    private static final int PAINT_COLOR = Color.GRAY;
    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private static final float STROKE_WIDTH = 1f;
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
        paint.setStrokeWidth(STROKE_WIDTH);

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
}