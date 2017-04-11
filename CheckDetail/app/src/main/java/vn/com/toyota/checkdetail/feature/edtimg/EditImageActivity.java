package vn.com.toyota.checkdetail.feature.edtimg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.toyota.checkdetail.Common;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.activity.view.ImageDrawing;
import vn.com.toyota.checkdetail.feature.BaseActivity;
import vn.com.toyota.checkdetail.feature.camera.CameraActivity;
import vn.com.toyota.checkdetail.model.DrawEntityPath;
import vn.com.toyota.checkdetail.model.ImageCapture;
import vn.com.toyota.checkdetail.model.Size;
import vn.com.toyota.checkdetail.utils.CanvasUtils;
import vn.com.toyota.checkdetail.utils.GsonUtils;

/**
 * Created by eo_cuong on 3/25/17.
 */

public class EditImageActivity extends BaseActivity {

    public static final int REQUET_CODE_CAMERA = 1357;

    @BindView(R.id.imageEdit)
    ImageDrawing mImageDrawing;

    @BindView(R.id.lyImage)
    LinearLayout lyImage;

    private int position;
    private ImageCapture mImageCapture;
    private int maxHeightImageCapture = -1;
    private boolean isEdited = false;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_edit_image;
    }

    @Override
    public void afterSetcontenview() {

        mImageDrawing.setOnImageDrawListener(new ImageDrawing.OnImageDrawListener() {
            @Override
            public void onDrawComplete(List<Path> paths, List<DrawEntityPath> drawEntityPaths) {
                if (mImageCapture == null) {
                    return;
                }
                isEdited = true;
                mImageCapture.setEditted(true);
                mImageCapture.setDrawEntityPaths(drawEntityPaths);
            }
        });

        lyImage.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        lyImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        maxHeightImageCapture = lyImage.getHeight(); //height is ready
                        if (mImageCapture != null) {
                            if (!mImageCapture.isFromFile()) {
                                showImagePreview(mImageCapture.getResourceId());
                            } else {
                                showImagePreview(mImageCapture.getFilepath());
                            }
                        }
                    }
                });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {

            return;
        }
        position = bundle.getInt(Common.BundleConstant.POSITION);
        mImageCapture =
                GsonUtils.String2Object(bundle.getString(Common.BundleConstant.IMAGE_CAPTURE),
                        ImageCapture.class);
        if (mImageCapture == null) {
            startActivityForResult(new Intent(EditImageActivity.this, CameraActivity.class),
                    REQUET_CODE_CAMERA);
            return;
        }

        if (maxHeightImageCapture != -1) {
            if (!mImageCapture.isFromFile()) {
                showImagePreview(mImageCapture.getResourceId());
            } else {
                showImagePreview(mImageCapture.getFilepath());
            }
        }
    }

    public void showImagePreview(int resourceId) {
        if (resourceId < 0) {
            mImageDrawing.setVisibility(View.GONE);
            return;
        }
        mImageDrawing.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(resourceId)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource,
                            GlideAnimation<? super Bitmap> glideAnimation) {
                        displayBitmap(resource);
                    }
                });
    }

    public void showImagePreview(final String filePath) {
        if (filePath == null) {
            mImageDrawing.setVisibility(View.GONE);
            return;
        }
        mImageDrawing.setVisibility(View.VISIBLE);

        Glide.with(getApplicationContext())
                .load(new File(filePath))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap originBitmap,
                            GlideAnimation<? super Bitmap> glideAnimation) {
                        originBitmap =
                                Bitmap.createScaledBitmap(originBitmap, originBitmap.getWidth() / 4,
                                        originBitmap.getHeight() / 4, false);
                        ExifInterface ei = null;
                        try {
                            ei = new ExifInterface(filePath);
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
                        mImageDrawing.setSourcePath(filePath);
                        displayBitmap(originBitmap);
                    }
                });
    }

    public void displayBitmap(Bitmap originBitmap) {

        mImageDrawing.clearDraw();
        int widthImageCapture = originBitmap.getWidth();
        int heightImageCapture = originBitmap.getHeight();
        Display display =
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenWidth = display.getWidth();

        final float ratio = (float) widthImageCapture / heightImageCapture;
        widthImageCapture = screenWidth;
        heightImageCapture = (int) (widthImageCapture / ratio);
        if (heightImageCapture >= maxHeightImageCapture) {
            heightImageCapture = maxHeightImageCapture;
            widthImageCapture = (int) (heightImageCapture * ratio);
        }

        mImageDrawing.getLayoutParams().height = heightImageCapture;
        mImageDrawing.getLayoutParams().width = widthImageCapture;
        mImageDrawing.requestLayout();
        mImageDrawing.setImageBitmap(originBitmap);

        //set size of imageview
        if (mImageCapture.getViewSize() == null) {
            mImageCapture.setViewSize(new Size(widthImageCapture, heightImageCapture));
        }
        if (mImageCapture.isEditted()) {
            mImageDrawing.drawPath(mImageCapture.getDrawEntityPaths());
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    @OnClick(R.id.btnClose)
    public void onCloseClick(View v) {
        if (!isEdited) {
            finish();
            return;
        }

        new SaveImageTask().execute();
    }

    private String saveImage() {
        String fileOut = null;
        if (mImageCapture.isFromFile()) {
            fileOut = CanvasUtils.createImageThumb(mImageCapture.getFilepath(),
                    mImageDrawing.getDrawEntityPaths(), mImageDrawing.getPaint(),
                    mImageDrawing.getSize());
        } else {
            try {
                fileOut = CanvasUtils.createImageThumb(getApplicationContext(),
                        mImageCapture.getResourceId(), mImageCapture.getThumbPath(),
                        mImageDrawing.getDrawEntityPaths(), mImageDrawing.getPaint(),
                        mImageDrawing.getSize());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return fileOut;
    }

    class SaveImageTask extends AsyncTask<Void, Void, String> {




        @Override
        protected void onPreExecute() {
            showHUD("Saving image");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return saveImage();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissHUD();
            if (s != null) {
                mImageCapture.setThumbPath(s);
                Intent intent = new Intent();
                intent.putExtra(Common.BundleConstant.POSITION, position);
                intent.putExtra(Common.BundleConstant.IMAGE_CAPTURE,
                        GsonUtils.Object2String(mImageCapture));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.have_to_complete),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUET_CODE_CAMERA && resultCode == RESULT_OK) {
            isEdited = true;
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            mImageCapture =
                    GsonUtils.String2Object(bundle.getString(Common.BundleConstant.IMAGE_CAPTURE),
                            ImageCapture.class);
        }
    }
}
