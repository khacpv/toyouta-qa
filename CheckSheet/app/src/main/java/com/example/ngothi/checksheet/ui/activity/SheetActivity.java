package com.example.ngothi.checksheet.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.activity.view.ImageDrawing;
import com.example.ngothi.checksheet.ui.adapter.StepImageAdapter;
import com.example.ngothi.checksheet.ui.event.OnItemListener;
import com.example.ngothi.checksheet.ui.model.ImageCapture;
import com.example.ngothi.checksheet.ui.utils.CanvasUtils;
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetActivity extends AppCompatActivity implements OnItemListener<ImageCapture> {
    Dialog dialog;
    int number = 1;
    ImageView image;
    ImageDrawing imagePreview;
    int REQUEST_ID_IMAGE_CAPTURE = 1000;
    RelativeLayout lyImage;
    int widthImageCapture;
    int heightImageCapture;
    int maxHeightImageCapture;

    @BindView(R.id.rcvImage)
    RecyclerView rcvImage;

    StepImageAdapter mStepImageAdapter;

    List<ImageCapture> mImageCaptures = new ArrayList<>();

    private int selectedPosition = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheetctivity);
        ButterKnife.bind(this);
        imagePreview = (ImageDrawing) findViewById(R.id.imagePreview);
        lyImage = (RelativeLayout) findViewById(R.id.lyImage);
        imagePreview.setOnImageDrawListener(new ImageDrawing.OnImageDrawListener() {
            @Override
            public void onDrawComplete(List<Path> paths) {
                if (mImageCaptures == null) {
                    return;
                }
                mImageCaptures.get(selectedPosition).setEditted(true);
                mImageCaptures.get(selectedPosition).setPaths(paths);
            }
        });

        lyImage.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        lyImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        maxHeightImageCapture = lyImage.getHeight(); //height is ready
                        showImagePreview(R.drawable.lopoto);
                    }
                });

        mImageCaptures.add(new ImageCapture.Builder().setFromFile(false)
                .setResourceId(R.drawable.lopoto)
                .build());

        mStepImageAdapter = new StepImageAdapter(getApplicationContext(), mImageCaptures, this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                        false);
        rcvImage.setHasFixedSize(true);
        rcvImage.setLayoutManager(layoutManager);
        rcvImage.setAdapter(mStepImageAdapter);
    }

    public void okClick(View v) {
       /* String fileOut = CanvasUtils.createImage(getApplicationContext(), R.drawable.lopoto,
                FileUtils.getDirectoryImageCapturePath() + "/" + FileUtils.getCaptureImageName(),
                imagePreview.getPathsLine(), imagePreview.getPaint(), imagePreview.getSize());*/

        String fileOut =
                CanvasUtils.createImage(imagePreview.getSourcePath(), imagePreview.getPathsLine(),
                        imagePreview.getPaint(), imagePreview.getSize());
        if (fileOut != null) {
            Toast.makeText(getApplicationContext(), "create image in " + fileOut,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void notGoodClick(View v) {
        FileUtils.createBitmapFromImageView(imagePreview);
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

    public void showImagePreview(int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        options.inDither = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap originBitmap =
                BitmapFactory.decodeResource(getApplicationContext().getResources(), resourceId);
        originBitmap = Bitmap.createScaledBitmap(originBitmap, originBitmap.getWidth() / 4,
                originBitmap.getHeight() / 4, false);
        displayBitmap(originBitmap);
    }

    public void showImagePreview(String filePath) {
        File fileImage = new File(filePath);
        if (fileImage.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            options.inDither = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap originBitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            originBitmap = Bitmap.createScaledBitmap(originBitmap, originBitmap.getWidth() / 4,
                    originBitmap.getHeight() / 4, false);
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(fileImage.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    originBitmap = rotateImage(originBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    originBitmap = rotateImage(originBitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    originBitmap = rotateImage(originBitmap, 270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    break;
            }
            imagePreview.setSourcePath(filePath);
            displayBitmap(originBitmap);
        }
    }

    public void displayBitmap(Bitmap originBitmap) {
        imagePreview.clearDraw();
        imagePreview.setSourcePath(null);
        widthImageCapture = originBitmap.getWidth();
        heightImageCapture = originBitmap.getHeight();
        Display display =
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenWidth = display.getWidth();

        final float ratio = (float) widthImageCapture / heightImageCapture;
        if (widthImageCapture > heightImageCapture) {
            widthImageCapture = screenWidth;
            heightImageCapture = (int) (widthImageCapture / ratio);
        } else {
            heightImageCapture = maxHeightImageCapture;
            widthImageCapture = (int) (heightImageCapture * ratio);
        }
        imagePreview.getLayoutParams().height = heightImageCapture;
        imagePreview.getLayoutParams().width = widthImageCapture;
        imagePreview.requestLayout();
        imagePreview.setImageBitmap(originBitmap);
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        String filePath = data.getExtras().getString("data");
                        mStepImageAdapter.addImage(new ImageCapture.Builder().setFromFile(true)
                                .setFilepath(filePath)
                                .build());
                        showImagePreview(filePath);
                        selectedPosition = mImageCaptures.size() - 1;
                        rcvImage.scrollToPosition(selectedPosition);
                    }
                });
            }
        }
    }

    @Override
    public void onItemClick(ImageCapture item, int position) {
        selectedPosition = position;
        if (!item.isFromFile()) {
            showImagePreview(item.getResourceId());
        } else {
            showImagePreview(item.getFilepath());
        }

        if (item.isEditted()) {
            imagePreview.drawPath(item.getPaths());
        }
    }
}