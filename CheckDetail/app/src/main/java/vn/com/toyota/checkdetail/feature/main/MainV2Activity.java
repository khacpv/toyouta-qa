package vn.com.toyota.checkdetail.feature.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.toyota.checkdetail.Common;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.feature.edtimg.EditImageActivity;
import vn.com.toyota.checkdetail.model.Error;
import vn.com.toyota.checkdetail.model.ErrorPart;
import vn.com.toyota.checkdetail.model.ErrorPixel;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.model.ImageCapture;
import vn.com.toyota.checkdetail.model.Product;
import vn.com.toyota.checkdetail.service.SaveImageService;
import vn.com.toyota.checkdetail.storage.ProductStorage;
import vn.com.toyota.checkdetail.utils.FileUtils;
import vn.com.toyota.checkdetail.utils.GsonUtils;
import vn.com.toyota.checkdetail.utils.ImageUtils;
import vn.com.toyota.checkdetail.view.TouchImageView;

public class MainV2Activity extends AppCompatActivity
        implements TouchImageView.TouchImageViewListener,
        ErrorPartAdapter.ErrorPartListener {
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
        showErrorPartList();
        showProductInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawGridLineIntoImage();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.have_to_complete),
                Toast.LENGTH_SHORT).show();
    }

    @BindView(R.id.tv_sequence)
    TextView tvSequence;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_process)
    TextView tvProcess;

    private void showProductInfo() {
        Product product = ProductStorage.getInstance().getProduct();

        String seq = getString(R.string.seq) + ": " + product.getSequence();
        tvSequence.setText(seq);
        String grade = getString(R.string.grade) + ": " + product.getGrade();
        tvGrade.setText(grade);
        ErrorPosition errorPosition = ProductStorage.getInstance().getCurrentErrorPosition();
        if (errorPosition != null) {
            String process = getString(R.string.process) + ": " + errorPosition.getCode();
            tvProcess.setText(process);
        }
    }

    @BindView(R.id.iv_car_part)
    TouchImageView ivCarPart;
    @BindView(R.id.rv_error_part)
    RecyclerView rvErrorPart;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.btn_scroll_down)
    Button btnScrollDown;
    @BindView(R.id.btn_scroll_up)
    Button btnScrollUp;

    private ErrorPartAdapter mErrorPartAdapter;

    private void initViews() {
        ivCarPart.setOnTouchImageViewListener(this);

        rvErrorPart.setHasFixedSize(true);
        rvErrorPart.setLayoutManager(new LinearLayoutManager(this));
        rvErrorPart.setItemAnimator(new DefaultItemAnimator());

        ivGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullGuideImage();
            }
        });
        ImageUtils.showImage(this, R.drawable.toyota_logo, ivLogo);

        btnScrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });

        btnScrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });

        scrollUp();
    }

    private void scrollDown() {
        if (mErrorPartAdapter != null) {
            rvErrorPart.smoothScrollToPosition(mErrorPartAdapter.getItemCount() - 1);
            btnScrollDown.setEnabled(false);
            btnScrollUp.setEnabled(true);
        }
    }

    private void scrollUp() {
        rvErrorPart.smoothScrollToPosition(0);
        btnScrollUp.setEnabled(false);
        btnScrollDown.setEnabled(true);
    }

    private void showErrorPartList() {
        ErrorPosition errorPosition = ProductStorage.getInstance().getCurrentErrorPosition();
        mErrorPartAdapter = new ErrorPartAdapter(this, errorPosition.getErrorParts(), this);
        rvErrorPart.setAdapter(mErrorPartAdapter);
    }

    private void showFullGuideImage() {
        Error error = ProductStorage.getInstance().getCurrentError();
        if (error == null) {
            Log.w(TAG, "error NULL");
            return;
        }
        DialogFragment fragment = GuideDialogFragment.newInstance(error);
        fragment.show(getSupportFragmentManager(), GuideDialogFragment.TAG);
    }

    @Override
    public void errorPartItemOnClick(View view, ErrorPart errorPart) {
        if (errorPart.getImgUrl().isEmpty()) {
            return;
        }
        for (ErrorPart ep : mErrorPartAdapter.getList()) {
            ep.setSelected(false);
        }
        errorPart.setSelected(true);
        mErrorPartAdapter.notifyDataSetChanged();

        ProductStorage.getInstance().setCurrentErrorPart(errorPart);
        drawGridLineIntoImage();
        showPopupMenu(view);
    }

    private void showPopupMenu(View view) {
        ErrorPart errorPart = ProductStorage.getInstance().getCurrentErrorPart();
        if (errorPart == null) {
            Log.w(TAG, "errorPart NULL");
            return;
        }

        final PopupMenu popupMenu = new PopupMenu(this, view);
        int id = 0;
        for (Error err : errorPart.getErrors()) {
            popupMenu.getMenu().add(Menu.NONE, id, id, err.getCode());
            id++;
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                ErrorPart errorPart = ProductStorage.getInstance().getCurrentErrorPart();
                Error error = errorPart.getErrors().get(id);
                error.setSelected(true);
                ProductStorage.getInstance().setCurrentError(error);
                showGuideImage();
                return true;
            }
        });
        popupMenu.show();
    }

    @BindView(R.id.iv_guide)
    ImageView ivGuide;

    private void showGuideImage() {
        Error error = ProductStorage.getInstance().getCurrentError();
        if (error == null) {
            Log.w(TAG, "error NULL");
            return;
        }
        ImageUtils.showImage(this, error.getImgGuideUrl(), ivGuide);
    }

    @OnClick(R.id.btn_finish)
    public void finishClick() {
        ErrorPixel errorPixel = ProductStorage.getInstance().getCurrentErrorPixel();
        if (errorPixel == null) {
            Toast.makeText(this, "Chưa chụp ảnh lỗi", Toast.LENGTH_SHORT).show();
            return;
        }
        chooseShift();
    }

    private void finishActivity() {
        finish();
        ProductStorage.getInstance().clearMemory();
    }

    private static final int PAINT_COLOR = Color.GRAY;
    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private static final float STROKE_WIDTH = 1f;
    private static final float ERROR_DOT_RADIUS = 10f;
    private int mBitmapWidth, mBitmapHeight;

    private void drawGridLineIntoImage() {
        ErrorPart errorPart = ProductStorage.getInstance().getCurrentErrorPart();
        if (errorPart == null) {
            ivLogo.setVisibility(View.VISIBLE);
            Log.w(TAG, "errorPart NULL");
            return;
        }
        ivLogo.setVisibility(View.GONE);
        Bitmap bmp = BitmapFactory.decodeFile(errorPart.getImgUrl());
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

        // Error dots
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        List<ErrorPixel> errorPixels = errorPart.getErrorPixels();
        for (ErrorPixel ep : errorPixels) {
            if (ep.getX() == 0 && ep.getY() == 0) {
                continue;
            }
            canvas.drawCircle(ep.getX(), ep.getY(), ERROR_DOT_RADIUS, paint);
        }

        this.mBitmapWidth = width;
        this.mBitmapHeight = height;

        ivCarPart.setImageBitmap(result);
    }

    @Override
    public void onActionUp(Matrix matrix, MotionEvent event) {
        Error error = ProductStorage.getInstance().getCurrentError();
        if (error == null) {
            Toast.makeText(this, "Chưa chọn mã lỗi", Toast.LENGTH_SHORT).show();
            return;
        }

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
        ErrorPart errorPart = ProductStorage.getInstance().getCurrentErrorPart();
        ErrorPixel errorPixel = new ErrorPixel(relativeX, relativeY);
        errorPart.getErrorPixels().add(errorPixel);
        ProductStorage.getInstance().setCurrentErrorPixel(errorPixel);
        goToCameraActivity();
    }

    public static final int REQUEST_CODE_EDIT = 1234;
    public static final int MAX = Integer.MAX_VALUE;

    private void goToCameraActivity() {
        Intent intent = new Intent(MainV2Activity.this, EditImageActivity.class);
        intent.putExtra(Common.BundleConstant.IMAGE_CAPTURE, "");
        intent.putExtra(Common.BundleConstant.POSITION, MAX);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == RESULT_OK) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        Bundle bundle = data.getExtras();

                        if (bundle == null) {
                            return;
                        }

                        ImageCapture imageCapture = GsonUtils.String2Object(
                                bundle.getString(Common.BundleConstant.IMAGE_CAPTURE),
                                ImageCapture.class);
                        List<ImageCapture> imageCaptures = new ArrayList<>();
                        imageCaptures.add(imageCapture);
                        saveImageDraw(imageCaptures);
                        setErrorPixelImageUrl(imageCapture.getFilepath());
                    }
                });
            }
        }
    }

    private void setErrorPixelImageUrl(String path) {
        ErrorPixel errorPixel = ProductStorage.getInstance().getCurrentErrorPixel();
        if (errorPixel != null) {
            String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());
            fileName = FileUtils.getFileNameWithExtAdded(fileName, AppConfig.SAVED_IMAGE_EXT);
            errorPixel.setImageUrl(fileName);
        }
    }

    private void saveImageDraw(List<ImageCapture> captures) {
        Intent intent = new Intent(MainV2Activity.this, SaveImageService.class);
        intent.putExtra(Common.BundleConstant.LIST_IMAGE_CAPTURE, GsonUtils.Object2String(captures));
        startService(intent);
    }

    Socket clientSocket;

    class ClientThread implements Runnable {

        private String msgToServer;

        public ClientThread(String msgToServer) {
            this.msgToServer = msgToServer;
        }

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(AppConfig.SERVER_IP);
                clientSocket = new Socket(serverAddr, AppConfig.SERVER_PORT);

                OutputStream outputStream = null;
                outputStream = clientSocket.getOutputStream();
                final PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgToServer);
                printStream.flush();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class StopClientThread implements Runnable {
        @Override
        public void run() {
            try {
                clientSocket.close();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private Dialog dialogChooseShift;

    public void chooseShift() {
        dialogChooseShift = new Dialog(this);
        dialogChooseShift.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChooseShift.setContentView(R.layout.dialog_choose_shift);
        dialogChooseShift.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogChooseShift.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button btnYellow = (Button) dialogChooseShift.findViewById(R.id.btnCavang);
        Button btnRed = (Button) dialogChooseShift.findViewById(R.id.btnCado);
        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("Ca Y");
            }
        });
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("Ca R");
            }
        });
        dialogChooseShift.show();
    }

    //Tên file ảnh - mã lỗi - mã process - tọa độ x - tọa độ y - col - row - ca làm việc

    private void sendRequest(String shiftCode) {
        try {
            ErrorPixel errorPixel = ProductStorage.getInstance().getCurrentErrorPixel();
            ErrorPosition errorPosition = ProductStorage.getInstance().getCurrentErrorPosition();
            ErrorPart errorPart = ProductStorage.getInstance().getCurrentErrorPart();

            if (errorPart == null || errorPosition == null || errorPixel == null) {
                Log.e(TAG, "params is null");
                dialogChooseShift.dismiss();
                return;
            }

            String errorCodes = "";
            for (Error err : errorPart.getErrors()) {
                if (err.isSelected()) {
                    errorCodes += err.getCode() + "|";
                }
            }
            errorCodes = errorCodes.substring(0, errorCodes.length() - 1);

            String msgToServer = errorPixel.getImageUrl()
                    + ";" + errorCodes
                    + ";" + errorPosition.getCode()
                    + ";" + errorPixel.getX()
                    + ";" + errorPixel.getY()
                    + ";" + NUMBER_OF_COLUMNS
                    + ";" + NUMBER_OF_ROWS
                    + ";" + shiftCode;
            Log.d(TAG, "msgToServer: " + msgToServer);
            new Thread(new ClientThread(msgToServer)).start();
            dialogChooseShift.dismiss();
            finishActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}