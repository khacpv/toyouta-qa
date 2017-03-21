package vn.com.toyota.checkdetail.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.Common;
import vn.com.toyota.checkdetail.activity.view.ImageDrawing;
import vn.com.toyota.checkdetail.adapter.StepImageAdapter;
import vn.com.toyota.checkdetail.event.OnItemListener;
import vn.com.toyota.checkdetail.model.CategoyCheck;
import vn.com.toyota.checkdetail.model.DrawEntityPath;
import vn.com.toyota.checkdetail.model.ImageCapture;
import vn.com.toyota.checkdetail.model.SettingModel;
import vn.com.toyota.checkdetail.model.Size;
import vn.com.toyota.checkdetail.model.Step;
import vn.com.toyota.checkdetail.utils.CanvasUtils;
import vn.com.toyota.checkdetail.utils.DrawableUtils;
import vn.com.toyota.checkdetail.utils.FileUtils;
import vn.com.toyota.checkdetail.utils.GsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SheetActivity extends BaseActivity implements OnItemListener<ImageCapture> {

    @BindView(R.id.imagePreview)
    ImageDrawing imagePreview;

    @BindView(R.id.rcvImage)
    RecyclerView rcvImage;

    @BindView(R.id.lyImage)
    RelativeLayout lyImage;

    @BindView(R.id.tvGrade)
    TextView tvGrade;

    @BindView(R.id.tvSequence)
    TextView tvSequence;

    @BindView(R.id.tvCategory)
    TextView tvCategory;

    @BindView(R.id.tvStepCount)
    TextView tvStepCount;

    @BindView(R.id.inputNote)
    EditText inputNote;

    int REQUEST_ID_IMAGE_CAPTURE = 1000;
    int widthImageCapture;
    int heightImageCapture;
    int maxHeightImageCapture;
    StepImageAdapter mStepImageAdapter;
    List<ImageCapture> mImageCaptures = new ArrayList<>();
    private int selectedPosition = 0;

    private String mSequence = "1H00543";
    private String mGrade = "2403";
    private SettingModel mSettingModel;
    private int currentStep = 1;
    private int maxStep;
    private CategoyCheck currenCategory;

    private List<Step> mSteps = new ArrayList<>();
    private Step mStep;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_sheet;
    }

    @Override
    public void afterSetcontenview() {
        imagePreview.setOnImageDrawListener(new ImageDrawing.OnImageDrawListener() {
            @Override
            public void onDrawComplete(List<Path> paths, List<DrawEntityPath> drawEntityPaths) {
                if (mImageCaptures == null) {
                    return;
                }
                mImageCaptures.get(selectedPosition).setEditted(true);
                mImageCaptures.get(selectedPosition).setPaths(paths);
                mImageCaptures.get(selectedPosition).setDrawEntityPaths(drawEntityPaths);
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

     /*   mImageCaptures.add(new ImageCapture.Builder().setFromFile(false)
                .setResourceId(R.drawable.lopoto)
                .build());*/

        mStepImageAdapter = new StepImageAdapter(getApplicationContext(), mImageCaptures, this);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                        false);
        rcvImage.setHasFixedSize(true);
        rcvImage.setLayoutManager(layoutManager);
        rcvImage.setAdapter(mStepImageAdapter);

        initData();
    }

    private void initData() {
        fakeData();

        if (mSettingModel.getCategoyChecks().size() == 0) {
            return;
        }
        currentStep = 1;
        currenCategory = mSettingModel.getCategoyChecks().get(currentStep - 1);
        maxStep = mSettingModel.getCategoyChecks().size();
        mStep = new Step();
        mStep.setNoNumber(currentStep);
        mStep.setCategoyCheck(currenCategory);
        refreshStepView();
        tvGrade.setText(
                "GRADE " + mSettingModel.getCarModel() + " : " + mSettingModel.getCarModelName());
        tvSequence.setText("SEQUENCE: " + mSequence);

        refreshCurrentCategory();
    }

    private void refreshCurrentCategory() {
        inputNote.setText("");
        tvCategory.setText(currenCategory.getName());
        if (currenCategory.getImageDefaul() != null) {
            mStepImageAdapter.clear();
            selectedPosition = 0;
            mStepImageAdapter.addImage(new ImageCapture.Builder().setFromFile(false)
                    .setResourceId(DrawableUtils.getResourceIdFromName(getApplicationContext(),
                            currenCategory.getImageDefaul()))
                    .build());
            showImagePreview(mImageCaptures.get(0).getResourceId());
        }
    }

    private void refreshStepView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String step =
                        String.format(getResources().getString(R.string.step_by_step), currentStep,
                                maxStep);
                tvStepCount.setText(step);
            }
        });
    }

    private void fakeData() {

        CategoyCheck categoyCheck1 = new CategoyCheck();
        categoyCheck1.setNoStt(0);
        categoyCheck1.setName("Kiem tra lop ");
        categoyCheck1.setSpecificities(Arrays.asList("xanh", "dep"));
        categoyCheck1.setImageDefaul("lopoto");

        CategoyCheck categoyCheck2 = new CategoyCheck();
        categoyCheck2.setNoStt(1);
        categoyCheck2.setName("Kiem tra canh cua");
        categoyCheck2.setSpecificities(Arrays.asList("to", "vuong"));
        categoyCheck2.setImageDefaul("canhcua");

        CategoyCheck categoyCheck3 = new CategoyCheck();
        categoyCheck3.setNoStt(2);
        categoyCheck3.setName("Kiem tra den pha");
        categoyCheck3.setSpecificities(Arrays.asList("sang ", "trang"));
        categoyCheck3.setImageDefaul("denpha");

        mSettingModel = new SettingModel();
        mSettingModel.setCarModel("2403");
        mSettingModel.setCarModelName("INNOVA HA");

        List<CategoyCheck> categoyChecks = new ArrayList<>();
        categoyChecks.add(categoyCheck1);
        categoyChecks.add(categoyCheck2);
        categoyChecks.add(categoyCheck3);
        mSettingModel.setCategoyChecks(categoyChecks);
    }

    private void onStepCheckDone(boolean isGood) {

        if (mStep != null) {
            mStep.setGood(isGood);
            mStep.setNoteReality(inputNote.getText().toString());
            mStep.setImageList(mImageCaptures);
            mSteps.add(mStep);
        }

        if (currentStep == maxStep) {
            //TODO add ResultSheetActivity
            /*Intent intent = new Intent(SheetActivity.this, ResultSheetActivity.class);
            intent.putExtra(Common.BundleConstant.LIST_STEP, GsonUtils.Object2String(mSteps));
            intent.putExtra(Common.BundleConstant.GRADE, mGrade);
            intent.putExtra(Common.BundleConstant.SEQUENCE, mSequence);
            startActivity(intent);*/
            finish();
            return;
        }

        currentStep++;
        currenCategory = mSettingModel.getCategoyChecks().get(currentStep - 1);
        mStep = new Step();
        mStep.setNoNumber(currentStep);
        mStep.setCategoyCheck(currenCategory);
        refreshStepView();
        refreshCurrentCategory();
    }

    public void okClick(View v) {
        onStepCheckDone(true);
    }

    public void notGoodClick(View v) {
        onStepCheckDone(false);
    }

    public void captureClick(View v) {
        //TODO add CameraActivity
        /*AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        Intent intent = new Intent(SheetActivity.this, CameraActivity.class);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);*/
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    public void showImagePreview(int resourceId) {
        if (resourceId < 0) {
            imagePreview.setVisibility(View.GONE);
            return;
        }
        imagePreview.setVisibility(View.VISIBLE);
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
            imagePreview.setVisibility(View.GONE);
            return;
        }
        imagePreview.setVisibility(View.VISIBLE);

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
                        imagePreview.setSourcePath(filePath);
                        displayBitmap(originBitmap);
                    }
                });
    }

    public void displayBitmap(Bitmap originBitmap) {

        if (mImageCaptures.size() == 0) {
            return;
        }

        imagePreview.clearDraw();
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

        //set size of imageview
        if (mImageCaptures.get(selectedPosition).getViewSize() == null) {
            mImageCaptures.get(selectedPosition)
                    .setViewSize(new Size(widthImageCapture, heightImageCapture));
        }
        if (mImageCaptures.get(selectedPosition).isEditted()) {
            imagePreview.drawPath(mImageCaptures.get(selectedPosition).getDrawEntityPaths());
        }
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
        if (selectedPosition == position) {
            return;
        }
        selectedPosition = position;
        if (!item.isFromFile()) {
            showImagePreview(item.getResourceId());
        } else {
            showImagePreview(item.getFilepath());
        }
    }

    private String saveImage() {
        String fileOut = null;
        if (mImageCaptures.get(selectedPosition).isFromFile()) {
            fileOut = CanvasUtils.createImage(mImageCaptures.get(selectedPosition).getFilepath(),
                    imagePreview.getPathsLine(), imagePreview.getPaint(), imagePreview.getSize());
        } else {
            try {
                fileOut = CanvasUtils.createImage(getApplicationContext(),
                        mImageCaptures.get(selectedPosition).getResourceId(),
                        new File(FileUtils.getDirectory(),
                                FileUtils.getCaptureImageName()).getAbsolutePath(),
                        imagePreview.getPathsLine(), imagePreview.getPaint(),
                        imagePreview.getSize());
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
                Toast.makeText(getApplicationContext(), "create image in " + s, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.have_to_complete),
                Toast.LENGTH_SHORT).show();
    }
}